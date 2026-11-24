package com.example.blinkitclone.presentation.navigation


sealed class Screen(val route: String) {
    object PhoneAuthScreen : Screen("phone_auth")
    object HomeScreen : Screen("home")
    object OrderAgainScreen : Screen("order_again")
    object CategoryScreen : Screen("category")
    object PrintScreen : Screen("print")
    object SearchBarScreen : Screen("search")
    object ProfileScreen : Screen("profile")
    object ProductScreen : Screen("product")
    object VerticalTabProductScreen : Screen("vertical_tab_product")
    object FinalCheckoutScreen : Screen("final_checkout")
}