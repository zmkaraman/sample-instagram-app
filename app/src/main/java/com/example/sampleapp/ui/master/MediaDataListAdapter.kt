package com.example.sampleapp.ui.master

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sampleapp.databinding.ItemMediaBinding
import com.example.sampleapp.network.model.MediaData

class MediaDataListAdapter(private val clickListener: MediaDataListener): ListAdapter<MediaData, MediaDataListAdapter.MediaDataViewHolder>(MediaDataDiffCallback) {

    class MediaDataListener (val clickListener: (mediaData: MediaData) -> Unit) {
        fun onClick(mediaData: MediaData) = clickListener(mediaData)
    }

    class MediaDataViewHolder(private var binding: ItemMediaBinding):
            RecyclerView.ViewHolder(binding.root) {

        fun bind(listener: MediaDataListener, mediaData: MediaData) {
            binding.media = mediaData
            binding.clickListener = listener
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }


        companion object {
            fun from(parent: ViewGroup): MediaDataViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemMediaBinding.inflate(layoutInflater, parent, false)
                return MediaDataViewHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaDataViewHolder {
        return MediaDataViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MediaDataViewHolder, position: Int) {
        holder.bind(clickListener, getItem(position))
    }
}

class MediaDataDiffCallback {
    companion object DiffCallback : DiffUtil.ItemCallback<MediaData>() {
        override fun areItemsTheSame(oldItem: MediaData, newItem: MediaData): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: MediaData, newItem: MediaData): Boolean {
            return newItem == oldItem
        }
    }
}




