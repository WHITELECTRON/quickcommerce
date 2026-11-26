package com.example.blinkitclone.presentation.screens

import android.widget.Toast
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.blinkitclone.common.AuthState
import com.example.blinkitclone.presentation.navigation.Screen
import com.example.blinkitclone.presentation.viewmodel.PhoneAuthViewModel

@Composable
fun PhoneAuthScreen(
    navController: NavHostController,
    viewModel: PhoneAuthViewModel = hiltViewModel()
) {
    val authState by viewModel.authState.collectAsState()
    val context = LocalContext.current
    var isCodeSentStep by remember { mutableStateOf(false) }

    LaunchedEffect(authState) {
        when (val state = authState) {
            is AuthState.CodeSent -> {
                isCodeSentStep = true
            }
            is AuthState.Error -> {
                Toast.makeText(
                    context,
                    state.message,
                    Toast.LENGTH_LONG
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

    if (isCodeSentStep) {
        OtpVerificationScreen(
            phoneAuthViewModel = viewModel
        )
    } else {
        PhoneNumberInputScreen(
            navController = navController,
            phoneAuthViewModel = viewModel
        )
    }
}
