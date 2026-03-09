package br.com.strfelix.esg_news_hub_kotlin.repository.news

import br.com.strfelix.esg_news_hub_kotlin.model.Article

interface INewsRepository {
    suspend fun getAllNews(): List<Article>
}