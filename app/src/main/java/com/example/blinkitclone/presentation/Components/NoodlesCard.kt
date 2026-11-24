package com.example.blinkitclone.presentation.Components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.blinkitclone.R

@Composable
fun MaggieNoodleItem(
    imageRes: Int = R.drawable.maggi,
    title: String = "Maggi Masala 2-Minutes Instant Noodles",
    weight: String = "280 g",
    tag: String = "Instant Noodles",
    ratingCount: String = "(88,000)",
    deliveryTime: String = "16 MINS",
    price: String = "₹60"
) {
    Column(
        modifier = Modifier
            .width(145.dp)
            .wrapContentHeight()
    ) {
        // Product Thumbnail Box + Overlapping ADD Button
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(145.dp)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .align(Alignment.TopCenter),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF3F4F6)),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    // "Bestseller" Tag
                    Text(
                        text = "Bestseller",
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF8B5E00),
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .background(
                                color = Color(0xFFFFF3CD),
                                shape = RoundedCornerShape(bottomEnd = 8.dp)
                            )
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    )

                    // Product Image
                    Image(
                        painter = painterResource(id = imageRes),
                        contentDescription = title,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .size(95.dp)
                            .align(Alignment.Center)
                            .clip(RoundedCornerShape(8.dp))
                    )

                    // Veg Icon
                    Icon(
                        painter = painterResource(id = R.drawable.veg_icon),
                        contentDescription = "Vegetarian",
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(6.dp)
                            .size(14.dp)
                    )
                }
            }

            // Overlapping ADD + "2 options" Button
            OutlinedButton(
                onClick = { /* Handle add to cart */ },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 6.dp)
                    .height(34.dp),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, Color(0xFF0C831F)),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.White,
                    contentColor = Color(0xFF0C831F)
                ),
                contentPadding = PaddingValues(horizontal = 14.dp, vertical = 0.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "ADD",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.ExtraBold,
                        lineHeight = 12.sp
                    )
                    Text(
                        text = "2 options",
                        fontSize = 8.sp,
                        color = Color.Gray,
                        lineHeight = 8.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Weight & Tag Badges
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = weight,
                fontSize = 10.sp,
                color = Color.DarkGray,
                modifier = Modifier
                    .background(Color(0xFFF3F4F6), RoundedCornerShape(4.dp))
                    .padding(horizontal = 4.dp, vertical = 2.dp)
            )
            Text(
                text = tag,
                fontSize = 10.sp,
                color = Color.DarkGray,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .background(Color(0xFFF3F4F6), RoundedCornerShape(4.dp))
                    .padding(horizontal = 4.dp, vertical = 2.dp)
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Product Title
        Text(
            text = title,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Star Rating Row
        Row(verticalAlignment = Alignment.CenterVertically) {
            repeat(5) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Star",
                    tint = Color(0xFFFFB800),
                    modifier = Modifier.size(11.dp)
                )
            }
            Spacer(modifier = Modifier.width(3.dp))
            Text(
                text = ratingCount,
                fontSize = 10.sp,
                color = Color.Gray
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Delivery Time
        Text(
            text = deliveryTime,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = Color.DarkGray
        )

        Spacer(modifier = Modifier.height(2.dp))

        // Price
        Text(
            text = price,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}
