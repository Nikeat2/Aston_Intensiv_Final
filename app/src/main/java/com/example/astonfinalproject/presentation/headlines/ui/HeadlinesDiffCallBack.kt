package com.example.astonfinalproject.presentation.headlines.ui

import androidx.recyclerview.widget.DiffUtil
import com.example.astonfinalproject.data.data.models.headlines.Article

class HeadlinesDiffCallBack : DiffUtil.ItemCallback<Article>() {
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.url == newItem.url
    }

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }
}
