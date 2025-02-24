package com.example.astonfinalproject.presentation.sources.sources_retrofit

import com.example.astonfinalproject.data.data.models.sources.SourceApiResponse
import retrofit2.http.GET

interface SourcesRetrofitService {

    @GET("top-headlines/sources?apiKey=bd7cecf5e7a842eeb0b4bddf72eedce4")
    suspend fun getSources(): SourceApiResponse

}