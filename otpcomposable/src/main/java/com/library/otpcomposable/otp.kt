package com.library.otpcomposable

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.library.otpcomposable.helpers.animateText

const val PIN_VIEW_TYPE_UNDERLINE = 0
const val PIN_VIEW_TYPE_BORDER = 1

@Composable
fun OtpView(
    pin: String,
    onPinChange: (String) -> Unit,
    expectedPin: String,
    onSuccess: () -> Unit,
    digitColor: Color = MaterialTheme.colors.onBackground,
    digitSize: TextUnit = 16.sp,
    containerSize: Dp = digitSize.value.dp * 2,
    digitCount: Int = 6,
    type: Int = PIN_VIEW_TYPE_UNDERLINE,
    modifier: Modifier,
    context: Context? = null,
    errorToastMsg: String = "",
    errorView: @Composable () -> Unit = { ErrorView(modifier) }
) {
    val scope = rememberCoroutineScope()
    val offset = remember { Animatable(0f) }
    var isError by remember { mutableStateOf(false) }

    Column {
        val view = LocalView.current
        BasicTextField(
            value = pin,
            onValueChange = {
                // update state
                if (it.length <= digitCount) onPinChange(it)

                // handle error
                isError = if (it.length >= digitCount && it != expectedPin) {
                    animateText(offset, scope, view)
                    if (context != null && errorToastMsg.isNotEmpty()) {
                        Toast.makeText(context, errorToastMsg, Toast.LENGTH_SHORT).show()
                    }
                    true
                } else {
                    false
                }

                // handle success
                if (it == expectedPin) onSuccess()
            },
            modifier = modifier.offset(offset.value.dp, 0.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            decorationBox = {
                DigitsView(
                    count = digitCount,
                    pin = pin,
                    color = digitColor,
                    size = digitSize,
                    containerSize = containerSize,
                    type = type
                )
            }
        )

        if (isError) errorView()
    }
}

@Composable
private fun DigitsView(
    count: Int,
    pin: String,
    color: Color,
    size: TextUnit,
    containerSize: Dp,
    type: Int = PIN_VIEW_TYPE_UNDERLINE
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
                type = type
            )
            Spacer(modifier = Modifier.width(5.dp))
        }
    }
}

@Composable
private fun DigitView(
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

// shake
// autoclear
// toast
@Composable
private fun ErrorView(modifier: Modifier) {
    Text(
        modifier = modifier,
        text = "Code is incorrect",
        color = Color.Red,
        fontSize = MaterialTheme.typography.caption.fontSize
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val (pinValue, onPinValueChange) = remember { mutableStateOf("") }
    OtpView(
        pin = pinValue,
        onPinChange = onPinValueChange,
        type = PIN_VIEW_TYPE_UNDERLINE,
        modifier = Modifier.padding(8.dp),
        expectedPin = "123456",
        onSuccess = {}
    )
}
