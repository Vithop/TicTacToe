package com.siyaman.tictactoe

sealed class Status {
  object XTurn : Status()
  object OTurn : Status()
  data class XWon(val winPositions: Bitboard) : Status() {
    override fun toString() = super.toString()
  }

  data class OWon(val winPositions: Bitboard) : Status() {
    override fun toString() = super.toString()
  }

  object Draw : Status()

  override fun toString() = when (this) {
    XTurn -> "X turn"
    OTurn -> "O turn"
    is XWon -> "X won"
    is OWon -> "O won"
    Draw -> "Draw"
  }

  val isGameOver get() = this != XTurn && this != OTurn
}