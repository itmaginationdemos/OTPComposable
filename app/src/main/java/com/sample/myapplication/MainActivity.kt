package com.sample.myapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.library.otpcomposable.OtpView
import com.sample.myapplication.ui.theme.OtpComposableTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            OtpComposableTheme {
                val (pinValue, onPinValueChange) = remember { mutableStateOf("") }

                Surface(color = MaterialTheme.colors.background) {
                    OtpView(
                        pin = pinValue,
                        expectedPin = "123456",
                        onPinChange = onPinValueChange,
                        modifier = Modifier.padding(8.dp),
                        context = this,
                        errorToastMsg = "Wrong code entered",
                        onSuccess = { Log.d("OTP", "SUCESS") }
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
