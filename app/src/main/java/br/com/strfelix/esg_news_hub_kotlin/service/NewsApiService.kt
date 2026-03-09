package br.com.strfelix.esg_news_hub_kotlin.service

import br.com.strfelix.esg_news_hub_kotlin.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("v2/everything")
    suspend fun getAllNews(
        @Query("q") query: String = "ESG",
        @Query("searchIn") searchIn: String = "title,description",
        @Query("language") language: String = "en",
        @Query("sortBy") sortBy: String = "publishedAt",
        @Query("pageSize") pageSize: Int = 100,
        @Query("apiKey") apiKey: String
    ): NewsResponse
}