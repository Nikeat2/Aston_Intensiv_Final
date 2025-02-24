package com.example.astonfinalproject.presentation.sources.presentation

import androidx.recyclerview.widget.DiffUtil
import com.example.astonfinalproject.data.data.models.sources.Source

class SourcesDiffCallBack:  DiffUtil.ItemCallback<Source>() {

    override fun areItemsTheSame(oldItem: Source, newItem: Source): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Source, newItem: Source): Boolean {
        return oldItem == newItem
    }
}
