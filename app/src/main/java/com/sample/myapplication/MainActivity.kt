package com.sample.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.library.otpcomposable.PIN_VIEW_TYPE_BORDER
import com.library.otpcomposable.PinView
import com.sample.myapplication.ui.theme.OtpComposableTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            OtpComposableTheme {
                val (pinValue, onPinValueChange) = remember { mutableStateOf("") }

                Surface(color = MaterialTheme.colors.background) {
                    PinView(
                        pinText = pinValue,
                        onPinTextChange = onPinValueChange,
                        type = PIN_VIEW_TYPE_BORDER
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    OtpComposableTheme {
        Greeting("Android")
    }
}
