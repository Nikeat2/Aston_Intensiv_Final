package com.example.astonfinalproject.domain.headlines

import com.example.astonfinalproject.data.data.models.headlines.Article

interface OnArticleClick {

    fun onClick(position: Int, article: Article)

}