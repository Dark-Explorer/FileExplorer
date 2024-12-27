package com.example.fileexplorer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import java.io.File

class FileContentFragment : Fragment() {
    companion object {
        fun newInstance(path: String): FileContentFragment {
            return FileContentFragment().apply {
                arguments = Bundle().apply {
                    putString("path", path)
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_file_content, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val textView: TextView = view.findViewById(R.id.contentView)

        arguments?.getString("path")?.let { path ->
            try {
                textView.text = File(path).readText()
            } catch (e: Exception) {
                textView.text = "Error reading file: ${e.message}"
            }
        }
    }
}