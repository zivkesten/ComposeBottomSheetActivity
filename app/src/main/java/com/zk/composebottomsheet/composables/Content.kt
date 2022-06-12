package com.zk.composebottomsheet.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun Content(modifier: Modifier, animRes: Int, button: (@Composable () -> Unit)? = null) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(animRes))
    Column {
        button?.let {
            Box(modifier = Modifier.align(CenterHorizontally)) {
                button()
            }
        }
        LottieAnimation(modifier = modifier.then(Modifier.fillMaxSize()), composition = composition)
    }
}

