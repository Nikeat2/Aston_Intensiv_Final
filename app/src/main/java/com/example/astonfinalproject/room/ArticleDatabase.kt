package com.example.astonfinalproject.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.astonfinalproject.data.data.models.headlines.Article

@Database(
    entities = [Article::class],
    version = 1
)
abstract class ArticleDatabase: RoomDatabase() {
    abstract fun getArticleDao(): ArticleDao
}