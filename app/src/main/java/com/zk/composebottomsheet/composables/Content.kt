package com.zk.composebottomsheet.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun Content(modifier: Modifier, animRes: Int) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(animRes))
    LottieAnimation(modifier = modifier.then(Modifier.fillMaxSize()), composition = composition)
}
