package com.siyaman.tictactoe

import org.junit.Assert.*
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class BitboardTest {
  @Test
  fun constructorTest() {
    Bitboard(0b0011011U)
    Bitboard(0b11101U)
    Bitboard(6U)
    Bitboard(123U)
    Bitboard(0b111111111U)

    assertThrows(IllegalArgumentException::class.java) { Bitboard(0b10010001100U) }
    assertThrows(IllegalArgumentException::class.java) { Bitboard(0b10000000000U) }
    assertThrows(IllegalArgumentException::class.java) { Bitboard(0b10000000000U) }
  }

  @Test
  fun arrayConstructorTest() {
    assertEquals(Bitboard(intArrayOf(0, 1, 6, 8)), Bitboard(0b101000011U))
    assertEquals(Bitboard(intArrayOf()), Bitboard())
    assertEquals(Bitboard(intArrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8)), Bitboard(0b111111111U))
    assertEquals(Bitboard(intArrayOf(5)), Bitboard(0b100000U))

    assertThrows(IllegalArgumentException::class.java) { Bitboard(intArrayOf(5, 7, 9)) }
    assertThrows(IllegalArgumentException::class.java) { Bitboard(intArrayOf(-3, 0)) }
  }

  @Test
  fun getTest() {
    assertEquals(Bitboard(0b100000000U).test(0), false)
    assertEquals(Bitboard(0b100000000U).test(8), true)

    assertEquals(Bitboard(0b100001100U).test(0), false)
    assertEquals(Bitboard(0b100001100U).test(1), false)
    assertEquals(Bitboard(0b100001100U).test(2), true)
    assertEquals(Bitboard(0b100001100U).test(3), true)

    assertEquals(Bitboard(0b010001100U).test(7), true)
    assertEquals(Bitboard(0b010001100U).test(8), false)
  }

  @Test
  fun getOutOfBoundsTest() {
    assertThrows(IllegalArgumentException::class.java) { Bitboard(0b010001100U).test(9597) }
    assertThrows(IllegalArgumentException::class.java) { Bitboard(0b010001100U).test(9) }
    assertThrows(IllegalArgumentException::class.java) { Bitboard(0b010001100U).test(-1) }
    assertThrows(IllegalArgumentException::class.java) { Bitboard(0b010001100U).test(-156) }
  }


  @Test
  fun setTest() {
    assertEquals(Bitboard(0b010001100U).set(7, true), Bitboard(0b010001100U))
    assertEquals(Bitboard(0b010001100U).set(1), Bitboard(0b010001110U))
    assertEquals(Bitboard(0b010001100U).set(2), Bitboard(0b010001100U))
    assertEquals(Bitboard(0b010001100U).set(2, false), Bitboard(0b010001000U))
    assertEquals(Bitboard(0b010001100U).set(8), Bitboard(0b110001100U))
    assertEquals(Bitboard(0b010010000U).set(8), Bitboard(0b110010000U))
    assertEquals(Bitboard(0b111010000U).set(8, false), Bitboard(0b011010000U))
    assertEquals(Bitboard(0b111010000U).set(0), Bitboard(0b111010001U))
    assertEquals(Bitboard(0b111010001U).set(0), Bitboard(0b111010001U))
    assertEquals(Bitboard(0b111010111U).set(0, false), Bitboard(0b111010110U))
    assertEquals(Bitboard(0b111010110U).set(0, false), Bitboard(0b111010110U))
    assertEquals(Bitboard(0b111010000U).set(5, true), Bitboard(0b111110000U))
    assertEquals(Bitboard(0b111110001U).set(5, true), Bitboard(0b111110001U))
    assertEquals(Bitboard(0b111110111U).set(5, false), Bitboard(0b111010111U))
    assertEquals(Bitboard(0b111010110U).set(5, false), Bitboard(0b111010110U))
  }

  @Test
  fun orTest() {
    assertEquals(
      Bitboard(0b010001100U) or Bitboard(0b10110110U), Bitboard(0b10111110U)
    )
    assertEquals(
      Bitboard(0b10110110U) or Bitboard(0b010001100U), Bitboard(0b10111110U)
    )
    assertEquals(Bitboard(0b111111111U) or Bitboard(), Bitboard(0b111111111U))
    assertEquals(
      Bitboard(0b011101111U) or Bitboard(0b100011111U), Bitboard(0b111111111U)
    );
    assertEquals(
      Bitboard(0b111111111U) or Bitboard(0b010000001U), Bitboard(0b111111111U)
    );
    assertEquals(
      Bitboard(0b111110000U) or Bitboard(0b1111U), Bitboard(0b111111111U)
    );
    assertEquals(
      Bitboard(0b111110000U) or Bitboard(0b1111U), Bitboard(0b111110000U or 0b1111U)
    );
  }

  @Test
  fun invTest() {
    assertEquals(Bitboard(0b010001100U).inv(), Bitboard(0b101110011U))
    assertEquals(Bitboard(0b100000001U).inv(), Bitboard(0b011111110U))
    assertEquals(Bitboard(0b011111110U).inv(), Bitboard(0b100000001U))
    assertEquals(Bitboard(0b010101010U).inv(), Bitboard(0b101010101U))
    assertEquals(Bitboard(0b101010101U).inv(), Bitboard(0b010101010U))
    assertEquals(Bitboard(0b101U).inv(), Bitboard(0b111111010U))
    assertEquals(Bitboard(0b111111111U).inv(), Bitboard(0b0U))
    assertEquals(Bitboard(0b0U).inv(), Bitboard(0b111111111U))
    assertEquals(Bitboard(0b0U).inv().inv(), Bitboard(0b0U))
  }

  @Test
  fun shiftTest() {
    assertEquals(Bitboard(0b111U).shift(Direction.North), Bitboard(0b111000U))
    assertEquals(Bitboard(0b111000111U).shift(Direction.North), Bitboard(0b111000U))
    assertEquals(Bitboard(0b1010U).shift(Direction.NorthEast), Bitboard(0b10100000U))
    assertEquals(Bitboard(0b10110000U).shift(Direction.NorthEast), Bitboard(0b100000000U))
    assertEquals(
      Bitboard(intArrayOf(0, 3, 6)).shift(Direction.East),
      Bitboard(intArrayOf(1, 4, 7))
    )
    assertNotEquals(
      Bitboard(intArrayOf(0, 3, 6)).shift(Direction.East), Bitboard(0b111U)
    )
    assertEquals(
      Bitboard(intArrayOf(1, 2, 4, 5, 7, 8)).shift(Direction.East), Bitboard(intArrayOf(2, 5, 8))
    )
    assertEquals(
      Bitboard(intArrayOf(3, 4, 7)).shift(Direction.SouthEast), Bitboard(intArrayOf(1, 2, 5))
    )
    assertEquals(
      Bitboard(intArrayOf(0, 1, 8)).shift(Direction.SouthEast), Bitboard(0U)
    )
    assertEquals(Bitboard(0b111U), Bitboard(0b111000U).shift(Direction.South))
    assertEquals(Bitboard(0b111000111U).shift(Direction.South), Bitboard(0b111000U))
    assertEquals(Bitboard(0b1010U), Bitboard(0b10100000U).shift(Direction.SouthWest))
    assertEquals(Bitboard(0b00010000U), Bitboard(0b100000000U).shift(Direction.SouthWest))
    assertEquals(Bitboard(intArrayOf(2, 4, 6)).shift(Direction.SouthWest), Bitboard(0b1U))
    assertEquals(
      Bitboard(intArrayOf(0, 3, 6)),
      Bitboard(intArrayOf(1, 4, 7)).shift(Direction.West)
    )
    assertEquals(
      Bitboard(intArrayOf(1, 4, 7)), Bitboard(intArrayOf(2, 5, 8)).shift(Direction.West)
    )
    assertEquals(
      Bitboard(0U), Bitboard(intArrayOf(0, 3, 6)).shift(Direction.West)
    )
    assertEquals(
      Bitboard(intArrayOf(3, 4, 7)),
      Bitboard(intArrayOf(1, 2, 5)).shift(Direction.NorthWest)
    )
    assertEquals(
      Bitboard(), Bitboard().shift(Direction.NorthWest)
    )
    assertEquals(
      Bitboard(intArrayOf(6)),
      Bitboard(intArrayOf(0, 4, 8)).shift(Direction.NorthWest)
    )
    assertEquals(LEFT_COLUMN.shift(Direction.East, 0), LEFT_COLUMN)
    assertEquals(LEFT_COLUMN.shift(Direction.West, 0), LEFT_COLUMN)
    assertEquals(BOTTOM_ROW.shift(Direction.North, 0), BOTTOM_ROW)
    assertEquals(BOTTOM_ROW.shift(Direction.South, 0), BOTTOM_ROW)

    assertThrows(IllegalArgumentException::class.java) { BOTTOM_ROW.shift(Direction.South, -1) }
    // TODO: Add tests for steps > 1
  }

  @Test
  fun positionsTest() {
    assertArrayEquals(intArrayOf(0, 1, 6, 8), Bitboard(0b101000011U).positions())
    assertArrayEquals(intArrayOf(), Bitboard().positions())
    assertArrayEquals(
      intArrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8),
      Bitboard(0b111111111U).positions()
    )
    assertArrayEquals(intArrayOf(5), Bitboard(0b100000U).positions())
  }

  @Test
  fun rowsTest() {
    assertEquals(BOTTOM_ROW, Bitboard(0b111U))
    assertEquals(BOTTOM_ROW.shift(Direction.North), Bitboard(0b111000U))
    assertEquals(BOTTOM_ROW.shift(Direction.North, 2), Bitboard(0b111000000U))
  }

  @Test
  fun columnsTest() {
    assertEquals(LEFT_COLUMN, Bitboard(0b1001001U))
    assertEquals(LEFT_COLUMN.shift(Direction.East), Bitboard(intArrayOf(1, 4, 7)))
    assertEquals(LEFT_COLUMN.shift(Direction.East, 2), Bitboard(intArrayOf(2, 5, 8)))
  }

  @Test
  fun diagonalsTest() {
    assertEquals(POSITIVE_DIAGONAL, Bitboard(intArrayOf(0, 4, 8)))
    assertEquals(NEGATIVE_DIAGONAL, Bitboard(intArrayOf(2, 4, 6)))
  }
}