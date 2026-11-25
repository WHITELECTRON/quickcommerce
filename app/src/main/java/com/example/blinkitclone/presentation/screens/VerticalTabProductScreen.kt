package com.example.blinkitclone.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.blinkitclone.R
import com.example.blinkitclone.presentation.viewmodel.CartViewModel

data class SideCategoryTab(
    val title: String,
    val imageRes: Int
)

data class VerticalGridProductItem(
    val name: String,
    val quantity: String,
    val discountPercent: Int,
    val discountedPrice: Int,
    val mrpPrice: Int,
    val imageRes: Int,
    val deliveryTime: String = "10 mins",
    val tag: String? = "Bestseller",
    val count: Int = 1
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerticalTabProductScreen(
    navController: NavHostController,
    cartViewModel: CartViewModel
) {
    val sideCategories = remember {
        listOf(
            SideCategoryTab("All", R.drawable.milk),
            SideCategoryTab("Fresh Vegetables", R.drawable.milk),
            SideCategoryTab("Fresh Fruits", R.drawable.milk),
            SideCategoryTab("Exotics", R.drawable.milk),
            SideCategoryTab("Leafy & Herbs", R.drawable.milk)
        )
    }

    val filterChips = remember {
        listOf("Filter", "Tomato", "Apple", "Kiwi", "Vegetables")
    }

    val productItems = remember {
        listOf(
            VerticalGridProductItem(
                name = "Fresh Tomato",
                quantity = "500 g",
                discountPercent = 20,
                discountedPrice = 24,
                mrpPrice = 30,
                imageRes = R.drawable.milk
            ),
            VerticalGridProductItem(
                name = "Shimla Apple",
                quantity = "4 pcs (500 - 600 g)",
                discountPercent = 15,
                discountedPrice = 110,
                mrpPrice = 130,
                imageRes = R.drawable.milk
            ),
            VerticalGridProductItem(
                name = "Green Kiwi",
                quantity = "3 pcs",
                discountPercent = 25,
                discountedPrice = 75,
                mrpPrice = 100,
                imageRes = R.drawable.milk
            ),
            VerticalGridProductItem(
                name = "Sweet Bell Pepper",
                quantity = "2 pcs",
                discountPercent = 25,
                discountedPrice = 45,
                mrpPrice = 60,
                imageRes = R.drawable.milk
            )
        )
    }

    var selectedCategoryIndex by remember { mutableIntStateOf(0) }
    var selectedFilter by remember { mutableStateOf("Filter") }

    val visibleProducts = remember(selectedFilter) {
        if (selectedFilter == "Filter" || selectedFilter == "Vegetables") {
            productItems
        } else {
            productItems.filter {
                it.name.contains(selectedFilter, ignoreCase = true)
            }.ifEmpty { productItems }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = sideCategories[selectedCategoryIndex].title,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Left vertical category slider
            CategorySlider(
                categories = sideCategories,
                selectedIndex = selectedCategoryIndex,
                onCategorySelect = { index -> selectedCategoryIndex = index },
                modifier = Modifier
                    .width(86.dp)
                    .fillMaxHeight()
                    .background(Color(0xFFF8F9FA))
            )

            VerticalDivider(
                thickness = 1.dp,
                color = Color.LightGray.copy(alpha = 0.5f)
            )

            // Right main pane: horizontal filter row + 2-column product grid
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(Color.White)
            ) {
                FilterChipRow(
                    filters = filterChips,
                    selectedFilter = selectedFilter,
                    onFilterSelect = { chip -> selectedFilter = chip }
                )

                HorizontalDivider(color = Color.LightGray.copy(alpha = 0.4f))

                ProductGrid(
                    products = visibleProducts,
                    onAddClick = { cartViewModel.increaseItemCount() },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 10.dp, vertical = 10.dp)
                )
            }
        }
    }
}

@Composable
fun CategorySlider(
    categories: List<SideCategoryTab>,
    selectedIndex: Int,
    onCategorySelect: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(categories) { index, tab ->
            val isSelected = index == selectedIndex

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onCategorySelect(index) }
                    .background(if (isSelected) Color.White else Color.Transparent)
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CategorySelectionIndicator(isSelected = isSelected)

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFF1F3F5)),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = tab.imageRes),
                            contentDescription = tab.title,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.size(30.dp)
                        )
                    }

                    Text(
                        text = tab.title,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        color = if (isSelected) Color.Black else Color.DarkGray,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        lineHeight = 13.sp
                    )
                }
            }
        }
    }
}

@Composable
fun CategorySelectionIndicator(
    isSelected: Boolean
) {
    Box(
        modifier = Modifier
            .width(4.dp)
            .height(42.dp)
            .clip(RoundedCornerShape(topEnd = 4.dp, bottomEnd = 4.dp))
            .background(if (isSelected) Color(0xFF0C831F) else Color.Transparent)
    )
}

@Composable
fun FilterChipRow(
    filters: List<String>,
    selectedFilter: String,
    onFilterSelect: (String) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        contentPadding = PaddingValues(horizontal = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(filters) { filter ->
            val isSelected = filter == selectedFilter
            FilterChip(
                selected = isSelected,
                onClick = { onFilterSelect(filter) },
                label = {
                    Text(
                        text = filter,
                        fontSize = 12.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                    )
                }
            )
        }
    }
}

@Composable
fun ProductGrid(
    products: List<VerticalGridProductItem>,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val rows = remember(products) { products.chunked(2) }

    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        items(rows.size) { rowIndex ->
            val pair = rows[rowIndex]
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                pair.forEach { product ->
                    VerticalGridProductCard(
                        product = product,
                        onAddClick = onAddClick,
                        modifier = Modifier.weight(1f)
                    )
                }
                if (pair.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
fun VerticalGridProductCard(
    product: VerticalGridProductItem,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Image(
                painter = painterResource(id = product.imageRes),
                contentDescription = product.name,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(78.dp)
            )

            Text(
                text = product.deliveryTime,
                fontSize = 10.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.DarkGray
            )

            Text(
                text = product.name,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = product.quantity,
                fontSize = 11.sp,
                color = Color.Gray
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "₹${product.discountedPrice}",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.Black
                    )
                    Text(
                        text = "₹${product.mrpPrice}",
                        fontSize = 10.sp,
                        color = Color.Gray,
                        textDecoration = TextDecoration.LineThrough
                    )
                }

                OutlinedButton(
                    onClick = onAddClick,
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp),
                    modifier = Modifier.height(30.dp)
                ) {
                    Text(
                        text = "ADD",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0C831F)
                    )
                }
            }
        }
    }
}
