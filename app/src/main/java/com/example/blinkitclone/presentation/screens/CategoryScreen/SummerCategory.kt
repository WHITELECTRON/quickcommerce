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
fun SummerCategory(
    navController: NavHostController
) {
    val summerProducts = listOf(
        SimpleProductItem(R.drawable.milk, "Fresh", "Food"),
        SimpleProductItem(R.drawable.milk, "Cold", "Milkshake"),
        SimpleProductItem(R.drawable.milk, "Soft", "Drinks")
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
                contentDescription = "Summer Banner",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
        }

        Text(
            text = "Be ready for the sunny days",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            summerProducts.forEach { product ->
                SimpleProductCard(
                    item = product,
                    navController = navController,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Text(
            text = "Summer coolers",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            summerProducts.forEach { product ->
                SimpleProductCard(
                    item = product,
                    navController = navController,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Text(
            text = "Stay fit & just chill",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            summerProducts.forEach { product ->
                SimpleProductCard(
                    item = product,
                    navController = navController,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}
