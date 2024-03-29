package com.myproject.gosport.presentation.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.myproject.gosport.R

data class AppColors(
    val background: Color,
    val pink: Color,
    val dimPink: Color,
    val gray: Color,
    val lightGray: Color,
    val black: Color,
    val white: Color,
    val line: Color,
    val disabled: Color,
)

data class AppShape(
    val small: Shape,
    val big: Shape,
)

data class AppTypography(
    val title: TextStyle,
    val description: TextStyle,
    val city: TextStyle,
    val bottomButton: TextStyle,
    val category: TextStyle,
    val selectCategory: TextStyle,
    val button: TextStyle,
)

object AppThemeCommon {
    val colors: AppColors
        @Composable
        get() = LocalAppColors.current

    val typography: AppTypography
        @Composable
        get() = LocalAppTypography.current

    val shape: AppShape
        @Composable
        get() = LocalAppShape.current
}

val Roboto = FontFamily(
    Font(R.font.roboto_medium, FontWeight.Medium),
    Font(R.font.roboto_bold, FontWeight.Bold),
)

val SFUIDisplay = FontFamily(
    Font(R.font.sf_ui_display_regular, FontWeight.Normal),
    Font(R.font.sf_ui_display_semibold, FontWeight.SemiBold),
)

val Inter = FontFamily(
    Font(R.font.inter_medium, FontWeight.Medium),
)

val LocalAppColors = staticCompositionLocalOf<AppColors> {
    error("No colors provided")
}

val LocalAppTypography = staticCompositionLocalOf<AppTypography> {
    error("No font provided")
}

val LocalAppShape = staticCompositionLocalOf<AppShape> {
    error("No shapes provided")
}
