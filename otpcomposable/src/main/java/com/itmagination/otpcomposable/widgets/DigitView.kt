package com.itmagination.otpcomposable.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.itmagination.otpcomposable.model.DigitViewType
import com.itmagination.otpcomposable.uimodel.ContentCustomization

@Composable
fun DigitView(
    index: Int,
    pin: String,
    isError: Boolean,
    content: ContentCustomization
) {
    val modifier = Modifier.resolveModifier(
        type = content.type,
        containerSize = content.containerSize,
        color = if (isError) MaterialTheme.colors.onError else content.color
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = getText(index, pin, content),
            modifier = modifier.wrapContentHeight(CenterVertically),
            color = content.color,
            textAlign = TextAlign.Center,
            fontSize = content.digitSize,
            style = MaterialTheme.typography.body1
        )

        if (content.type == DigitViewType.Underline) {
            Spacer(modifier = Modifier.height(2.dp))
            Box(
                modifier = Modifier
                    .background(content.color)
                    .height(1.dp)
                    .width(content.containerSize)
            )
        }
    }
}

private fun getText(index: Int, pin: String, content: ContentCustomization): String =
    when {
        index >= pin.length -> ""
        content.isPassword -> content.passwordChar.toString()
        else -> pin[index].toString()
    }

private fun Modifier.resolveModifier(
    type: DigitViewType,
    color: Color,
    containerSize: Dp
): Modifier {
    val roundedBy = when (type) {
        is DigitViewType.Rounded -> type.percentage
        else -> 0
    }

    return if (type is DigitViewType.Rounded) {
        this
            .border(width = 1.dp, color = color, shape = RoundedCornerShape(roundedBy))
            .size(containerSize)
    } else {
        this.width(containerSize)
    }
}

@Preview(showBackground = true)
@Composable
fun DigitPreview() {
    DigitView(
        index = 1,
        pin = "123",
        isError = false,
        content = ContentCustomization()
    )
}
