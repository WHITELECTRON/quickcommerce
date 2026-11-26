package com.example.blinkitclone.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.blinkitclone.common.AuthState
import com.example.blinkitclone.presentation.viewmodel.PhoneAuthViewModel

@Composable
fun OtpVerificationScreen(
    phoneAuthViewModel: PhoneAuthViewModel
) {
    var otpCode by remember { mutableStateOf("") }
    val authState by phoneAuthViewModel.authState.collectAsState()
    val isLoading = authState is AuthState.Loading

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 20.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Verify your phone number",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Enter the 6-digit code sent to your phone",
            fontSize = 14.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = otpCode,
            onValueChange = { input ->
                if (input.length <= 6) {
                    otpCode = input
                }
            },
            label = { Text("6-Digit Code", color = Color.DarkGray) },
            placeholder = { Text("123456", color = Color.Gray) },
            singleLine = true,
            textStyle = TextStyle(
                color = Color.Black,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            shape = RoundedCornerShape(10.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedBorderColor = Color(0xFF0C831F),
                unfocusedBorderColor = Color.LightGray,
                focusedLabelColor = Color(0xFF0C831F),
                unfocusedLabelColor = Color.DarkGray,
                cursorColor = Color(0xFF0C831F),
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                if (otpCode.length == 6) {
                    phoneAuthViewModel.verifyCode(otpCode)
                }
            },
            enabled = otpCode.length == 6 && !isLoading,
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF0C831F),
                contentColor = Color.White,
                disabledContainerColor = Color(0xFF0C831F).copy(alpha = 0.5f),
                disabledContentColor = Color.White.copy(alpha = 0.7f)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    color = Color.White,
                    strokeWidth = 2.dp,
                    modifier = Modifier.size(22.dp)
                )
            } else {
                Text(
                    text = "Verify OTP",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
