package br.com.strfelix.esg_news_hub_kotlin.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SupervisedUserCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.strfelix.esg_news_hub_kotlin.R
import br.com.strfelix.esg_news_hub_kotlin.model.Article
import br.com.strfelix.esg_news_hub_kotlin.screens.components.BackgroundPosition
import br.com.strfelix.esg_news_hub_kotlin.screens.components.BackgroundVector
import br.com.strfelix.esg_news_hub_kotlin.screens.components.CategoryButtonComponent
import br.com.strfelix.esg_news_hub_kotlin.screens.components.NewsCard
import br.com.strfelix.esg_news_hub_kotlin.ui.theme.EsgnewshubkotlinTheme

@Composable
fun HomeScreen(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()){
        BackgroundVector(position = BackgroundPosition.Top)
        BackgroundVector(position = BackgroundPosition.Bottom)
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                TopBar()
            },
            bottomBar = {
                NavBarBottom(navController = navController)
            },
        ) { paddingValues ->
            Content(modifier = Modifier.padding(paddingValues), navController)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    TopAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        ),
        title = {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Welcome to ESG News,",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "<name_user>",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold
                    )
                }

                Card(
                    shape = CircleShape,
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Transparent
                    ),
                    border = BorderStroke(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.size(64.dp)
                ) {
                    Image(
                        imageVector = Icons.Default.SupervisedUserCircle,
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    )
}

@Composable
fun Content(modifier: Modifier = Modifier, navController: NavController) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
    ) {
        val mockArticles = listOf(
            Article(
                title = "SBI rolls out $500 million social loan programme",
                description = "State Bank of India has launched a USD 500 million syndicated social term loan focused on women empowerment.",
                urlToImage = "https://www.livemint.com/lm-img/img/2026/03/07/1600x900/logo/STATE-BANK-INDIA-FUNDRAISING--0_1686300346067_1772877998515.JPG"
            ),
            Article(
                title = "Companies increase ESG investments",
                description = "Organizations are investing more in environmental and social governance strategies.",
                urlToImage = "https://journals.plos.org/plosone/article/figure/image?id=10.1371/journal.pone.0344283.g001&size=inline"
            )
        )
        var searchText by remember { mutableStateOf("") }
        var selectedCategory by remember { mutableStateOf<String?>(null) }

        // MVP: Keywords to filter articles <--
        // v1: AI Agent to filter articles
        val environmentKeywords = listOf(
            "climate", "environment", "carbon", "sustainability", "renewable"
        )
        val socialKeywords = listOf(
            "women", "education", "community", "diversity", "inclusion", "social"
        )
        val governanceKeywords = listOf(
            "governance", "compliance", "board", "transparency", "ethics"
        )

        val filteredArticles = mockArticles.filter { article ->

            val text = "${article.title} ${article.description}".lowercase()

            val matchesSearch =
                article.title?.contains(searchText, true) == true ||
                        article.description?.contains(searchText, true) == true

            val matchesCategory = when (selectedCategory) {

                "environment" ->
                    environmentKeywords.any { text.contains(it) }

                "social" ->
                    socialKeywords.any { text.contains(it) }

                "governance" ->
                    governanceKeywords.any { text.contains(it) }

                else -> true
            }

            matchesSearch && matchesCategory
        }

        NewsFilterSection(
            selectedCategory = selectedCategory,
            onCategoryClick = { category ->
                selectedCategory =
                    if (selectedCategory == category) null
                    else category
            },
            searchText = searchText,
            onSearchChange = { searchText = it}

        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {

            items(filteredArticles) { article ->

                NewsCard(
                    imageUrl = article.urlToImage,
                    title = article.title ?: "",
                    description = article.description ?: ""
                )
                Spacer(modifier = Modifier.height(12.dp))
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
                        text = "Search an article",
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
                    contentDescription = "Search",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))


        // Title
        Text(
            text = "Filter by category",
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
                text = "Environment",
                selected = selectedCategory == "environment",
                onClick = { onCategoryClick("environment") }
            )

            CategoryButtonComponent(
                text = "Social",
                selected = selectedCategory == "social",
                onClick = { onCategoryClick("social") }
            )

            CategoryButtonComponent(
                text = "Governance",
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
    EsgnewshubkotlinTheme() {
        NavBarBottom(navController = rememberNavController())
    }
}

@Preview
@Composable
fun TopBarPreview() {
    EsgnewshubkotlinTheme() {
        TopBar()
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    EsgnewshubkotlinTheme() {
        HomeScreen(rememberNavController())
    }
}

@Preview
@Composable
fun NewsFilterSectionPreview() {
    EsgnewshubkotlinTheme() {
        NewsFilterSection(selectedCategory = null, onCategoryClick = {}, searchText = "", onSearchChange = {})
    }
}