package com.sample.myapplication

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.library.otpcomposable.OtpView
import com.library.otpcomposable.model.DigitViewType
import com.sample.myapplication.ui.theme.OtpComposableTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            OtpComposableTheme {
                Surface {
                    Screen(this)
                }
            }
        }
    }
}

@Composable
fun Screen(context: Context) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth().padding(bottom = 200.dp)
        ) {
            val (pinValue, onPinValueChange) = remember { mutableStateOf("") }

            Text(
                modifier = Modifier.padding(24.dp, 0.dp),
                text = "Please enter PIN code you received in the SMS",
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                lineHeight = 30.sp,
                letterSpacing = 2.sp
            )
            Spacer(Modifier.height(24.dp))
            OtpView(
                pin = pinValue,
                onPinChange = onPinValueChange,
                expectedPin = "123456",
                onSuccess = { Log.d("OTP", "SUCESS") },
                type = DigitViewType.Rounded(50),
                modifier = Modifier.padding(8.dp),
                context = context,
                errorToastMsg = "Wrong code entered",
                errorModifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Screen(LocalContext.current)
}
