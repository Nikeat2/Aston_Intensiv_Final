package com.example.astonfinalproject.presentation.sources.domain

import androidx.fragment.app.FragmentManager
import com.example.astonfinalproject.data.data.models.sources.Source
import com.example.astonfinalproject.presentation.sources.presentation.SourcesAdapter

interface GetSources {

    suspend fun execute(
        listOfSources: MutableList<Source>,
        adapter: SourcesAdapter,
        fragmentManager: FragmentManager
    )
}