package com.example.astonfinalproject.presentation.headlines.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.astonfinalproject.R
import com.example.astonfinalproject.data.data.models.headlines.Article
import com.example.astonfinalproject.domain.headlines.OnArticleClick

class HeadlinesAdapter(
    private val onArticleClickListener: OnArticleClick
) :
    ListAdapter<Article, HeadlinesAdapter.HeadlinesViewHolder>(HeadlinesDiffCallBack()) {

    inner class HeadlinesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                onArticleClickListener.onClick(
                    position = position,
                    article = getItem(position)
                )
            }
        }

        val textViewHeadlinesNameOfSource: TextView =
            view.findViewById(R.id.rvHeadlinesNameOfSourceTextView)
        val imageViewHeadline: ImageView = view.findViewById(R.id.rvHeadlinesImageView)
        val imageViewOfSource: ImageView = view.findViewById(R.id.rvHeadlinesImageViewOfSource)
        val textViewHeadline: TextView = view.findViewById(R.id.rvHeadlinesHeadlineTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeadlinesViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.headlines_recycler_view_item_layout, parent, false)
        return HeadlinesViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    override fun onBindViewHolder(holder: HeadlinesViewHolder, position: Int) {
        val article = currentList[position]
        holder.textViewHeadline.text = article.title
        holder.textViewHeadlinesNameOfSource.text = article.headlinesSource.name
        Glide.with(holder.itemView.context).load(article.urlToImage).centerCrop()
            .into(holder.imageViewHeadline)
        holder.imageViewOfSource.setImageResource(R.drawable.cnn_logo_jpg)
    }

    fun filter(query: String) {
        val filteredList = if (query.isEmpty()) {
            currentList
        } else {
            currentList.filter { article ->
                article.title.contains(query, ignoreCase = true)
            }
        }
        submitList(filteredList)
    }
}