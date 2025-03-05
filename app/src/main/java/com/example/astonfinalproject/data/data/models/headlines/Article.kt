package com.example.astonfinalproject.data.data.models.headlines

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = FAVORITE_ARTICLES)
data class Article (
    @Embedded
    val headlinesSource: HeadlinesSource,
    val author: String?,
    val title: String,
    val description: String?,
    @PrimaryKey
    val url: String,
    val urlToImage: String?,
    val publishedAt: String,
    val content: String?
) : Parcelable

const val FAVORITE_ARTICLES = "favorite_articles"