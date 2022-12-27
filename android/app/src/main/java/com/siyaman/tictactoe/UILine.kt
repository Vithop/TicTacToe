package com.siyaman.tictactoe

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

private val LINE_THICKNESS = 2.dp

@Composable
private fun UILine(modifier: Modifier = Modifier) {
  Divider(
    modifier,
    color = MaterialTheme.colorScheme.secondary,
    thickness = LINE_THICKNESS
  )
}

@Composable
fun UIHorizontalLine() {
  UILine()
}

@Composable
fun UIVerticalLine() {
  UILine(
    Modifier
      .fillMaxHeight()
      .width(LINE_THICKNESS)
  )
}
