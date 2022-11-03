package com.library.otpcomposable

import android.content.Context
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.library.otpcomposable.helpers.animateText
import com.library.otpcomposable.model.DigitViewType
import kotlinx.coroutines.launch

const val OTP_VIEW_TAG = "otp_view"

@Composable
fun OtpView(
    pin: String,
    onPinChange: (String) -> Unit,
    expectedPin: String,
    onSuccess: () -> Unit,
    modifier: Modifier,
    digitSize: TextUnit = 24.sp,
    containerSize: Dp = digitSize.value.dp * 2.2f,
    digitCount: Int = 6,
    color: Color = MaterialTheme.colors.onBackground,
    type: DigitViewType = DigitViewType.Rounded(50),
    context: Context? = null,
    errorModifier: Modifier,
    errorSnackMsg: String = "",
    errorMessage: String = "Code is incorrect",
    scaffoldState: ScaffoldState? = null
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
                    if (context != null && errorSnackMsg.isNotEmpty()) {
                        scope.launch {
                            scaffoldState?.snackbarHostState?.showSnackbar(errorSnackMsg)
                        }
                    }
                    true
                } else {
                    false
                }

                // handle success
                if (it == expectedPin) onSuccess()
            },
            modifier = modifier.offset(offset.value.dp, 0.dp).testTag(OTP_VIEW_TAG),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            decorationBox = {
                DigitsView(
                    count = digitCount,
                    pin = pin,
                    color = color,
                    size = digitSize,
                    containerSize = containerSize,
                    type = type,
                    isError = isError
                )
            }
        )

        ErrorView(
            message = errorMessage,
            modifier = errorModifier.alpha(if (isError) 1f else 0f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun OtpPreview() {
    val (pinValue, onPinValueChange) = remember { mutableStateOf("") }
    OtpView(
        pin = pinValue,
        onPinChange = onPinValueChange,
        expectedPin = "123456",
        onSuccess = {},
        modifier = Modifier.padding(8.dp),
        type = DigitViewType.Underline,
        errorModifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
        scaffoldState = null
    )
}
