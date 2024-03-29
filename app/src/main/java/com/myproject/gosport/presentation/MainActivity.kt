package com.myproject.gosport.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.myproject.gosport.R
import com.myproject.gosport.presentation.screens.MainScreen
import com.myproject.gosport.presentation.screens.MainViewModel
import com.myproject.gosport.presentation.screens.models.MainState
import com.myproject.gosport.presentation.ui.theme.AppThemeCommon
import com.myproject.gosport.presentation.ui.theme.GoSportTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainViewModel>()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GoSportTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        bottomBar = {
                            when (viewModel.state.collectAsState().value) {
                                is MainState.Success -> {
                                    var selectedIndex by rememberSaveable { mutableStateOf(0) }
                                    NavigationBar(
                                        containerColor = Color(0xFFF0F0F0)
                                    ) {
                                        NavBarItem(
                                            isSelected = selectedIndex == 0,
                                            icon = R.drawable.icon,
                                            label = "Меню",
                                            onClick = { selectedIndex = 0 }
                                        )
                                        NavBarItem(
                                            isSelected = selectedIndex == 1,
                                            icon = R.drawable.union,
                                            label = "Профиль",
                                            onClick = { selectedIndex = 1 }
                                        )
                                        NavBarItem(
                                            isSelected = selectedIndex == 2,
                                            icon = R.drawable.cart,
                                            label = "Корзина",
                                            onClick = { selectedIndex = 2 }
                                        )
                                    }
                                }

                                else -> {}
                            }
                        }
                    ) { paddingValue ->
                        MainScreen(
                            state = viewModel.state.collectAsState().value,
                            onChangeCategory = { viewModel.changeCategory(it) },
                            onReload = { initViewModel() },
                            modifier = Modifier
                                .padding(bottom = paddingValue.calculateBottomPadding())
                        )
                    }
                }
            }
        }
        initViewModel()
    }

    private fun initViewModel() {
        viewModel.loadData(this@MainActivity)
    }
}

@Composable
fun RowScope.NavBarItem(
    isSelected: Boolean,
    onClick: () -> Unit,
    @DrawableRes icon: Int,
    label: String
) {
    NavigationBarItem(
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = AppThemeCommon.colors.pink,
            selectedTextColor = AppThemeCommon.colors.pink,
            indicatorColor = Color(0xFFF0F0F0),
            unselectedIconColor = AppThemeCommon.colors.disabled,
            unselectedTextColor = AppThemeCommon.colors.disabled,
        ),
        selected = isSelected,
        onClick = onClick,
        icon = {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
            )
        },
        label = {
            Text(
                text = label,
                style = AppThemeCommon.typography.bottomButton
            )
        }
    )
}