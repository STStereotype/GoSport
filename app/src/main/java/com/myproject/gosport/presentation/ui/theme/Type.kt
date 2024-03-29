package com.myproject.gosport.presentation.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// FontWeight
private val regular = FontWeight(400)
private val medium = FontWeight(500)
private val semiBold = FontWeight(600)
private val bold = FontWeight(700)

val Typography = AppTypography(
    title = TextStyle(
        fontFamily = Roboto,
        fontWeight = bold,
        fontSize = 16.sp,
        color = BaseLightPalette.black
    ),
    description = TextStyle(
        fontFamily = SFUIDisplay,
        fontWeight = regular,
        fontSize = 14.sp,
        color = BaseLightPalette.gray
    ),
    city = TextStyle(
        fontFamily = Roboto,
        fontWeight = regular,
        fontSize = 16.sp,
        color = BaseLightPalette.black
    ),
    bottomButton = TextStyle(
        fontFamily = Inter,
        fontWeight = medium,
        fontSize = 12.sp,
    ),
    category = TextStyle(
        fontFamily = SFUIDisplay,
        fontWeight = regular,
        fontSize = 13.sp,
        color = BaseLightPalette.lightGray
    ),
    selectCategory = TextStyle(
        fontFamily = SFUIDisplay,
        fontWeight = semiBold,
        fontSize = 13.sp,
        color = BaseLightPalette.pink
    ),
    button = TextStyle(
        fontFamily = SFUIDisplay,
        fontWeight = regular,
        fontSize = 13.sp,
        color = BaseLightPalette.pink
    ),
)
