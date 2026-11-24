package com.example.blinkitclone.presentation.navigation

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.blinkitclone.presentation.screens.*
import com.example.blinkitclone.presentation.viewmodel.CartViewModel

@Composable
fun NavigationGraph(
    navController: NavHostController,
    listState: LazyListState,
    cartViewModel: CartViewModel = hiltViewModel()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.PhoneAuthScreen.route
    ) {
        composable(Screen.PhoneAuthScreen.route) {
            PhoneAuthScreen(
                navController = navController
            )
        }

        composable(Screen.HomeScreen.route) {
            HomeScreen(
                navController = navController,
                listState = listState,
                cartViewModel = cartViewModel
            )
        }

        composable(Screen.OrderAgainScreen.route) {
            OrderAgainScreen()
        }

        composable(Screen.CategoryScreen.route) {
            CategoryScreen()
        }

        composable(Screen.PrintScreen.route) {
            PrintScreen()
        }

        composable(Screen.SearchBarScreen.route) {
            SearchBarScreen()
        }

        composable(Screen.ProfileScreen.route) {
            ProfileScreen()
        }

        composable(Screen.ProductScreen.route) {
            ProductScreen()
        }

        composable(Screen.VerticalTabProductScreen.route) {
            VerticalTabProductScreen()
        }

        composable(Screen.FinalCheckoutScreen.route) {
            FinalCheckoutScreen()
        }
    }
}
