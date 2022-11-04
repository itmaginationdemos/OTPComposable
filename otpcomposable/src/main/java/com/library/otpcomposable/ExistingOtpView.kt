package com.library.otpcomposable

import android.view.View
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.library.otpcomposable.helpers.animateText
import com.library.otpcomposable.model.DigitViewType
import com.library.otpcomposable.uimodel.OtpErrorCustomization
import com.library.otpcomposable.uimodel.OtpViewCustomization
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Use this in case when the one time pin/password is known in advance.
 * This View will then be able to handle error states automatically.
 */
@Composable
fun ExistingOtpView(
    pin: String,
    onPinChange: (String) -> Unit,
    expectedPin: String,
    onSuccess: () -> Unit,
    view: OtpViewCustomization,
    error: OtpErrorCustomization,
    scaffoldState: ScaffoldState
) {
    val scope = rememberCoroutineScope()
    val offset = remember { Animatable(0f) }
    var isError by remember { mutableStateOf(false) }

    Column {
        val localView = LocalView.current
        BasicTextField(
            value = pin,
            onValueChange = {
                // update state
                if (it.length <= view.digitCount) onPinChange(it)
                // handle error
                isError = handleError(it, expectedPin, offset, scope, localView, view, error, scaffoldState)
                // handle success
                if (it == expectedPin) onSuccess()
            },
            modifier = view.modifier.offset(offset.value.dp, 0.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            decorationBox = {
                DigitsView(
                    pin = pin,
                    view = view,
                    isError = isError
                )
            }
        )

        ErrorView(
            message = error.message,
            modifier = error.modifier.alpha(if (isError) 1f else 0f)
        )
    }
}

fun handleError(
    newValue: String,
    expectedPin: String,
    offset: Animatable<Float, AnimationVector1D>,
    scope: CoroutineScope,
    localView: View,
    view: OtpViewCustomization,
    error: OtpErrorCustomization,
    scaffoldState: ScaffoldState
): Boolean {
    if (newValue.length < view.digitCount || newValue == expectedPin) {
        return false
    }

    animateText(offset, scope, localView)
    if (error.snackMsg.isNotEmpty()) {
        scope.launch {
            scaffoldState.snackbarHostState.showSnackbar(error.snackMsg)
        }
    }
    return true
}

@Preview(showBackground = true)
@Composable
fun ExistingOtpPreview() {
    val (pinValue, onPinValueChange) = remember { mutableStateOf("") }
    ExistingOtpView(
        pin = pinValue,
        onPinChange = onPinValueChange,
        expectedPin = "123456",
        onSuccess = {},
        view = OtpViewCustomization(
            modifier = Modifier.padding(8.dp),
            type = DigitViewType.Rounded(50),
            color = MaterialTheme.colors.onBackground
        ),
        error = OtpErrorCustomization(Modifier.padding(horizontal = 8.dp, vertical = 4.dp)),
        scaffoldState = rememberScaffoldState()
    )
}
