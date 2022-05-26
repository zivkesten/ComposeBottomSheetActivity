package com.zk.composebottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.zk.composebottomsheet.composables.Content
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterialApi::class)
class NewFlowActivity : ComposeBottomSheetActivity() {

    @Composable
    override fun ScreenContent(
        coroutineScope: CoroutineScope,
        modalBottomSheetState: ModalBottomSheetState
    ) {
        Box(modifier = Modifier.height(300.dp)) {
            Content(modifier = Modifier.fillMaxSize(), animRes = R.raw.learn)
        }
    }

    override fun wrapWithBottomSheetUI() = true
}