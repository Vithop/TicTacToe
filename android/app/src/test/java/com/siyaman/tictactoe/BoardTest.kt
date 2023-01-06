package com.siyaman.tictactoe

import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class BoardTest {
  @Test
  fun constructorTest() {
    Board(Bitboard(0b111011U), Bitboard(0b100U))
    Board(Bitboard(intArrayOf(0, 5, 6)), Bitboard(intArrayOf(3, 7, 8)))
    Board(0b1010011U, 0B110100000U)
    Board(intArrayOf(0, 5, 6), intArrayOf(3, 7, 8))

    assertEquals(Board().getPieces(Player.X), Bitboard(0U))
    assertEquals(Board().getPieces(Player.O), Bitboard(0U))

    assertThrows(IllegalArgumentException::class.java) { Board(0b1010011U, 0B111110000U) }
    assertThrows(IllegalArgumentException::class.java) { Board(intArrayOf(9, 10), intArrayOf(569)) }
  }

  @Test
  fun equalityTest() {
    assertEquals(Board(), Board())
    assertEquals(
      Board(intArrayOf(0, 4, 8), intArrayOf(5, 6, 7)),
      Board(0b100010001U, 0b11100000U)
    )
  }

  @Test
  fun getPieceTest() {
    assertEquals(Board(intArrayOf(0, 5, 6), intArrayOf(3, 7, 8)).getPiece(0), Player.X)
    assertEquals(Board(intArrayOf(0, 5, 6), intArrayOf(3, 7, 8)).getPiece(4), null)
    assertEquals(Board(intArrayOf(0, 5, 6), intArrayOf(3, 7, 8)).getPiece(8), Player.O)
  }

  @Test
  fun emptySpacesTest() {
    assertEquals(Board(intArrayOf(0, 2, 3, 4, 7), intArrayOf(1, 5, 6, 8)).emptySpaces, Bitboard())
    assertEquals(Board().emptySpaces, Bitboard(0b111111111U))
    assertEquals(
      Board(intArrayOf(0, 4, 7), intArrayOf(1, 5, 8)).emptySpaces,
      Bitboard(intArrayOf(2, 3, 6))
    )
  }
}