package br.com.strfelix.esg_news_hub_kotlin.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import br.com.strfelix.esg_news_hub_kotlin.model.User

class AuthViewModel : ViewModel() {
    var loggedUser by mutableStateOf<User?>(null)
}