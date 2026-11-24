package com.example.blinkitclone.presentation.Components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.blinkitclone.R

@Composable
fun FilterRow(
    filters: List<String> = listOf("Filter", "Tomato", "Apple", "Kiwi", "Vegetables")
) {
    var selectedFilter by remember { mutableStateOf<String?>(null) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(filters) { filter ->
                val isSelected = selectedFilter == filter

                FilterChip(
                    selected = isSelected,
                    onClick = {
                        selectedFilter = if (isSelected) null else filter
                    },
                    label = {
                        Text(
                            text = filter,
                            fontSize = 13.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                        )
                    },
                    leadingIcon = if (filter == "Filter") {
                        {
                            Icon(
                                painter = painterResource(id = R.drawable.category),
                                contentDescription = "Filter Icon",
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    } else null,
                    trailingIcon = if (isSelected && filter != "Filter") {
                        {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Clear Filter",
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    } else null,
                    shape = RoundedCornerShape(10.dp),
                    colors = FilterChipDefaults.filterChipColors(
                        containerColor = Color.White,
                        labelColor = Color.Black,
                        selectedContainerColor = Color(0xFFE8F5E9),
                        selectedLabelColor = Color(0xFF0C831F),
                        selectedLeadingIconColor = Color(0xFF0C831F),
                        selectedTrailingIconColor = Color(0xFF0C831F)
                    ),
                    border = FilterChipDefaults.filterChipBorder(
                        enabled = true,
                        selected = isSelected,
                        borderColor = Color(0xFFE5E7EB),
                        selectedBorderColor = Color(0xFF0C831F)
                    )
                )
            }
        }
    }
}
