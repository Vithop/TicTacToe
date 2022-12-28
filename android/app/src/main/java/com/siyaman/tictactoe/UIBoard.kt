package com.siyaman.tictactoe

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.siyaman.tictactoe.ui.theme.TicTacToeTheme

@Composable
fun UIBoard(board: Board, enabled: Boolean, onClick: (Int) -> Unit) {
  Column(
    modifier = Modifier
      .aspectRatio(ratio = 1.0f)
      .padding(16.dp)
  ) {
    for (row in Board.LENGTH - 1 downTo 0) {
      Row(modifier = Modifier.weight(1f)) {
        for (column in 0 until Board.LENGTH) {
          val position = row * Board.LENGTH + column
          val piece = board.getPiece(position)
          UITile(modifier = Modifier.weight(1f), piece, enabled) {
            onClick(position)
          }
          if (column < Board.LENGTH - 1) UIVerticalLine()
        }
      }
      if (row > 0) UIHorizontalLine()
    }
  }
}

@DarkLightPreviews
@Composable
private fun DefaultPreview() {
  val board = Board(intArrayOf(0, 2, 5, 7), intArrayOf(1, 3, 4, 8))
  TicTacToeTheme {
    Surface(
      color = MaterialTheme.colorScheme.background
    ) {
      Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Box() {
          UIBoard(board, true, onClick = {})
          UIHorizontalLine()
        }
      }
    }
  }
}