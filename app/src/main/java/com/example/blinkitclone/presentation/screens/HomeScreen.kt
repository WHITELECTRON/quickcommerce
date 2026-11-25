package com.example.blinkitclone.presentation.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.blinkitclone.R
import com.example.blinkitclone.data.models.BlinkitCategoryData
import com.example.blinkitclone.presentation.navigation.Screen
import com.example.blinkitclone.presentation.screens.CategoryScreen.AllCategoryScreen
import com.example.blinkitclone.presentation.screens.CategoryScreen.SummerCategory
import com.example.blinkitclone.presentation.Components.BlinkitSearchBar
import com.example.blinkitclone.presentation.utils.BlinkitTabRow
import com.example.blinkitclone.presentation.viewmodel.CartViewModel
import kotlin.math.roundToInt

fun getCategoryGradient(index: Int): Brush {
    return when (index) {
        1 -> Brush.verticalGradient(listOf(Color(0xFF58D3DA), Color(0xFF02A3B1)))
        else -> Brush.verticalGradient(listOf(Color(0xFFF7CB05), Color(0xFFC89000)))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    listState: LazyListState,
    cartViewModel: CartViewModel = hiltViewModel()
) {
    val categories = listOf(
        BlinkitCategoryData("All", R.drawable.category),
        BlinkitCategoryData("Summer", R.drawable.summerbanner),
        BlinkitCategoryData("Electronics", R.drawable.homeappliance),
        BlinkitCategoryData("Beauty", R.drawable.blush),
        BlinkitCategoryData("Kids", R.drawable.bottlebaby)
    )

    var selectedTabIndex by remember { mutableIntStateOf(0) }

    // Scroll tracking for collapsible top header animation
    val firstVisibleItemIndex by remember {
        derivedStateOf { listState.firstVisibleItemIndex }
    }
    val firstVisibleItemScrollOffset by remember {
        derivedStateOf { listState.firstVisibleItemScrollOffset }
    }

    val isScrolled by remember {
        derivedStateOf {
            firstVisibleItemIndex > 0 || firstVisibleItemScrollOffset > 50
        }
    }

    val headerHeightDp by animateDpAsState(
        targetValue = if (isScrolled) 0.dp else 88.dp,
        label = "headerHeight"
    )

    val density = LocalDensity.current
    val topContentOffset by remember {
        derivedStateOf {
            if (isScrolled) with(density) { -88.dp.toPx() } else 0f
        }
    }

    val headerGradient: Brush = getCategoryGradient(selectedTabIndex)

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(headerGradient)
            ) {
                // Collapsible Delivery Info + Location + Profile Section
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(headerHeightDp)
                        .offset { IntOffset(0, topContentOffset.roundToInt()) }
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Blinkit in",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                            Text(
                                text = "10 minutes",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.Black
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "HOME - ",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )
                                Text(
                                    text = "United States",
                                    fontSize = 12.sp,
                                    color = Color.DarkGray
                                )
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowDown,
                                    contentDescription = "Select Location",
                                    tint = Color.Black,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }

                        // Profile Button
                        Box(
                            modifier = Modifier
                                .size(38.dp)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.85f))
                                .clickable {
                                    navController.navigate(Screen.ProfileScreen.route)
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.profile),
                                contentDescription = "Profile",
                                tint = Color.Black,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }
                }

                // Persistent Search Bar
                BlinkitSearchBar(navController = navController)

                // Category Tab Row (All, Summer, Electronics, Beauty, Kids)
                BlinkitTabRow(
                    selectedTabIndex = selectedTabIndex,
                    onTabSelected = { selectedTabIndex = it },
                    categories = categories
                )
            }
        }
    ) { innerPadding ->
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)
        ) {
            item {
                AnimatedContent(
                    targetState = selectedTabIndex,
                    label = "CategoryTabContent"
                ) { tabIndex ->
                    when (tabIndex) {
                        0 -> AllCategoryScreen(navController = navController)
                        1 -> SummerCategory(navController = navController)
                        else -> AllCategoryScreen(navController = navController)
                    }
                }
            }
        }
    }
}
