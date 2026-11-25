package com.example.blinkitclone.presentation.viewmodel
//package com.example.projectnew.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor() : ViewModel() {

    private val _cartItemCount = mutableIntStateOf(0)
    val cartItemCount: State<Int> = _cartItemCount

    private val _showCartButton = mutableStateOf(false)
    val showCartButton: State<Boolean> = _showCartButton

    fun incrementCartCount() {
        _cartItemCount.intValue++
        if (_cartItemCount.intValue >= 1) {
            _showCartButton.value = true
        }
    }

    fun increaseItemCount() = incrementCartCount()

    fun decrementCartCount() {
        if (_cartItemCount.intValue > 0) {
            _cartItemCount.intValue--
        }
        if (_cartItemCount.intValue == 0) {
            clearCart()
        }
    }

    fun decreaseItemCount() = decrementCartCount()

    fun clearCart() {
        _cartItemCount.intValue = 0
        _showCartButton.value = false
    }

    fun dismissCartButton() {
        _showCartButton.value = false
    }
}