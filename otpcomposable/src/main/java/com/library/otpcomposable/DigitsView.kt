package com.library.otpcomposable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.library.otpcomposable.uimodel.OtpViewCustomization

@Composable
fun DigitsView(
    pin: String,
    isError: Boolean,
    view: OtpViewCustomization
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        repeat(view.digitCount) { index ->
            DigitView(
                index = index,
                pin = pin,
                isError = isError,
                view = view
            )
            Spacer(modifier = Modifier.width(5.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DigitsPreview() {
    DigitsView(
        pin = "123",
        isError = false,
        view = OtpViewCustomization()
    )
}
