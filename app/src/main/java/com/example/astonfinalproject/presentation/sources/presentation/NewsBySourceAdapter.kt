package com.example.astonfinalproject.presentation.sources.presentation

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
import com.example.astonfinalproject.presentation.headlines.ui.HeadlinesDiffCallBack

class NewsBySourceAdapter(
    private val onArticleClickListener: OnArticleClick
) :
    ListAdapter<Article, NewsBySourceAdapter.NewsBySourceViewHolder>((HeadlinesDiffCallBack())) {

    inner class NewsBySourceViewHolder(view: View) : RecyclerView.ViewHolder(view) {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsBySourceViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.headlines_recycler_view_item_layout, parent, false)
        return NewsBySourceViewHolder(itemView)
    }

    override fun getItemCount(): Int = currentList.size

    override fun onBindViewHolder(holder: NewsBySourceViewHolder, position: Int) {
        val article = currentList[position]
        holder.textViewHeadline.text = article.title
        holder.textViewHeadlinesNameOfSource.text = article.headlinesSource.name
        Glide.with(holder.itemView.context).load(article.urlToImage).centerCrop()
            .into(holder.imageViewHeadline)
        holder.imageViewOfSource.setImageResource(R.drawable.cnn_logo_jpg)
    }
}