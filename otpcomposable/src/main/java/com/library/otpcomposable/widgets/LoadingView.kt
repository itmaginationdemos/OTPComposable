package com.library.otpcomposable.widgets

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.library.otpcomposable.uimodel.LoadingCustomization

@Composable
fun LoadingView(data: LoadingCustomization) {
    Row(verticalAlignment = CenterVertically) {
        if (data.showMessage) {
            Text(
                modifier = data.modifier,
                text = data.loadingMessage,
                color = MaterialTheme.colors.onBackground,
                fontSize = MaterialTheme.typography.body2.fontSize,
                fontWeight = FontWeight.Bold
            )
        }
        if (data.showProgress) {
            CircularProgressIndicator(
                modifier = Modifier.padding(start = 8.dp).size(24.dp),
                strokeWidth = 3.dp
            )
        }
    }
}

@Preview
@Composable
fun LoadingPreview() {
    LoadingView(
        LoadingCustomization(
            modifier = Modifier.padding(8.dp),
            loadingMessage = "Loading time!",
            showMessage = true,
            showProgress = true,
        )
    )
}
