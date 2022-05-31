package com.zk.composebottomsheet

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Alignment.Companion.TopEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.zk.composebottomsheet.composables.CloseButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/*
To extend this class, make sure it's descendants
add android:theme="@style/Theme.Transparent" to it's manifest tag
 */
@OptIn(ExperimentalMaterialApi::class)
abstract class ComposeBottomSheetActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val coroutineScope = rememberCoroutineScope()
            val modalBottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
            val isSheetOpened = remember { mutableStateOf(false) }

            ModalBottomSheetLayout(
                sheetBackgroundColor = Color.Transparent,
                sheetState = modalBottomSheetState,
                sheetContent = {
                    when {
                        wrapWithBottomSheetUI() -> {
                            BottomSheetUI(coroutineScope, modalBottomSheetState) {
                                ScreenContent(coroutineScope, modalBottomSheetState) {
                                    onFinish(coroutineScope, modalBottomSheetState)
                                }
                            }
                        }
                        else -> ScreenContent(coroutineScope, modalBottomSheetState) {
                            onFinish(coroutineScope, modalBottomSheetState)
                        }
                    }
                }
            ) {}

            BackHandler {
                onFinish(coroutineScope, modalBottomSheetState)
            }

            // Take action based on hidden state
            LaunchedEffect(modalBottomSheetState.currentValue) {
                when (modalBottomSheetState.currentValue) {
                    ModalBottomSheetValue.Hidden -> {
                        handleBottomSheetAtHiddenState(
                            isSheetOpened,
                            modalBottomSheetState
                        )
                    }
                    else -> {
                        Log.i(TAG, "Bottom sheet ${modalBottomSheetState.currentValue} state")
                    }
                }
            }
        }
    }

    @Composable
    private fun BottomSheetUI(
        coroutineScope: CoroutineScope,
        modalBottomSheetState: ModalBottomSheetState,
        content: @Composable () -> Unit
    ) {
        Box(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .clip(RoundedCornerShape(topEnd = 20.dp, topStart = 20.dp))
                .background(Color.White)
        ) {
            content()

            Divider(
                color = Color.Gray,
                thickness = 5.dp,
                modifier = Modifier
                    .padding(top = 15.dp)
                    .align(TopCenter)
                    .width(80.dp)
                    .clip(RoundedCornerShape(50.dp))
            )

            CloseButton(
                modifier = Modifier
                    .align(TopEnd)
                    .padding(10.dp)
            ) {
                coroutineScope.launch {
                    modalBottomSheetState.hide()
                }
            }
        }
    }

    // Helper methods
    private suspend fun handleBottomSheetAtHiddenState(
        isSheetOpened: MutableState<Boolean>,
        modalBottomSheetState: ModalBottomSheetState
    ) {
        when {
            !isSheetOpened.value -> initializeModalLayout(isSheetOpened, modalBottomSheetState)
            else -> exit()
        }
    }

    private suspend fun initializeModalLayout(
        isSheetOpened: MutableState<Boolean>,
        modalBottomSheetState: ModalBottomSheetState
    ) {
        isSheetOpened.value = true
        modalBottomSheetState.show()
    }

    open fun exit() = finish()

    private fun onFinish(
        coroutineScope: CoroutineScope,
        modalBottomSheetState: ModalBottomSheetState,
        withResults: Boolean = false,
        result: Intent? = null
    ) {
        coroutineScope.launch {
            if (withResults) setResult(RESULT_OK)
            result?.let { intent = it}
            modalBottomSheetState.hide() // will trigger the LaunchedEffect
        }
    }

    abstract fun wrapWithBottomSheetUI(): Boolean

    @Composable
    abstract fun ScreenContent(
        coroutineScope: CoroutineScope,
        modalBottomSheetState: ModalBottomSheetState,
        onExit: (() -> Unit?)
    )

    override fun onPause() {
        super.onPause()
        overridePendingTransition(0, 0)
    }


    companion object {
        private val TAG = ComposeBottomSheetActivity::class.java.simpleName
    }
}