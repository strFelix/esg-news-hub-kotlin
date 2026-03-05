package br.com.strfelix.esg_news_hub_kotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import br.com.strfelix.esg_news_hub_kotlin.routes.NavigationRoutes
import br.com.strfelix.esg_news_hub_kotlin.ui.theme.EsgnewshubkotlinTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EsgnewshubkotlinTheme{
                NavigationRoutes()
            }
        }
    }
}
