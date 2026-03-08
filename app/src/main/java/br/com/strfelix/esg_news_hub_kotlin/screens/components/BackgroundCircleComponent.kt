package br.com.strfelix.esg_news_hub_kotlin.screens.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import br.com.strfelix.esg_news_hub_kotlin.R

enum class BackgroundPosition {
    Top,
    Bottom
}

@Composable
fun BoxScope.BackgroundVector(
    drawable: Int = R.drawable.circles_vector,
    position: BackgroundPosition
) {

    Image(
        painter = painterResource(id = drawable),
        contentDescription = null,
        modifier = Modifier
            .align(
                if (position == BackgroundPosition.Top)
                    Alignment.TopCenter
                else
                    Alignment.BottomCenter
            )
            .rotate(
                if (position == BackgroundPosition.Bottom) 180f else 0f
            )
            .graphicsLayer(scaleX = 1.5f, scaleY = 1.5f)
    )
}