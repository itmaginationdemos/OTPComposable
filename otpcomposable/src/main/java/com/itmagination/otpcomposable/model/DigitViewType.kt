package com.itmagination.otpcomposable.model

import androidx.annotation.IntRange

sealed interface DigitViewType {
    object Underline : DigitViewType
    data class Rounded(@IntRange(from = 0, to = 50) val percentage: Int) : DigitViewType
}
