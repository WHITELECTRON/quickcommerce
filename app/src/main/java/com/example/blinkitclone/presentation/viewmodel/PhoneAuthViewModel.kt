package com.example.blinkitclone.presentation.viewmodel

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.example.blinkitclone.common.AuthState
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class PhoneAuthViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Initial)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    private var verificationId: String? = null
    private var resendToken: PhoneAuthProvider.ForceResendingToken? = null

    fun sendVerificationCode(rawPhoneNumber: String, activity: Activity) {
        val formattedNumber = if (rawPhoneNumber.startsWith("+")) {
            rawPhoneNumber.trim()
        } else {
            "+91${rawPhoneNumber.trim()}"
        }

        _authState.value = AuthState.Loading

        val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                val errorMsg = when {
                    e.message?.contains("SHA-1", ignoreCase = true) == true -> "App not authorized (SHA-1 missing in Firebase Console)"
                    e.message?.contains("invalid", ignoreCase = true) == true -> "Invalid phone number format"
                    else -> e.localizedMessage ?: "Verification failed"
                }
                _authState.value = AuthState.Error(errorMsg)
            }

            override fun onCodeSent(
                storedVerificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                verificationId = storedVerificationId
                resendToken = token
                _authState.value = AuthState.CodeSent
            }
        }

        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(formattedNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(callbacks)
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    fun verifyCode(otpCode: String) {
        val currentVerificationId = verificationId
        if (currentVerificationId.isNullOrEmpty()) {
            _authState.value = AuthState.Error("Verification ID not received yet. Please try sending code again.")
            return
        }

        _authState.value = AuthState.Loading
        val credential = PhoneAuthProvider.getCredential(currentVerificationId, otpCode)
        signInWithPhoneAuthCredential(credential)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        firebaseAuth.signInWithCredential(credential)
            .addOnSuccessListener {
                _authState.value = AuthState.Verified
            }
            .addOnFailureListener { e ->
                _authState.value = AuthState.Error(e.localizedMessage ?: "Invalid OTP code entered")
            }
    }

    fun resetAuthState() {
        _authState.value = AuthState.Initial
    }
}
