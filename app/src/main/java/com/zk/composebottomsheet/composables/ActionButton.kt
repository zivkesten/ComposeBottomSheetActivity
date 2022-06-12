package com.zk.composebottomsheet.composables

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp


@Composable
fun ActionButton(
    modifier: Modifier,
    text: String,
    color: Color,
    onClick: () -> Unit) {
    Button(colors = ButtonDefaults.buttonColors(backgroundColor = color),
        onClick = { onClick() },
        modifier = modifier.then(
            Modifier
                .padding(top = 15.dp)
                .width(200.dp)
                .height(58.dp)),
        shape = RoundedCornerShape(10.dp)
    ) {
        Text(text, textAlign = TextAlign.Center)
    }
}