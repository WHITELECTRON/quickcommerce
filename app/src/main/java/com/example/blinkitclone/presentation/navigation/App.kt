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
            OrderAgainScreen(
                navController = navController,
                listState = listState,
                cartViewModel = cartViewModel
            )
        }

        composable(Screen.CategoryScreen.route) {
            CategoryScreen(
                navController = navController,
                listState = listState
            )
        }

        composable(Screen.PrintScreen.route) {
            PrintScreen(
                navController = navController,
                listState = listState
            )
        }

        composable(Screen.SearchBarScreen.route) {
            SearchBarScreen(
                navController = navController,
                cartViewModel = cartViewModel
            )
        }

        composable(Screen.ProfileScreen.route) {
            ProfileScreen(
                navController = navController
            )
        }

        composable(Screen.ProductScreen.route) {
            ProductScreen(
                navController = navController,
                cartViewModel = cartViewModel
            )
        }

        composable(Screen.VerticalTabProductScreen.route) {
            VerticalTabProductScreen(
                navController = navController,
                cartViewModel = cartViewModel
            )
        }

        composable(Screen.FinalCheckoutScreen.route) {
            FinalCheckoutScreen(
                navController = navController,
                cartViewModel = cartViewModel
            )
        }
    }
}
