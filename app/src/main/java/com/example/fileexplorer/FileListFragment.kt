package com.example.fileexplorer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File

class FileListFragment : Fragment(), FileAdapter.OnFileClickListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FileAdapter
    private var currentPath: String? = null

    companion object {
        fun newInstance(path: String): FileListFragment {
            return FileListFragment().apply {
                arguments = Bundle().apply {
                    putString("path", path)
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_file_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentPath = arguments?.getString("path")

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = FileAdapter(this)
        recyclerView.adapter = adapter

        currentPath?.let { loadFiles(it) }
    }

    private fun loadFiles(path: String) {
        val directory = File(path)
        val files = directory.listFiles()?.filter { file ->
            file.isDirectory || file.extension.equals("txt", ignoreCase = true)
        }?.sortedWith(compareBy { it.name.lowercase() }) ?: emptyList()
        adapter.submitList(files)
    }

    override fun onFileClick(file: File) {
        if (file.isDirectory) {
            parentFragmentManager.beginTransaction()
                .replace(R.id.container, newInstance(file.absolutePath))
                .addToBackStack(null)
                .commit()
        } else if (file.extension.equals("txt", ignoreCase = true)) {
            parentFragmentManager.beginTransaction()
                .replace(R.id.container, FileContentFragment.newInstance(file.absolutePath))
                .addToBackStack(null)
                .commit()
        }
    }
}