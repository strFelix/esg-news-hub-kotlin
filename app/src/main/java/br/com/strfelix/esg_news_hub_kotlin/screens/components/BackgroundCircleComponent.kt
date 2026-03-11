package br.com.strfelix.esg_news_hub_kotlin.screens.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import br.com.strfelix.esg_news_hub_kotlin.R

enum class BackgroundPosition {
    Top,
    Bottom
}

@Composable
fun BoxScope.BackgroundVector(
    drawable: Int? = null,
    position: BackgroundPosition
) {
    val resourceId = drawable ?: if (position == BackgroundPosition.Top) {
        R.drawable.circle_top
    } else {
        R.drawable.circle_bottom
    }

    Image(
        painter = painterResource(id = resourceId),
        contentDescription = null,
        modifier = Modifier
            .align(
                if (position == BackgroundPosition.Top)
                    Alignment.TopCenter
                else
                    Alignment.BottomCenter
            )
            .graphicsLayer(scaleX = 1.5f, scaleY = 1.5f)
    )
}