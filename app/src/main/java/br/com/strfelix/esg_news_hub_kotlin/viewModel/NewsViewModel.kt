package br.com.strfelix.esg_news_hub_kotlin.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import br.com.strfelix.esg_news_hub_kotlin.model.Article

class NewsViewModel : ViewModel() {
    var selectedArticle by mutableStateOf<Article?>(null)
}