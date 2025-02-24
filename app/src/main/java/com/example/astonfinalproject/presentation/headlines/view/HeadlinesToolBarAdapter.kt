package com.example.astonfinalproject.presentation.headlines.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.astonfinalproject.R
import com.example.astonfinalproject.data.data.models.headlines.HeadlinesToolBarTab

class HeadlinesToolBarAdapter(
    private val listOfTabs: List<HeadlinesToolBarTab>,
    private val onItemClick: (HeadlinesToolBarTab) -> Unit
) :
    RecyclerView.Adapter<HeadlinesToolBarAdapter.TabViewHolder>() {

    inner class TabViewHolder(itemView: View) : ViewHolder(itemView) {
        val tabTextView: TextView = itemView.findViewById(R.id.headlinesToolBarTabText)
        val tabImageView: ImageView = itemView.findViewById(R.id.headlinesToolBarIconImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TabViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.headlines_toolbar_item, parent, false)
        return TabViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return listOfTabs.size
    }

    override fun onBindViewHolder(holder: TabViewHolder, position: Int) {
        val tab = listOfTabs[position]
        holder.tabTextView.text = tab.tabText
        holder.tabImageView.setImageResource(tab.tabImage)
        holder.itemView.setOnClickListener {
            onItemClick(tab)
        }
    }
}