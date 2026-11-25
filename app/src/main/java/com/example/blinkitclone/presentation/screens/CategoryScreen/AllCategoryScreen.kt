package com.example.blinkitclone.presentation.screens.CategoryScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.blinkitclone.R
import com.example.blinkitclone.presentation.Components.SimpleProductCard
import com.example.blinkitclone.presentation.Components.SimpleProductItem

@Composable
fun AllCategoryScreen(
    navController: NavHostController
) {
    val groceryItems = listOf(
        SimpleProductItem(R.drawable.milk, "Milk &", "Dairy"),
        SimpleProductItem(R.drawable.milk, "Atta, Rice", "& Dal"),
        SimpleProductItem(R.drawable.milk, "Fruits &", "Vegetables")
    )

    val snacksAndDrinksItems = listOf(
        SimpleProductItem(R.drawable.milk, "Chips &", "Namkeen"),
        SimpleProductItem(R.drawable.milk, "Cold Drinks", "& Juices"),
        SimpleProductItem(R.drawable.milk, "Instant", "Noodles")
    )

    val beautyItems = listOf(
        SimpleProductItem(R.drawable.milk, "Bath &", "Body"),
        SimpleProductItem(R.drawable.milk, "Hair", "Care"),
        SimpleProductItem(R.drawable.milk, "Skin", "Care")
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(id = R.drawable.milk),
                contentDescription = "All Category Banner",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
        }

        Text(
            text = "Grocery & Kitchen",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            groceryItems.forEach { product ->
                SimpleProductCard(
                    item = product,
                    navController = navController,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Text(
            text = "Snacks & Drinks",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            snacksAndDrinksItems.forEach { product ->
                SimpleProductCard(
                    item = product,
                    navController = navController,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Text(
            text = "Beauty & Personal Care",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            beautyItems.forEach { product ->
                SimpleProductCard(
                    item = product,
                    navController = navController,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}
