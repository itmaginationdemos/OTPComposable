package com.library.otpcomposable.model

sealed interface DigitViewType {
    object Underline : DigitViewType
    data class Rounded(val percentage: Int): DigitViewType
}