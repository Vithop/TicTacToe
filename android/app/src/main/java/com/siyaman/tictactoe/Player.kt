package com.siyaman.tictactoe

enum class Player {
  X, O;

  fun opposite() = when (this) {
    X -> O
    O -> X
  }
}