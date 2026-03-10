package br.com.strfelix.esg_news_hub_kotlin.routes

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.strfelix.esg_news_hub_kotlin.screens.HomeScreen
import br.com.strfelix.esg_news_hub_kotlin.screens.InitialScreen
import br.com.strfelix.esg_news_hub_kotlin.screens.LoginScreen
import br.com.strfelix.esg_news_hub_kotlin.screens.NewsScreen
import br.com.strfelix.esg_news_hub_kotlin.screens.SignupScreen
import br.com.strfelix.esg_news_hub_kotlin.viewModel.NewsViewModel

@Composable
fun NavigationRoutes() {
    val navController = rememberNavController()
    val newsViewModel: NewsViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Destination.InitialScreen.route
    ) {
        composable(Destination.InitialScreen.route){ InitialScreen(navController) }
        composable(Destination.SignupScreen.route) { SignupScreen(navController) }
        composable(Destination.LoginScreen.route) { LoginScreen(navController) }
        composable(Destination.HomeScreen.route) { HomeScreen(navController, newsViewModel) }
        
        composable(route = Destination.NewsScreen.route) {
            NewsScreen(navController, newsViewModel)
        }
    }
}