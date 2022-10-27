package com.library.otpcomposable.helpers

import android.os.Build
import android.view.HapticFeedbackConstants
import android.view.View
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.keyframes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

private val shakeKeyframes: AnimationSpec<Float> = keyframes {
    durationMillis = 900
    val easing = FastOutLinearInEasing

    // generate 8 keyframes
    for (i in 1..8) {
        val x = when (i % 3) {
            0 -> 6f
            1 -> -6f
            else -> 0f
        }
        x at durationMillis / 10 * i with easing
    }
}

fun animateText(
    offset: Animatable<Float, AnimationVector1D>,
    coroutineScope: CoroutineScope,
    view: View? = null
) {
    coroutineScope.launch {
        offset.animateTo(
            targetValue = 0f,
            animationSpec = shakeKeyframes
        )
    }
    view?.let {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            view.performHapticFeedback(HapticFeedbackConstants.REJECT)
        } else {
            view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)
        }
    }
}
