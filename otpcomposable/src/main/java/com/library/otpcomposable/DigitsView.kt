package com.library.otpcomposable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.library.otpcomposable.model.DigitViewType

@Composable
fun DigitsView(
    count: Int,
    pin: String,
    color: Color,
    size: TextUnit,
    containerSize: Dp,
    type: DigitViewType = DigitViewType.Underline,
    isError: Boolean
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        repeat(count) { index ->
            DigitView(
                index = index,
                pin = pin,
                color = color,
                size = size,
                containerSize = containerSize,
                type = type,
                isError = isError
            )
            Spacer(modifier = Modifier.width(5.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DigitsPreview() {
    DigitsView(
        count = 6,
        pin = "123",
        color = Color.Black,
        size = 22.sp,
        containerSize = (22 * 2.2f).dp,
        type = DigitViewType.Underline,
        isError = false
    )
}
