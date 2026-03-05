package br.com.strfelix.esg_news_hub_kotlin.routes

sealed class Destination(val route: String){
    object InitialScreen: Destination("initial")
    object SignupScreen: Destination("signup")
    object LoginScreen: Destination("login")
    object HomeScreen: Destination("home")
    object NewsScreen: Destination("newsScreen")
}