package com.example.blinkitclone.Common

sealed class AuthState {
    object Initial : AuthState()
    object CodeSent : AuthState()
    object Verified : AuthState()
    data class Error(val message: String) : AuthState()
}
