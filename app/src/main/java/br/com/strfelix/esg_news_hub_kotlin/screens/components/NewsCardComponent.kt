package br.com.strfelix.esg_news_hub_kotlin.screens.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.strfelix.esg_news_hub_kotlin.ui.theme.EsgnewshubkotlinTheme
import coil.compose.AsyncImage

@Composable
fun NewsCard(
    imageUrl: String?,
    title: String,
    description: String,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {

        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = imageUrl ?: "https://via.placeholder.com/150",
                contentDescription = title,
                modifier = Modifier
                    .size(100.dp)
                    .clip(
                        RoundedCornerShape(
                            topStart = 12.dp,
                            bottomStart = 12.dp
                        )
                    ),
                contentScale = ContentScale.Crop,
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
@Preview
fun NewsCardPreview() {
    EsgnewshubkotlinTheme() {
        NewsCard(
            imageUrl = "https://via.placeholder.com/150",
            title = "Sample Title",
            description = "Sample Description"
        )
    }
}