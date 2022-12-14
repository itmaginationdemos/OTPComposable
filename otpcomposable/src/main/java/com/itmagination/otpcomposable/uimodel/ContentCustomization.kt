package com.itmagination.otpcomposable.uimodel

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.itmagination.otpcomposable.model.DigitViewType

data class ContentCustomization(
    val modifier: Modifier = Modifier,
    val color: Color = Color.Black,
    val digitCount: Int = 6,
    val keyboardType: KeyboardType = KeyboardType.Number,
    val isPassword: Boolean = false,
    val passwordChar: Char = '*',
    val type: DigitViewType = DigitViewType.Rounded(0),
    val digitSize: TextUnit = 24.sp,
    val containerSize: Dp = digitSize.value.dp * 2.2f,
)
