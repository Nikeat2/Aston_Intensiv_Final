package com.example.astonfinalproject.data.data.models.headlines

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class HeadlinesSource(
    val id: String?,
    val name: String
) : Parcelable
