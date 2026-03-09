package br.com.strfelix.esg_news_hub_kotlin.model

data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)
