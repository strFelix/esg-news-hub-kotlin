package br.com.strfelix.esg_news_hub_kotlin.screens.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CategoryButtonComponent(
    text: String,
    selected: Boolean = false,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .width(110.dp)
            .height(38.dp)
            .clickable() { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = if (selected)
                MaterialTheme.colorScheme.primary
            else
                Color.Transparent
        ),
        border = if (selected){
            BorderStroke(
                1.dp,
                MaterialTheme.colorScheme.primary
            )
        } else {
            BorderStroke(
                1.dp,
                Color.LightGray
            )
        }
    ) {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onBackground,
            )
        }
    }
}