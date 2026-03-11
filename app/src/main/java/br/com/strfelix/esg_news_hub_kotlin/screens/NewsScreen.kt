package br.com.strfelix.esg_news_hub_kotlin.screens

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.strfelix.esg_news_hub_kotlin.R
import br.com.strfelix.esg_news_hub_kotlin.screens.components.BackgroundPosition
import br.com.strfelix.esg_news_hub_kotlin.screens.components.BackgroundVector
import br.com.strfelix.esg_news_hub_kotlin.screens.components.TopBar
import br.com.strfelix.esg_news_hub_kotlin.viewModel.AuthViewModel
import br.com.strfelix.esg_news_hub_kotlin.viewModel.NewsViewModel
import coil.compose.AsyncImage
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun NewsScreen(
    navController: NavController,
    newsViewModel: NewsViewModel,
    authViewModel: AuthViewModel
    ){
    val article = newsViewModel.selectedArticle

    Box(modifier = Modifier.fillMaxSize()){
        BackgroundVector(position = BackgroundPosition.Top)
        BackgroundVector(position = BackgroundPosition.Bottom)
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                TopBar(userName = authViewModel.loggedUser?.name ?: "Guest")
            },
            bottomBar = {
                NavBarBottom(navController = navController)
            },
        ) { paddingValues ->
            if (article != null) {
                Content(
                    modifier = Modifier.padding(paddingValues),
                    navController = navController,
                    urlToImage = article.urlToImage,
                    title = article.title,
                    description = article.description,
                    publishedAt = article.publishedAt,
                    content = article.content,
                    url = article.url
                )
            } else {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Article not found.", style = MaterialTheme.typography.headlineMedium)
                }
            }
        }
    }
}

@Composable
fun Content(
    modifier: Modifier = Modifier,
    navController: NavController,
    urlToImage: String?,
    title: String?,
    description: String?,
    publishedAt: String?,
    content: String?,
    url: String?
) {
    val context = LocalContext.current
    val formattedDate = remember(publishedAt) {
        formatPublishedAt(publishedAt)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable { navController.popBackStack() }
        ) {

            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.Black
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Back",
                style = MaterialTheme.typography.bodyMedium,
                textDecoration = TextDecoration.Underline
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        AsyncImage(
            model = urlToImage,
            placeholder = painterResource(id = R.drawable.news_placeholder),
            error = painterResource(id = R.drawable.news_placeholder),
            contentDescription = title,
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = title ?: "",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            softWrap = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = formattedDate,
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray,
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = description ?: "",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium,
            softWrap = true

        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = content ?: "",
            style = MaterialTheme.typography.bodyMedium,
            softWrap = true
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (!url.isNullOrBlank()) {
            Button(
                onClick = {
                    try {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        context.startActivity(intent)
                    } catch (e: Exception) {
                        Toast.makeText(context, R.string.not_found_browser, Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(R.string.read_more))
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

private fun formatPublishedAt(dateString: String?): String {
    if (dateString.isNullOrBlank()) return ""
    return try {
        val zonedDateTime = ZonedDateTime.parse(dateString)
        val formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy", Locale.US)
        zonedDateTime.format(formatter)
    } catch (e: Exception) {
        dateString
    }
}
