package com.siyaman.tictactoe

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.siyaman.tictactoe.ui.theme.TicTacToeTheme

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