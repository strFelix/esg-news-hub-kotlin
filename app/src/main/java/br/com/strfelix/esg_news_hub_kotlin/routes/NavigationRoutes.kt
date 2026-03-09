package br.com.strfelix.esg_news_hub_kotlin.routes

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.strfelix.esg_news_hub_kotlin.screens.HomeScreen
import br.com.strfelix.esg_news_hub_kotlin.screens.InitialScreen
import br.com.strfelix.esg_news_hub_kotlin.screens.LoginScreen
import br.com.strfelix.esg_news_hub_kotlin.screens.SignUpScreen

@Composable
fun NavigationRoutes() {
    val navController = rememberNavController();
    NavHost(
        navController = navController,
        startDestination = Destination.HomeScreen.route
    ) {
        composable(Destination.InitialScreen.route){ InitialScreen(navController) }
        composable(Destination.SignupScreen.route) { SignUpScreen(navController) }
        composable(Destination.LoginScreen.route) { LoginScreen(navController) }
        composable(Destination.HomeScreen.route) { HomeScreen(navController) }
        composable(Destination.NewsScreen.route) { HomeScreen(navController) }
    }
}