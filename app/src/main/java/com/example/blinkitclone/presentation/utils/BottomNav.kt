package com.example.blinkitclone.presentation.utils

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.blinkitclone.presentation.Components.FloatingCartButton
import com.example.blinkitclone.presentation.navigation.Screen
import com.example.blinkitclone.presentation.viewmodel.CartViewModel

data class BottomNavItem(
    val title: String,
    val icon: ImageVector,
    val screen: Screen
)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BlinkitBottomNav(
    isVisible: Boolean,
    navController: NavHostController,
    listState: LazyListState,
    cartViewModel: CartViewModel,
    content: @Composable (PaddingValues) -> Unit
) {
    val itemCount by cartViewModel.cartItemCount
    val showCartButton by cartViewModel.showCartButton

    var selectedItemIndex by remember { mutableIntStateOf(0) }

    val bottomNavItems = remember {
        listOf(
            BottomNavItem("Home", Icons.Default.Home, Screen.HomeScreen),
            BottomNavItem("Order Again", Icons.Default.ShoppingBag, Screen.OrderAgainScreen),
            BottomNavItem("Categories", Icons.Default.GridView, Screen.CategoryScreen),
            BottomNavItem("Print", Icons.Default.Print, Screen.PrintScreen)
        )
    }

    val selectedColor = Color.Black

    val shouldHideOnScroll by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex > 1 || listState.firstVisibleItemScrollOffset > 120
        }
    }

    val bottomBarHeightDp by animateDpAsState(
        targetValue = if (!isVisible || shouldHideOnScroll) 0.dp else 68.dp,
        label = "bottomBarHeight"
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val mainScreens = remember {
        listOf(
            Screen.HomeScreen.route,
            Screen.OrderAgainScreen.route,
            Screen.CategoryScreen.route,
            Screen.PrintScreen.route
        )
    }

    val shouldShowBottomBar = isVisible && (currentRoute == null || currentRoute in mainScreens)

    LaunchedEffect(shouldHideOnScroll) {
        if (shouldHideOnScroll && showCartButton) {
            cartViewModel.dismissCartButton()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AnimatedVisibility(
                    visible = showCartButton && itemCount > 0 && shouldShowBottomBar,
                    enter = fadeIn() + slideInVertically { it / 2 },
                    exit = fadeOut() + slideOutVertically { it / 2 }
                ) {
                    FloatingCartButton(
                        itemCount = itemCount,
                        onClick = {
                            navController.navigate(Screen.FinalCheckoutScreen.route)
                        }
                    )
                }

                AnimatedVisibility(
                    visible = shouldShowBottomBar && bottomBarHeightDp > 0.dp,
                    enter = fadeIn() + slideInVertically { it },
                    exit = fadeOut() + slideOutVertically { it }
                ) {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(bottomBarHeightDp),
                        color = Color.White,
                        tonalElevation = 4.dp,
                        shadowElevation = 6.dp
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 8.dp),
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            bottomNavItems.forEachIndexed { index, item ->
                                val isSelected = currentRoute == item.screen.route ||
                                        (currentRoute == null && selectedItemIndex == index)

                                Column(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clickable {
                                            selectedItemIndex = index
                                            navController.navigate(item.screen.route) {
                                                popUpTo(navController.graph.startDestinationId) {
                                                    saveState = true
                                                }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        }
                                        .padding(vertical = 6.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Icon(
                                        imageVector = item.icon,
                                        contentDescription = item.title,
                                        tint = if (isSelected) selectedColor else Color.Gray,
                                        modifier = Modifier.size(22.dp)
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = item.title,
                                        fontSize = 11.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                        color = if (isSelected) selectedColor else Color.Gray
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        content(innerPadding)
    }
}
