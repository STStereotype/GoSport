package com.myproject.gosport.presentation.screens

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.myproject.gosport.R
import com.myproject.gosport.presentation.screens.models.MainState
import com.myproject.gosport.presentation.ui.theme.AppThemeCommon
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    state: MainState,
    onChangeCategory: (String) -> Unit,
    onReload: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        when (state) {
            MainState.Loading -> {
                LoadingScreen()
            }

            is MainState.Success -> {
                SuccessScreen(
                    state = state,
                    onClickCategory = {
                        onChangeCategory(it)
                    }
                )
            }

            is MainState.Error -> {
                ErrorScreen(
                    state.message,
                    onReload
                )
            }
        }
    }
}

@Composable
private fun LoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFFFFFFF)),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.size(128.dp),
            painter = painterResource(id = R.drawable.logo),
            contentDescription = null
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun SuccessScreen(
    state: MainState.Success,
    onClickCategory: (categoryName: String) -> Unit
) {
    val promotions = listOf(1, 2, 3, 4, 5)
    val pageCount = promotions.size * 500
    val mainLazyListState = rememberLazyListState()
    val categoryLazyListState = rememberLazyListState()
    var currentCategoryName by rememberSaveable { mutableStateOf(state.categories[0].name) }
    val pagerState = rememberPagerState(
        initialPage = pageCount / 2,
    ) { pageCount }
    val coroutineScope = rememberCoroutineScope()
    DisposableEffect(Unit) {
        var isRunning = true
        coroutineScope.launch {
            while (isRunning) {
                try {
                    delay(5000)
                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                } catch (_: Exception) {
                }
            }
        }
        onDispose {
            isRunning = false
        }
    }
    val visibility by remember {
        derivedStateOf {
            when {
                mainLazyListState.layoutInfo.visibleItemsInfo.isNotEmpty() && mainLazyListState.firstVisibleItemIndex == 0 -> {
                    val imageSize = mainLazyListState.layoutInfo.visibleItemsInfo[0].size
                    val scrollOffset = mainLazyListState.firstVisibleItemScrollOffset

                    (imageSize.toFloat() - scrollOffset) / imageSize.toFloat()
                }

                else -> 0f
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppThemeCommon.colors.background)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(AppThemeCommon.colors.background)
                .padding(start = 16.dp, end = 16.dp, top = 40.dp, bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Москва",
                    style = AppThemeCommon.typography.city
                )
                Icon(
                    modifier = Modifier.padding(start = 8.dp),
                    tint = AppThemeCommon.colors.black,
                    painter = painterResource(id = R.drawable.arrow),
                    contentDescription = null
                )
            }
            Image(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = R.drawable.qr_code),
                contentDescription = null
            )
        }
        LazyColumn(
            state = mainLazyListState
        ) {
            item {
                HorizontalPager(
                    modifier = Modifier
                        .background(AppThemeCommon.colors.background)
                        .alpha(visibility),
                    state = pagerState,
                    pageSpacing = 15.dp,
                    contentPadding = PaddingValues(top = 16.dp, end = 48.dp),
                ) { page ->
                    promotions.getOrNull(
                        page % promotions.size
                    )?.let { content ->
                        Box(
                            modifier = Modifier
                                .padding(start = 16.dp)
                                .background(Color(0xFFAAAAAA), RoundedCornerShape(10.dp))
                                .fillMaxWidth()
                                .height(112.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "$content",
                                style = AppThemeCommon.typography.title
                            )
                        }
                    }
                }
            }
            stickyHeader {
                LazyRow(
                    state = categoryLazyListState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(GenericShape { size, _ ->
                            lineTo(size.width, 0f)
                            lineTo(size.width, Float.MAX_VALUE)
                            lineTo(0f, Float.MAX_VALUE)
                        })
                        .shadow(30.dp * (1f - visibility))
                        .background(AppThemeCommon.colors.background),
                    contentPadding = PaddingValues(
                        start = 8.dp,
                        end = 16.dp,
                        top = 24.dp,
                        bottom = 16.dp
                    ),
                ) {
                    items(state.categories.size) { index ->
                        val category = state.categories[index]
                        val isSelected = category.name == currentCategoryName
                        val color =
                            if (isSelected) AppThemeCommon.colors.dimPink
                            else AppThemeCommon.colors.white
                        val style =
                            if (isSelected) AppThemeCommon.typography.selectCategory
                            else AppThemeCommon.typography.category
                        val elevation = if (isSelected) 0.dp else 2.dp
                        Box(
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .shadow(
                                    elevation,
                                    RoundedCornerShape(6.dp),
                                    spotColor = AppThemeCommon.colors.lightGray
                                )
                                .clickable {
                                    if (!isSelected) {
                                        currentCategoryName = category.name
                                        onClickCategory(category.name)
                                    }
                                }
                                .background(
                                    color,
                                    RoundedCornerShape(6.dp)
                                ),
                        ) {
                            Text(
                                modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
                                text = category.name,
                                style = style
                            )
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color(0xFFF3F5F9))
                )
            }
            items(state.meals.size) { index ->
                val meal = state.meals[index]
                val painter = rememberAsyncImagePainter(meal.image)
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Image(
                        modifier = Modifier
                            .background(AppThemeCommon.colors.white)
                            .size(135.dp),
                        painter = painter,
                        contentDescription = null
                    )
                    Column(
                        modifier = Modifier
                            .height(135.dp)
                            .padding(start = 22.dp)
                            .weight(1f)
                    ) {
                        Text(
                            text = meal.name,
                            style = AppThemeCommon.typography.title
                        )
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp)
                                .height(68.dp),
                            text = meal.description,
                            maxLines = 4,
                            overflow = TextOverflow.Ellipsis,
                            style = AppThemeCommon.typography.description
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp)
                                .height(32.dp),
                            contentAlignment = Alignment.BottomEnd
                        ) {
                            Box(
                                modifier = Modifier
                                    .width(87.dp)
                                    .height(32.dp)
                                    .border(
                                        1.dp,
                                        AppThemeCommon.colors.pink,
                                        AppThemeCommon.shape.small
                                    )
                                    .background(
                                        color = AppThemeCommon.colors.background,
                                        shape = AppThemeCommon.shape.small
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "От 345 р",
                                    style = AppThemeCommon.typography.button
                                )
                            }
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color(0xFFF3F5F9))
                )
            }
        }
        if (state.meals.isEmpty())
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier,
                    text = "Хмм, блюд нет :(\nпопробуй выбрать другую категию :)",
                    style = AppThemeCommon.typography.description.copy(
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp
                    ),
                )
            }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ErrorScreen(
    message: String,
    onReload: () -> Unit
) {
    val pagerState = rememberPagerState(
        initialPage = 0,
    ) { 2 }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFFFFFFF))
                .padding(start = 16.dp, end = 16.dp, top = 40.dp, bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .background(Color(0xFFAAAAAA), RoundedCornerShape(6.dp))
                    .fillMaxWidth(0.3f)
                    .height(24.dp)
            )
            Box(
                modifier = Modifier
                    .background(Color(0xFFAAAAAA), RoundedCornerShape(6.dp))
                    .size(24.dp)
            )
        }
        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(top = 16.dp, end = 48.dp),
            userScrollEnabled = false
        ) {
            Box(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .background(Color(0xFFAAAAAA), RoundedCornerShape(10.dp))
                    .fillMaxWidth()
                    .height(112.dp)
            )
        }
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(start = 8.dp, end = 16.dp, top = 24.dp, bottom = 16.dp),
            userScrollEnabled = false
        ) {
            items(50) {
                Box(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .background(Color(0xFFAAAAAA), RoundedCornerShape(6.dp))
                        .width(88.dp)
                        .height(32.dp)
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color(0xFFF3F5F9))
        )
        LazyColumn(
            userScrollEnabled = false
        ) {
            items(5) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .background(Color(0xFFAAAAAA))
                            .size(135.dp)
                    )
                    Column(
                        modifier = Modifier
                            .height(135.dp)
                            .padding(start = 22.dp)
                            .weight(1f)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.75f)
                                .height(19.dp)
                                .background(Color(0xFFAAAAAA))
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp)
                                .height(68.dp)
                                .background(Color(0xFFAAAAAA))
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp)
                                .height(32.dp),
                            contentAlignment = Alignment.BottomEnd
                        ) {
                            Box(
                                modifier = Modifier
                                    .width(87.dp)
                                    .height(32.dp)
                                    .background(Color(0xFFAAAAAA), RoundedCornerShape(6.dp))
                            )
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color(0xFFF3F5F9))
                )
            }
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ) {
        Row(
            modifier = Modifier
                .padding(bottom = 50.dp)
                .background(AppThemeCommon.colors.disabled)
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .weight(1f),
                text = message,
                style = AppThemeCommon.typography.description.copy(color = AppThemeCommon.colors.white)
            )
            IconButton(onClick = { onReload() }) {
                Icon(
                    modifier = Modifier
                        .size(24.dp),
                    imageVector = Icons.Default.Refresh,
                    tint = AppThemeCommon.colors.white,
                    contentDescription = null
                )
            }
        }
    }
}
