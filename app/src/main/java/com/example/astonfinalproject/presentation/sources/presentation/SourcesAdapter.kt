package com.example.astonfinalproject.presentation.sources.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.astonfinalproject.R
import com.example.astonfinalproject.data.data.models.sources.Source
import com.example.astonfinalproject.presentation.sources.data.OnSourceClick

private const val CNN = "CNN"
private const val THE_NEW_YORK_TIMES = "The New York Times"
private const val FOX_NEWS = "Fox News"
private const val DAILY_MAIL = "Daily mail"
private const val BLOOMBERG = "Bloomberg"

class SourcesAdapter(
    private val listOfSources: List<Source>,
    private val onSourceClickListener: OnSourceClick
) :
    ListAdapter<Source, SourcesAdapter.SourceViewHolder>(
        SourcesDiffCallBack()
    ) {

    inner class SourceViewHolder(view: View) : ViewHolder(view) {

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                onSourceClickListener.onSourceClick(listOfSources[position])
            }
        }

        val sourceImageView: ImageView = view.findViewById(R.id.sourceImageView)
        val sourceTitleTextView: TextView = view.findViewById(R.id.sourceTitleTextView)
        val sourceTopicTextView: TextView = view.findViewById(R.id.sourceTopicTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SourceViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.sources_item, parent, false)
        return SourceViewHolder(itemView)
    }

    override fun getItemCount(): Int = listOfSources.size

    override fun onBindViewHolder(holder: SourceViewHolder, position: Int) {
        val source = listOfSources[position]
        holder.sourceTitleTextView.text = source.name
        holder.sourceTopicTextView.text = source.category
        holder.sourceImageView.setImageResource(
            when (source.name) {
                CNN -> R.drawable.cnn_logo_jpg
                THE_NEW_YORK_TIMES -> R.drawable.the_new_york_times_logo
                FOX_NEWS -> R.drawable.fox_news_logo
                DAILY_MAIL -> R.drawable.daily_mail_logo
                BLOOMBERG -> R.drawable.bloomberg_logo
                else -> R.drawable.other_sources_logo
            }
        )
    }
}