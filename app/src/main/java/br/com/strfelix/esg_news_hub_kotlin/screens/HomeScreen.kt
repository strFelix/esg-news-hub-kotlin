package br.com.strfelix.esg_news_hub_kotlin.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.strfelix.esg_news_hub_kotlin.R
import br.com.strfelix.esg_news_hub_kotlin.model.Article
import br.com.strfelix.esg_news_hub_kotlin.repository.news.NewsRepository
import br.com.strfelix.esg_news_hub_kotlin.routes.Destination
import br.com.strfelix.esg_news_hub_kotlin.screens.components.BackgroundPosition
import br.com.strfelix.esg_news_hub_kotlin.screens.components.BackgroundVector
import br.com.strfelix.esg_news_hub_kotlin.screens.components.CategoryButtonComponent
import br.com.strfelix.esg_news_hub_kotlin.screens.components.NewsCard
import br.com.strfelix.esg_news_hub_kotlin.screens.components.TopBar
import br.com.strfelix.esg_news_hub_kotlin.ui.theme.EsgnewshubkotlinTheme
import br.com.strfelix.esg_news_hub_kotlin.viewModel.AuthViewModel
import br.com.strfelix.esg_news_hub_kotlin.viewModel.NewsViewModel

@Composable
fun HomeScreen(navController: NavController, newsViewModel: NewsViewModel, authViewModel: AuthViewModel) {
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
            Content(modifier = Modifier.padding(paddingValues), navController, newsViewModel)
        }
    }
}

@Composable
fun Content(modifier: Modifier = Modifier, navController: NavController, newsViewModel: NewsViewModel) {
    val newsRepository = remember { NewsRepository() }

    var fullApiArticles by remember { mutableStateOf<List<Article>>(emptyList()) }
    var displayCount by remember { mutableStateOf(10) }

    LaunchedEffect(Unit) {
        fullApiArticles = newsRepository.getAllNews()
    }

    var searchText by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<String?>(null) }

    val environmentKeywords = listOf(
        "climate", "environment", "carbon", "sustainability", "renewable"
    )
    val socialKeywords = listOf(
        "women", "education", "community", "diversity", "inclusion", "social"
    )
    val governanceKeywords = listOf(
        "governance", "compliance", "board", "transparency", "ethics"
    )

    // Filtro global sobre os dados carregados da API
    val allFilteredArticles = fullApiArticles.filter { article ->
        val text = "${article.title} ${article.description}".lowercase()

        val matchesSearch =
            article.title?.contains(searchText, true) == true ||
                    article.description?.contains(searchText, true) == true

        val matchesCategory = when (selectedCategory) {
            "environment" -> environmentKeywords.any { text.contains(it) }
            "social" -> socialKeywords.any { text.contains(it) }
            "governance" -> governanceKeywords.any { text.contains(it) }
            else -> true
        }

        matchesSearch && matchesCategory
    }

    // Lista final que será renderizada (paginação local)
    val articlesToDisplay = allFilteredArticles.take(displayCount)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
    ) {
        NewsFilterSection(
            selectedCategory = selectedCategory,
            onCategoryClick = { category ->
                selectedCategory =
                    if (selectedCategory == category) null
                    else category
                displayCount = 10 // Reseta a exibição ao trocar categoria
            },
            searchText = searchText,
            onSearchChange = { 
                searchText = it
                displayCount = 10 // Reseta a exibição ao digitar
            }

        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {

            itemsIndexed(articlesToDisplay) { index, article ->
                NewsCard(
                    imageUrl = article.urlToImage,
                    title = article.title ?: "",
                    description = article.description ?: "",
                    modifier = Modifier.clickable {
                        newsViewModel.selectedArticle = article
                        navController.navigate(Destination.NewsScreen.route)
                    }
                )
                Spacer(modifier = Modifier.height(12.dp))

                // Gatilho para carregar mais 10 itens localmente
                if (index == articlesToDisplay.lastIndex && displayCount < allFilteredArticles.size) {
                    displayCount += 10
                }
            }
        }
    }
}

@Composable
fun NewsFilterSection(
    selectedCategory: String?,
    onCategoryClick: (String) -> Unit = {},
    searchText: String,
    onSearchChange: (String) -> Unit
) {

    Column(modifier = Modifier.padding(0.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            TextField(
                value = searchText,
                onValueChange = {
                    onSearchChange(it)
                },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,

                    focusedIndicatorColor = Color.LightGray,
                    unfocusedIndicatorColor = Color.LightGray,

                    focusedLabelColor = Color.Gray,
                    unfocusedLabelColor = Color.Gray
                ),
                singleLine = true,
                trailingIcon = {
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "",
                            tint = Color.Gray
                        )
                    }
                },
                placeholder = {
                    Text(
                        text = stringResource(R.string.search_an_article),
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Gray,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            )

            Spacer(modifier = Modifier.width(8.dp))

            IconButton(
                onClick = {}
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(R.string.search),
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))


        // Title
        Text(
            text = stringResource(R.string.filter_by_category),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Categories
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            CategoryButtonComponent(
                text = stringResource(R.string.category_environment),
                selected = selectedCategory == "environment",
                onClick = { onCategoryClick("environment") }
            )

            CategoryButtonComponent(
                text = stringResource(R.string.category_social),
                selected = selectedCategory == "social",
                onClick = { onCategoryClick("social") }
            )

            CategoryButtonComponent(
                text = stringResource(R.string.category_governance),
                selected = selectedCategory == "governance",
                onClick = { onCategoryClick("governance") }
            )
        }
    }
}

data class BottomNavigationItem(
    val icon: Any,
    val nav: String
)
@Composable
fun NavBarBottom(modifier: Modifier = Modifier, navController: NavController) {
    val items = listOf(
        BottomNavigationItem(icon = R.drawable.home_icon,  nav = "home"),
        BottomNavigationItem(icon = R.drawable.exit_icon, nav = "login"),
    )

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primary,
        modifier = modifier
    ) {
        items.forEachIndexed { index, item ->

            NavigationBarItem(
                selected = false,
                onClick = {navController.navigate(item.nav)},
                icon = {
                    when (val icon = item.icon) {
                        is ImageVector -> Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.size(20.dp)
                        )
                        is Int -> Icon(
                            painter = painterResource(id = icon),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onBackground,
                            modifier = if (item.icon == R.drawable.exit_icon) {
                                Modifier.size(24.dp)
                            } else {
                                Modifier.size(17.dp)
                            }
                        )
                    }
                }
            )

            if (index < items.lastIndex) {
                VerticalDivider(
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .height(30.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun NavBarBottomPreview() {
    EsgnewshubkotlinTheme {
        NavBarBottom(navController = rememberNavController())
    }
}

@Preview
@Composable
fun TopBarPreview() {
    EsgnewshubkotlinTheme {
        TopBar("Guest")
    }
}

@Preview
@Composable
fun NewsFilterSectionPreview() {
    EsgnewshubkotlinTheme {
        NewsFilterSection(selectedCategory = null, onCategoryClick = {}, searchText = "", onSearchChange = {})
    }
}