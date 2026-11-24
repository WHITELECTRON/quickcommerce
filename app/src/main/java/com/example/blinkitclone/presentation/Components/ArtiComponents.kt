package com.example.blinkitclone.presentation.Components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import com.example.blinkitclone.R

data class AartiData(
    val title: String,
    val pageCount: String,
    val reviews: String,
    val discountPercentage: String,
    val discountedPrice: String,
    val originalPrice: String,
    val imageRes: Int
)

@Composable
fun AartiUI() {
    val aartiList = listOf(
        AartiData(
            title = "Print Your Aartis",
            pageCount = "3 pages",
            reviews = "(1.2k)",
            discountPercentage = "50% OFF",
            discountedPrice = "₹19",
            originalPrice = "₹38",
            imageRes = R.drawable.aarti_ganesh // Replace with your drawable resource
        ),
        AartiData(
            title = "Print Your Aartis",
            pageCount = "3 pages",
            reviews = "(980)",
            discountPercentage = "50% OFF",
            discountedPrice = "₹19",
            originalPrice = "₹38",
            imageRes = R.drawable.aarti_laxmi // Replace with your drawable resource
        ),
        AartiData(
            title = "Print Your Aartis",
            pageCount = "3 pages",
            reviews = "(1.5k)",
            discountPercentage = "50% OFF",
            discountedPrice = "₹19",
            originalPrice = "₹38",
            imageRes = R.drawable.aarti_hanuman // Replace with your drawable resource
        )
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = "Print Your Aartis",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(aartiList) { aarti ->
                AartiCard(aarti = aarti)
            }
        }
    }
}

@Composable
fun AartiCard(aarti: AartiData) {
    Card(
        modifier = Modifier
            .width(160.dp)
            .wrapContentHeight(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
            ) {
                Image(
                    painter = painterResource(id = aarti.imageRes),
                    contentDescription = aarti.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(8.dp))
                )

                OutlinedButton(
                    onClick = { /* Handle print action */ },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(4.dp)
                        .height(30.dp),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color.White,
                        contentColor = Color(0xFF0C831F)
                    )
                ) {
                    Text(
                        text = "Print",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Page count badge
            Text(
                text = aarti.pageCount,
                fontSize = 10.sp,
                color = Color.DarkGray,
                modifier = Modifier
                    .background(Color(0xFFF2F2F2), RoundedCornerShape(4.dp))
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = aarti.title,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Star Rating Row
            Row(verticalAlignment = Alignment.CenterVertically) {
                repeat(4) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Rating Star",
                        tint = Color(0xFFFFB800),
                        modifier = Modifier.size(12.dp)
                    )
                }
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = aarti.reviews,
                    fontSize = 11.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = aarti.discountPercentage,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF256FEF)
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = aarti.discountedPrice,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "MRP ${aarti.originalPrice}",
                    fontSize = 11.sp,
                    color = Color.Gray,
                    textDecoration = TextDecoration.LineThrough
                )
            }
        }
    }
}