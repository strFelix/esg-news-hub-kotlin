package br.com.strfelix.esg_news_hub_kotlin.model

data class Article(
    val title: String?,
    val description: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?,
    val url: String?
)