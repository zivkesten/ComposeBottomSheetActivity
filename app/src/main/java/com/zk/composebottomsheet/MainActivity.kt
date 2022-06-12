package com.zk.composebottomsheet

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zk.composebottomsheet.composables.ActionButton
import com.zk.composebottomsheet.composables.Content

class MainActivity : ComponentActivity() {

    private var useBottomSheetUiWrap = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            var switchState by remember { mutableStateOf(useBottomSheetUiWrap)}
            Column(Modifier.fillMaxSize()) {
                SwitchTitle(modifier = Modifier.align(CenterHorizontally))
                Switch(
                    checked = switchState,
                    onCheckedChange = {
                        switchState = !switchState
                        toggleBottomSheetUiWrap()
                    },
                    modifier = Modifier
                        .align(CenterHorizontally)

                )
                Divider(thickness = 3.dp)
                ActionButton(
                    Modifier.align(CenterHorizontally),
                    "Bottom sheet activity!",
                    Color.Green
                ) { startFlow() }
                ActionButton(
                    Modifier.align(CenterHorizontally),
                    "Bottom sheet composeView",
                    Color.Yellow
                ) {
                    showAsBottomSheet(useBottomSheetUiWrap) { action ->
                        Box(
                            modifier = Modifier
                                .height(300.dp)
                                .background(Color.White)
                        ) {
                            Content(modifier = Modifier.fillMaxSize(), animRes = R.raw.learn) {
                                Button(onClick = action) {
                                    Text("Tap to close")
                                }
                            }
                        }
                    }
                }
                Content(modifier = Modifier.weight(1f), R.raw.android)
            }
        }
    }

    @Composable
    private fun SwitchTitle(modifier: Modifier) {
        Text(
            modifier = modifier.then(Modifier.padding(top = 25.dp, end = 25.dp, start = 25.dp)),
            text = "Wrap content with bottom sheet UI",
            fontSize = 20.sp,
            textAlign = TextAlign.Center
        )
    }

    private fun toggleBottomSheetUiWrap() {
        useBottomSheetUiWrap = !useBottomSheetUiWrap
    }

    private fun startFlow() {
        startActivity(Intent(this, NewFlowActivity::class.java).apply {
            putExtra(KEY_SHOULD_WRAP, useBottomSheetUiWrap)
        })
    }

    companion object {
        const val KEY_SHOULD_WRAP = "shouldWrap"
    }
}