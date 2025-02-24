package com.example.astonfinalproject.presentation.sources.data

import androidx.fragment.app.FragmentManager
import com.example.astonfinalproject.ErrorFragment
import com.example.astonfinalproject.R
import com.example.astonfinalproject.data.data.models.sources.Source
import com.example.astonfinalproject.presentation.sources.domain.GetSources
import com.example.astonfinalproject.presentation.sources.presentation.SourcesAdapter
import com.example.astonfinalproject.retrofit.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetSourcesImpl : GetSources {

    private val getNewsBySourceApi = RetrofitInstance.sourceRetrofitService

    override suspend fun execute(
        listOfSources: MutableList<Source>,
        adapter: SourcesAdapter,
        fragmentManager: FragmentManager
    ) {
        try {
            val result = getNewsBySourceApi.getSources()
            val sources = result.sources.map {
                Source(
                    id = it.id,
                    category = it.category,
                    country = it.country,
                    description = it.description,
                    language = it.language,
                    name = it.name,
                    url = it.url
                )
            }
            withContext(Dispatchers.Main) {
                listOfSources.addAll(sources)
                println("New list: $listOfSources")
                adapter.notifyDataSetChanged()
            }
        } catch (e: Exception) {
            fragmentManager.beginTransaction().replace(R.id.main, ErrorFragment()).commit()
        }
    }
}