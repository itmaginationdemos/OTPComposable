package com.library.otpcomposable.uimodel

import androidx.compose.ui.Modifier

data class OtpErrorCustomization(
    val modifier: Modifier = Modifier,
    val snackMsg: String = "",
    val message: String = "Code is incorrect",
)
