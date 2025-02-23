package com.example.astonfinalproject.data.data.models.headlines

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HeadlinesSource(
    val id: String?,
    val name: String
) : Parcelable
