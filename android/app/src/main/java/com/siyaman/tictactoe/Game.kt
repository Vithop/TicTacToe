package com.siyaman.tictactoe

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Game(private var m_player: Player = Player.X, private val m_board: Board = Board()) :
  Parcelable {
  val player get() = m_player
  val board get() = m_board
  val isGameOver get() = status.isGameOver
  val status: Status
    get() {
      val xWinPositions = winPositions(Player.X)
      val oWinPositions = winPositions(Player.O)

      if (xWinPositions != null) return Status.XWon(xWinPositions)
      if (oWinPositions != null) return Status.OWon(oWinPositions)
      if (board.emptySpaces == Bitboard()) return Status.Draw
      return if (player == Player.X) Status.XTurn else Status.OTurn
    }

  fun getScore(player: Player) = m_board.getPieces(player).count

  /**
   *
   * The index is 1D starting from the bottom left at 0 and ending
   * at the top right at 8 as follows
   *
   *     6 | 7 | 8
   *    -----------
   *     3 | 4 | 5
   *    -----------
   *     0 | 1 | 2
   *
   */
  fun executeMove(index: Int): Status {
    require(arrayOf(Status.XTurn, Status.OTurn).contains(status)) { "Game is over $status" }
    board.setPiece(index, player)
    m_player = player.opposite()
    return status
  }

  private fun winPositions(player: Player) =
    allWinPositions.find { (it and board.getPieces(player)).count == Board.LENGTH }

  companion object {
    private val allWinPositions = arrayOf(
      POSITIVE_DIAGONAL,
      NEGATIVE_DIAGONAL,
    ) + (0 until Bitboard.LENGTH).map {
      LEFT_COLUMN.shift(Direction.East, it)
    } + (0 until Bitboard.LENGTH).map { BOTTOM_ROW.shift(Direction.North, it) }
  }
}


