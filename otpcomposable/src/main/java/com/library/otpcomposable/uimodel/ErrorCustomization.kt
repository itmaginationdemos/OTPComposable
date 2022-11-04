package com.library.otpcomposable.uimodel

import androidx.compose.ui.Modifier

data class ErrorCustomization(
    val modifier: Modifier = Modifier,
    val snackMsg: String = "",
    val message: String = "Code is incorrect",
)
