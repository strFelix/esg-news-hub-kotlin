package br.com.strfelix.esg_news_hub_kotlin.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun InitialScreen(navController: NavController){
    Box(modifier = Modifier
        .background(color = MaterialTheme.colorScheme.background)
        .fillMaxSize()
    )
}

@Preview
@Composable
fun InitialScreenPreview(){
    InitialScreen(rememberNavController())
}