package com.example.blinkitclone.presentation.Components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.blinkitclone.R
import com.example.blinkitclone.presentation.navigation.Screen
import com.example.blinkitclone.presentation.viewmodel.CartViewModel

data class ProductItem(
    val name: String,
    val quantity: String,
    val discountedPrice: String,
    val price: String,
    val mrp: String = "",
    val imageRes: Int,
    val deliveryTime: String = "10 MINS",
    val tags: List<String> = emptyList(),
    val recipeCount: Int = 0
)

@Composable
fun ProductCard(
    product: ProductItem,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    cartViewModel: CartViewModel = hiltViewModel()
) {
    var itemCount by remember { mutableIntStateOf(0) }

    Card(
        modifier = modifier
            .width(155.dp)
            .wrapContentHeight()
            .clickable {
                navController.navigate(Screen.ProductScreen.route)
            },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color(0xFFF0F0F0)),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            // Product Image + ADD / Quantity Stepper Button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
            ) {
                Image(
                    painter = painterResource(id = product.imageRes),
                    contentDescription = product.name,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(96.dp)
                        .align(Alignment.Center)
                )

                if (itemCount == 0) {
                    OutlinedButton(
                        onClick = {
                            itemCount++
                            cartViewModel.incrementCartCount()
                        },
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .height(30.dp),
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(1.dp, Color(0xFF0C831F)),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = Color.White,
                            contentColor = Color(0xFF0C831F)
                        ),
                        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 0.dp)
                    ) {
                        Text(
                            text = "ADD",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                } else {
                    Row(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .height(30.dp)
                            .background(Color(0xFF0C831F), RoundedCornerShape(8.dp))
                            .padding(horizontal = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Remove,
                            contentDescription = "Decrease Quantity",
                            tint = Color.White,
                            modifier = Modifier
                                .size(14.dp)
                                .clickable {
                                    itemCount--
                                    cartViewModel.decrementCartCount()
                                }
                        )
                        Text(
                            text = itemCount.toString(),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Increase Quantity",
                            tint = Color.White,
                            modifier = Modifier
                                .size(14.dp)
                                .clickable {
                                    itemCount++
                                    cartViewModel.incrementCartCount()
                                }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Delivery Time Tag
            Text(
                text = product.deliveryTime,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray
            )

            Spacer(modifier = Modifier.height(2.dp))

            // Product Name
            Text(
                text = product.name,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(2.dp))

            // Quantity
            Text(
                text = product.quantity,
                fontSize = 11.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Price + Strikethrough MRP Row
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = product.discountedPrice.ifEmpty { product.price },
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                if (product.mrp.isNotEmpty()) {
                    Text(
                        text = product.mrp,
                        fontSize = 11.sp,
                        color = Color.Gray,
                        textDecoration = TextDecoration.LineThrough
                    )
                }
            }

            if (product.recipeCount > 0) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${product.recipeCount} recipes",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF0C831F)
                )
            }
        }
    }
}
