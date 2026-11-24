package com.example.blinkitclone.presentation.screens

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.blinkitclone.Common.AuthState
import com.example.blinkitclone.presentation.navigation.Screen
import com.example.blinkitclone.presentation.viewmodel.PhoneAuthViewModel

@Composable
fun PhoneAuthScreen(
    navController: NavHostController,
    viewModel: PhoneAuthViewModel = hiltViewModel()
) {
    val authState by viewModel.authState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Error -> {
                Toast.makeText(
                    context,
                    (authState as AuthState.Error).message,
                    Toast.LENGTH_SHORT
                ).show()
            }
            is AuthState.Verified -> {
                Toast.makeText(
                    context,
                    "Authentication Successful",
                    Toast.LENGTH_SHORT
                ).show()
                navController.navigate(Screen.HomeScreen.route) {
                    popUpTo(Screen.PhoneAuthScreen.route) { inclusive = true }
                }
            }
            else -> {}
        }
    }

    when (authState) {
        is AuthState.Initial -> {
            PhoneNumberInputScreen()
        }
        is AuthState.CodeSent -> {
            OtpVerificationScreen()
        }
        is AuthState.Verified -> {
            // Handled in LaunchedEffect above to navigate to HomeScreen
        }
        is AuthState.Error -> {
            PhoneNumberInputScreen()
        }
    }
}
