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
import com.library.otpcomposable.uimodel.LCE
import com.library.otpcomposable.uimodel.OtpErrorCustomization
import com.library.otpcomposable.uimodel.OtpLoadingCustomization
import com.library.otpcomposable.uimodel.OtpViewCustomization
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun OtpView(
    pin: String,
    onPinChange: (String) -> Unit,
    onFullPin: (String) -> Unit,
    view: OtpViewCustomization,
    error: OtpErrorCustomization,
    loading: OtpLoadingCustomization,
    scaffoldState: ScaffoldState,
    lce: LCE = LCE.Content,
) {
    var lastState by remember { mutableStateOf(LCE.Content) }
    val scope = rememberCoroutineScope()
    val offset = remember { Animatable(0f) }
    val localView = LocalView.current

    // handle error
    if (lce == LCE.Error && lastState != LCE.Error) {
        handleError(error, scaffoldState, offset, scope, localView)
    }

    // keep last state in sync to avoid multiple recompositions
    if (lce != lastState) lastState = lce

    Column {
        BasicTextField(
            value = pin,
            onValueChange = {
                // update state
                if (it.length <= view.digitCount) {
                    onPinChange(it)
                }
                // full pin entered
                if (it.length == view.digitCount) onFullPin(it)
            },
            enabled = lce != LCE.Loading,
            modifier = view.modifier.offset(offset.value.dp, 0.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            decorationBox = {
                DigitsView(
                    pin = pin,
                    view = view,
                    isError = lce == LCE.Error
                )
            }
        )

        if (lce != LCE.Loading) {
            ErrorView(
                message = error.message,
                modifier = error.modifier.alpha(if (lce == LCE.Error) 1f else 0f)
            )
        } else {
            LoadingView(loading)
        }
    }
}

fun handleError(
    error: OtpErrorCustomization,
    scaffoldState: ScaffoldState,
    offset: Animatable<Float, AnimationVector1D>,
    scope: CoroutineScope,
    localView: View,
) {
    animateText(offset, scope, localView)
    if (error.snackMsg.isNotEmpty()) {
        scope.launch {
            scaffoldState.snackbarHostState.showSnackbar(error.snackMsg)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OtpPreview() {
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
