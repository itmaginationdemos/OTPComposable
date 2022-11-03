package com.sample.myapplication

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
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
            val scaffoldState = rememberScaffoldState()
            OtpComposableTheme {
                Scaffold(
                    scaffoldState = scaffoldState,
                    content = { padding -> Screen(this, scaffoldState, padding) },
                    snackbarHost = { host -> SnackbarHost(hostState = host) { Snackbar(snackbarData = it) } }
                )
            }
        }
    }
}

@Composable
fun Screen(context: Context, scaffoldState: ScaffoldState?, padding: PaddingValues) {
    Box(
        modifier = Modifier.fillMaxSize().padding(padding),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth().padding(bottom = 100.dp)
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
                onSuccess = { Log.d("OTP", "SUCCESS") },
                type = DigitViewType.Rounded(50),
                modifier = Modifier.padding(8.dp),
                context = context,
                scaffoldState = scaffoldState,
                errorSnackMsg = "Wrong code entered",
                errorModifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Screen(LocalContext.current, null, PaddingValues(0.dp))
}
