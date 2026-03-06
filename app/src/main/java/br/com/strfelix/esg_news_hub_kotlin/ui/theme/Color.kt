package br.com.strfelix.esg_news_hub_kotlin.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush


val Black17 = Color(0xFF171717)
val GrayE8 = Color(0xFFE8E8E8)
val GrayAD = Color(0xFFADADAD)
val GrayF3 = Color(0xFFF3F3F3)
val Gray9B = Color(0xFF9B9B9B)
val Yellow = Color(0xFFEEC213)

// cores com opacidade
val Black17_60Percent = Black17.copy(alpha = 0.60f)
val Black17_20Percent = Black17.copy(alpha = 0.20f)
val GrayE8_20Percent = GrayE8.copy(alpha = 0.20f)
val BlackPure_02Percent = Color(0xFF000000).copy(alpha = 0.02f)

// gradiente
val GradientStart = Color(0xFFCDA505)
val GradientEnd = Color(0xFFF8CF2B)

val EsgBackgroundGradient = Brush.verticalGradient(
    colors = listOf(GradientStart, GradientEnd)
)