package br.com.strfelix.esg_news_hub_kotlin.repository.news

import br.com.strfelix.esg_news_hub_kotlin.instances.RetrofitInstance
import br.com.strfelix.esg_news_hub_kotlin.model.Article
import br.com.strfelix.esg_news_hub_kotlin.BuildConfig

class NewsRepository: INewsRepository {

    private val api = RetrofitInstance.api
    private val apiKey = BuildConfig.NEWS_API_KEY

    override suspend fun getAllNews(): List<Article> {
        return try {
            val response = api.getAllNews(apiKey = apiKey)
            response.articles
        } catch (e: Exception) {
            emptyList()
        }
    }
}