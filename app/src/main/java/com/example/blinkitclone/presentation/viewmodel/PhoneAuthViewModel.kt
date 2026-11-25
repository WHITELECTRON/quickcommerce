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

    fun sendVerificationCode(phoneNumber: String, activity: Activity) {
        _authState.value = AuthState.CodeSent

        val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                _authState.value = AuthState.Error(e.localizedMessage ?: "Verification failed")
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
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(callbacks)
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    fun verifyCode(otpCode: String) {
        val currentVerificationId = verificationId
        if (currentVerificationId.isNullOrEmpty()) {
            _authState.value = AuthState.Error("Verification ID is empty")
            return
        }

        val credential = PhoneAuthProvider.getCredential(currentVerificationId, otpCode)
        signInWithPhoneAuthCredential(credential)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        firebaseAuth.signInWithCredential(credential)
            .addOnSuccessListener {
                _authState.value = AuthState.Verified
            }
            .addOnFailureListener { e ->
                _authState.value = AuthState.Error(e.localizedMessage ?: "Authentication failed")
            }
    }
}
