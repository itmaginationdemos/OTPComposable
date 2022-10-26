package com.library.otpcomposable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

const val PIN_VIEW_TYPE_UNDERLINE = 0
const val PIN_VIEW_TYPE_BORDER = 1

@Composable
fun Otp(
    pin: String,
    onPinChange: (String) -> Unit,
    digitColor: Color = MaterialTheme.colors.onBackground,
    digitSize: TextUnit = 16.sp,
    containerSize: Dp = digitSize.value.dp * 2,
    digitCount: Int = 6,
    type: Int = PIN_VIEW_TYPE_UNDERLINE,
    modifier: Modifier
) {
    BasicTextField(
        value = pin,
        onValueChange = { if (it.length <= digitCount) onPinChange(it) },
        modifier = modifier,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        decorationBox = {
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                repeat(digitCount) { index ->
                    Digit(
                        index = index,
                        pin = pin,
                        color = digitColor,
                        size = digitSize,
                        containerSize = containerSize,
                        type = type
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                }
            }
        }
    )
}

@Composable
private fun Digit(
    index: Int,
    pin: String,
    color: Color,
    size: TextUnit,
    containerSize: Dp,
    type: Int = PIN_VIEW_TYPE_UNDERLINE
) {
    val modifier = if (type == PIN_VIEW_TYPE_BORDER) {
        Modifier
            .width(containerSize)
            .border(
                width = 1.dp,
                color = color,
                shape = MaterialTheme.shapes.medium
            )
            .padding(bottom = 3.dp)
    } else Modifier.width(containerSize)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = if (index >= pin.length) "" else pin[index].toString(),
            color = color,
            modifier = modifier,
            style = MaterialTheme.typography.body1,
            fontSize = size,
            textAlign = TextAlign.Center
        )
        if (type == PIN_VIEW_TYPE_UNDERLINE) {
            Spacer(modifier = Modifier.height(2.dp))
            Box(
                modifier = Modifier
                    .background(color)
                    .height(1.dp)
                    .width(containerSize)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val (pinValue, onPinValueChange) = remember { mutableStateOf("") }
    Otp(
        pin = pinValue,
        onPinChange = onPinValueChange,
        type = PIN_VIEW_TYPE_UNDERLINE,
        modifier = Modifier.padding(8.dp)
    )
}
