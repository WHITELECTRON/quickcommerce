package com.example.blinkitclone.presentation.utils

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.blinkitclone.data.models.BlinkitCategoryData

@Composable
fun BlinkitTabRow(
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,
    categories: List<BlinkitCategoryData>
) {
    ScrollableTabRow(
        selectedTabIndex = selectedTabIndex,
        containerColor = Color.Transparent,
        contentColor = Color.Black,
        edgePadding = 16.dp
    ) {
        categories.forEachIndexed { index, category ->
            Tab(
                selected = selectedTabIndex == index,
                onClick = { onTabSelected(index) },
                text = { Text(text = category.name, modifier = Modifier.padding(vertical = 8.dp)) }
            )
        }
    }
}
