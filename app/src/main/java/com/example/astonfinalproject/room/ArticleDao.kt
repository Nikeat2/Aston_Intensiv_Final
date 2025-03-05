package com.example.astonfinalproject.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.astonfinalproject.data.data.models.headlines.Article

@Dao
interface ArticleDao {

    @Query("SELECT * FROM FAVORITE_ARTICLES")
    suspend fun getAllArticles(): List<Article>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnArticle(article: Article)

    @Delete
    suspend fun deleteAll(list: List<Article>)


}