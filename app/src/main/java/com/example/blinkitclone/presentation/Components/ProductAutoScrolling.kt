package com.example.blinkitclone.presentation.Components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.blinkitclone.R
import com.example.blinkitclone.presentation.navigation.Screen
import kotlinx.coroutines.delay
import kotlin.math.ceil
import kotlin.math.roundToInt

data class ProductItemAutoScroll(
    val imageRes: Int,
    val name: String
)

@Composable
fun AutoScrollingProductCarousel(
    navController: NavHostController
) {
    val products = listOf(
        ProductItemAutoScroll(R.drawable.milk, "Milk"),
        ProductItemAutoScroll(R.drawable.instantfood, "Tea"),
        ProductItemAutoScroll(R.drawable.kitkat, "KitKat"),
        ProductItemAutoScroll(R.drawable.choclate, "Chocolate"),
        ProductItemAutoScroll(R.drawable.dryfruits, "Dry Fruits"),
        ProductItemAutoScroll(R.drawable.potato, "Potato"),
        ProductItemAutoScroll(R.drawable.brocli, "Broccoli")
    )

    val scrollSpeed = 1.5f
    val spacing = 8.dp

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(340.dp)
    ) {
        AutoScrollingHorizontalColumn(
            products = products,
            speed = scrollSpeed,
            spacing = spacing
        )

        // Skip button overlaid at the top right
        Button(
            onClick = {
                navController.navigate(Screen.HomeScreen.route) {
                    popUpTo(Screen.PhoneAuthScreen.route) { inclusive = true }
                }
            },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp),
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White.copy(alpha = 0.9f),
                contentColor = Color.Black
            ),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp)
        ) {
            Text(
                text = "Skip",
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun AutoScrollingHorizontalColumn(
    products: List<ProductItemAutoScroll>,
    speed: Float,
    spacing: Dp
) {
    var scrollOffset by remember { mutableFloatStateOf(0f) }

    val density = LocalDensity.current
    val itemWidth = 100.dp
    val itemWidthPx = with(density) { itemWidth.toPx() }
    val spacingPx = with(density) { spacing.toPx() }
    val totalItemWidthPx = itemWidthPx + spacingPx

    // Repeat products 5 times for seamless looping
    val repeatedProducts = remember(products) {
        List(5) { products }.flatten()
    }

    val itemsPerColumn = 3
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val screenWidthPx = with(density) { screenWidth.toPx() }
    val totalColumns = ceil(repeatedProducts.size.toFloat() / itemsPerColumn).toInt()
    val totalContentWidth = totalColumns * totalItemWidthPx

    // Continuous 60fps horizontal scroll loop
    LaunchedEffect(Unit) {
        while (true) {
            delay(16L)
            scrollOffset += speed
            if (scrollOffset >= totalContentWidth / 2f) {
                scrollOffset = 0f
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 8.dp)
    ) {
        val columnCount = ceil(repeatedProducts.size.toFloat() / itemsPerColumn).toInt()

        for (col in 0 until columnCount) {
            val xPos = (col * totalItemWidthPx) - scrollOffset

            // Only render columns that are visible or about to become visible
            if (xPos + totalItemWidthPx >= 0f && xPos <= screenWidthPx) {
                Column(
                    modifier = Modifier
                        .offset { IntOffset(xPos.roundToInt(), 0) }
                        .width(itemWidth),
                    verticalArrangement = Arrangement.spacedBy(spacing)
                ) {
                    for (row in 0 until itemsPerColumn) {
                        val index = (col * itemsPerColumn + row) % repeatedProducts.size
                        ProductCardForAutoScroll(product = repeatedProducts[index])
                    }
                }
            }
        }
    }
}

@Composable
fun ProductCardForAutoScroll(
    product: ProductItemAutoScroll
) {
    Card(
        modifier = Modifier
            .size(100.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF3F6FA)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = product.imageRes),
                contentDescription = product.name,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = product.name,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = Color.DarkGray,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
