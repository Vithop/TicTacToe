package com.siyaman.tictactoe

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UIGame() {
  var game by rememberSaveable { mutableStateOf(Game()) }
  Scaffold(
    floatingActionButton = {
      FloatingActionButton(onClick = { game = Game() }) {
        Icon(Icons.Filled.Add, "New Game")
      }
    }
  ) { contentPadding ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(contentPadding),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      Text("${game.status}")
      UIBoard(board = game.board, enabled = !game.isGameOver) { position ->
        val newGame = game.copy()
        newGame.executeMove(position)
        game = newGame
      }
    }
  }
}

@DarkLightPreviews
@Composable
private fun DefaultPreview() {
  TicTacToeTheme {
    Surface(
      modifier = Modifier.fillMaxSize(),
      color = MaterialTheme.colorScheme.background
    ) {
      UIGame()
    }
  }
}