package com.library.otpcomposable.uimodel

import androidx.compose.ui.Modifier

data class OtpLoadingCustomization(
    val modifier: Modifier = Modifier,
    val loadingMessage: String = "Loading...",
    val showMessage: Boolean = true,
    val showProgress: Boolean = true,
)
