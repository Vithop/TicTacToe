package com.siyaman.tictactoe

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 *
 * Tic tac toe board that holds X and O pieces. The index is 1D starting from
 * the bottom left at 0 and ending at the top right at 8 as follows
 *
 *     6 | 7 | 8
 *    -----------
 *     3 | 4 | 5
 *    -----------
 *     0 | 1 | 2
 *
 */
@Parcelize
data class Board(
  private var xPieces: Bitboard = Bitboard(),
  private var oPieces: Bitboard = Bitboard()
) : Parcelable {
  constructor(xPieces: UInt, oPieces: UInt) : this(Bitboard(xPieces), Bitboard(oPieces))
  constructor(xPieces: IntArray, oPieces: IntArray) : this(Bitboard(xPieces), Bitboard(oPieces))

  init {
    validate()
  }

  private fun validate() {
    val overlap = xPieces and oPieces
    require(overlap == Bitboard()) {
      "Cannot have two pieces on the same square, there was overlap on ${
        overlap.positions().contentToString()
      }"
    }
  }

  fun getPiece(position: Int): Player? {
    if (xPieces.test(position)) return Player.X
    else if (oPieces.test(position)) return Player.O
    return null
  }

  fun setPiece(position: Int, player: Player) = setPieces(player, getPieces(player).set(position))

  fun getPieces(player: Player): Bitboard {
    return when (player) {
      Player.X -> xPieces
      Player.O -> oPieces
    }
  }

  fun setPieces(player: Player, bitboard: Bitboard) {
    when (player) {
      Player.X -> xPieces = bitboard
      Player.O -> oPieces = bitboard
    }
    validate()
  }

  val emptySpaces get() = (getPieces(Player.X) or getPieces(Player.O)).inv()

  companion object {
    const val LENGTH = Bitboard.LENGTH
    const val AREA = Bitboard.AREA
  }
};