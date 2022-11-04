package com.library.otpcomposable.widgets

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ErrorView(message: String, modifier: Modifier) {
    Text(
        modifier = modifier,
        text = message,
        color = MaterialTheme.colors.onError,
        fontSize = MaterialTheme.typography.body2.fontSize,
        fontWeight = FontWeight.Bold
    )
}

@Preview
@Composable
fun ErrorPreview() {
    ErrorView(
        modifier = Modifier,
        message = "Wrong code"
    )
}
