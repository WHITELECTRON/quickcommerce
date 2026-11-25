package com.example.blinkitclone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.blinkitclone.presentation.navigation.NavigationGraph
import com.example.blinkitclone.presentation.utils.BlinkitBottomNav
import com.example.blinkitclone.presentation.viewmodel.CartViewModel
import com.example.blinkitclone.ui.theme.BlinkitCloneTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val cartViewModel: CartViewModel = hiltViewModel()

            // Track scroll direction
            var isVisible by remember { mutableStateOf(true) }
            var lastScrollOffset by remember { mutableIntStateOf(0) }
            var lastIndex by remember { mutableIntStateOf(0) }
            val listState = rememberLazyListState()

            // Listen to scroll events
            LaunchedEffect(listState) {
                snapshotFlow { listState.firstVisibleItemIndex to listState.firstVisibleItemScrollOffset }
                    .distinctUntilChanged()
                    .collect { (index, scrollOffset) ->
                        if (index > lastIndex || (index == lastIndex && scrollOffset > lastScrollOffset + 50)) {
                            // Scrolling Down -> Hide BottomNavBar
                            isVisible = false
                        } else if (index < lastIndex || (scrollOffset < lastScrollOffset - 50)) {
                            // Scrolling Up -> Show BottomNavBar
                            isVisible = true
                        }
                        lastIndex = index
                        lastScrollOffset = scrollOffset
                    }
            }

            val navController = rememberNavController()

            BlinkitCloneTheme {
                BlinkitBottomNav(
                    isVisible = isVisible,
                    navController = navController,
                    listState = listState,
                    cartViewModel = cartViewModel
                ) { innerPadding ->
                    NavigationGraph(
                        navController = navController,
                        listState = listState,
                        cartViewModel = cartViewModel
                    )
                }
            }
        }
    }
}
