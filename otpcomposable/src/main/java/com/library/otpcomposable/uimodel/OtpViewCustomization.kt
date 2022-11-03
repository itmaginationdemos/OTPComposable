package com.library.otpcomposable.uimodel

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.library.otpcomposable.model.DigitViewType

data class OtpViewCustomization(
    val modifier: Modifier = Modifier,
    val color: Color = Color.Black,
    val digitCount: Int = 6,
    val type: DigitViewType = DigitViewType.Rounded(50),
    val digitSize: TextUnit = 24.sp,
    val containerSize: Dp = digitSize.value.dp * 2.2f,
)
