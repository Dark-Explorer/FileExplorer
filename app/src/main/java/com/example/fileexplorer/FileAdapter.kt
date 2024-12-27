package com.example.fileexplorer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import java.io.File

class FileAdapter(private val listener: OnFileClickListener) :
    ListAdapter<File, FileAdapter.FileViewHolder>(FileDiffCallback()) {

    interface OnFileClickListener {
        fun onFileClick(file: File)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_file, parent, false)
        return FileViewHolder(view)
    }

    override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class FileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameView: TextView = itemView.findViewById(R.id.nameView)

        fun bind(file: File) {
            nameView.text = file.name
            itemView.setOnClickListener { listener.onFileClick(file) }
        }
    }

    class FileDiffCallback : DiffUtil.ItemCallback<File>() {
        override fun areItemsTheSame(oldItem: File, newItem: File) =
            oldItem.absolutePath == newItem.absolutePath

        override fun areContentsTheSame(oldItem: File, newItem: File) =
            oldItem.lastModified() == newItem.lastModified()
    }
}