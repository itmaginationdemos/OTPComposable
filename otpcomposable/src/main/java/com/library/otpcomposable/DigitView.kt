package com.library.otpcomposable

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
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.library.otpcomposable.model.DigitViewType

@Composable
fun DigitView(
    index: Int,
    pin: String,
    color: Color,
    size: TextUnit,
    containerSize: Dp,
    isError: Boolean,
    type: DigitViewType = DigitViewType.Underline
) {
    val modifier = Modifier.resolveModifier(
        type = type,
        containerSize = containerSize,
        color = if (isError) MaterialTheme.colors.onError else color
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = if (index >= pin.length) "" else pin[index].toString(),
            modifier = modifier.wrapContentHeight(CenterVertically),
            color = color,
            textAlign = TextAlign.Center,
            fontSize = size,
            style = MaterialTheme.typography.body1
        )

        if (type == DigitViewType.Underline) {
            Spacer(modifier = Modifier.height(2.dp))
            Box(
                modifier = Modifier
                    .background(color)
                    .height(1.dp)
                    .width(containerSize)
            )
        }
    }
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
        color = Color.Black,
        size = 22.sp,
        containerSize = (22 * 2.2f).dp,
        type = DigitViewType.Rounded(50),
        isError = false
    )
}
