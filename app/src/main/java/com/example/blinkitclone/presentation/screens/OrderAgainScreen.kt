package com.example.blinkitclone.presentation.screens

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.blinkitclone.presentation.Components.BlinkitSearchBar
import com.example.blinkitclone.presentation.Components.ProductCard
import com.example.blinkitclone.presentation.Components.ProductItem
import com.example.blinkitclone.presentation.navigation.Screen
import com.example.blinkitclone.presentation.viewmodel.CartViewModel
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderAgainScreen(
    navController: NavHostController,
    listState: LazyListState,
    cartViewModel: CartViewModel = hiltViewModel()
) {
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
        label = "orderAgainHeaderHeight"
    )

    val density = LocalDensity.current
    val topContentOffset by remember {
        derivedStateOf {
            if (isScrolled) with(density) { -88.dp.toPx() } else 0f
        }
    }

    val orderAgainProducts = remember {
        listOf(
            ProductItem(
                name = "Amul Taaza Toned Milk",
                quantity = "500 ml",
                discountedPrice = "₹27",
                price = "₹27",
                mrp = "₹28",
                imageRes = R.drawable.milk,
                deliveryTime = "10 MINS"
            ),
            ProductItem(
                name = "Maggi Masala 2-Minute Noodles",
                quantity = "280 g",
                discountedPrice = "₹56",
                price = "₹60",
                mrp = "₹60",
                imageRes = R.drawable.maggi,
                deliveryTime = "10 MINS"
            ),
            ProductItem(
                name = "Fresh Sweet Bell Pepper",
                quantity = "2 pcs",
                discountedPrice = "₹45",
                price = "₹50",
                mrp = "₹65",
                imageRes = R.drawable.milk,
                deliveryTime = "12 MINS"
            ),
            ProductItem(
                name = "Bestseller Combo Pack",
                quantity = "1 pack",
                discountedPrice = "₹149",
                price = "₹199",
                mrp = "₹249",
                imageRes = R.drawable.milk,
                deliveryTime = "10 MINS"
            )
        )
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF8F7FF))
            ) {
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
                                text = "Grocery in",
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

                        Box(
                            modifier = Modifier
                                .size(38.dp)
                                .clip(CircleShape)
                                .background(Color.White)
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

                BlinkitSearchBar(navController = navController)
            }
        }
    ) { innerPadding ->
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(
                    text = "Order Again",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

            items(orderAgainProducts.chunked(2)) { rowItems ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    rowItems.forEach { product ->
                        ProductCard(
                            product = product,
                            navController = navController,
                            cartViewModel = cartViewModel,
                            modifier = Modifier.weight(1f)
                        )
                    }
                    if (rowItems.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}
