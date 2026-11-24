package com.example.blinkitclone.presentation.Components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.blinkitclone.R

data class NoodleListItem(
    val imageRes: Int,
    val title: String,
    val weight: String = "280 g",
    val tag: String = "Instant Noodles",
    val ratingCount: String = "(88,000)",
    val deliveryTime: String = "16 MINS",
    val price: String = "₹60"
)

@Composable
fun NoodlesDownList() {
    val instantNoodles = listOf(
        NoodleListItem(
            imageRes = R.drawable.maggi,
            title = "Maggi Masala 2-Minutes Instant Noodles",
            price = "₹60"
        ),
        NoodleListItem(
            imageRes = R.drawable.milk,
            title = "Maggi Masala 2-Minutes Instant Noodles",
            price = "₹60"
        ),
        NoodleListItem(
            imageRes = R.drawable.milk,
            title = "Maggi Masala 2-Minutes Instant Noodles",
            price = "₹60"
        )
    )

    val trendingItems = listOf(
        NoodleListItem(
            imageRes = R.drawable.milk,
            title = "Pepsi Black Can",
            weight = "300 ml",
            tag = "Soft Drink",
            price = "₹40"
        ),
        NoodleListItem(
            imageRes = R.drawable.milk,
            title = "Yippee Mood Masala Noodles",
            weight = "260 g",
            tag = "Instant Noodles",
            price = "₹48"
        ),
        NoodleListItem(
            imageRes = R.drawable.milk,
            title = "Coca-Cola Can",
            weight = "300 ml",
            tag = "Soft Drink",
            price = "₹40"
        )
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = "Instant Noodles",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
        )

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(instantNoodles) { item ->
                MaggieNoodleItem(
                    imageRes = item.imageRes,
                    title = item.title,
                    weight = item.weight,
                    tag = item.tag,
                    ratingCount = item.ratingCount,
                    deliveryTime = item.deliveryTime,
                    price = item.price
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Text(
            text = "Trending in your city",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
        )

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(trendingItems) { item ->
                MaggieNoodleItem(
                    imageRes = item.imageRes,
                    title = item.title,
                    weight = item.weight,
                    tag = item.tag,
                    ratingCount = item.ratingCount,
                    deliveryTime = item.deliveryTime,
                    price = item.price
                )
            }
        }
    }
}
