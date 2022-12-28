package com.siyaman.tictactoe

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlin.experimental.and
import kotlin.experimental.inv
import kotlin.experimental.or
import kotlin.experimental.xor

private const val ZERO = 0.toShort()
private const val MAX = ((1 shl Bitboard.AREA) - 1).toShort()

/**
 * Returns a number where the index is 1 and everything else is 0 in binary form
 */
private fun mask(index: Int): Short {
  require(index in 0 until Bitboard.AREA) { "index should be within ∈ [0, ${Bitboard.AREA}), was $index" }
  return (1U shl index).toShort()
}

/**
 *
 * Bit array structure for two dimensional game board that
 * represents a particular piece type and board space. Bitboard is an inline
 * value class and behaves similar to a primitive. Allows primitive bit
 * operations
 *
 * Index ϵ [0,8]
 *
 *     6 | 7 | 8
 *    -----------
 *     3 | 4 | 5
 *    -----------
 *     0 | 1 | 2
 *
 */
@Parcelize
@JvmInline
value class Bitboard(private val data: Short = 0) : Parcelable {
  constructor(number: UInt) : this(number.toShort())
  constructor(indices: IntArray) : this(if (indices.isEmpty()) 0 else indices.map { mask(it) }
    .reduce { acc, value -> acc or value })

  constructor(indices: IntProgression) : this(if (indices.isEmpty()) 0.toShort() else indices.map {
    mask(
      it
    )
  }
    .reduce { acc, value -> acc or value })

  init {
    require(data in ZERO..MAX) { "Bitboard should be between ∈ [$ZERO, $MAX], was $data" }
  }

  /**
   * @brief      Set bit at given index to true (1) or false (0)
   *
   * @param      index  Index ϵ [0,8] of the bit to be modified
   * @param      value  True sets the bit to 1 otherwise to 0. Default is true
   *
   * @return     Returns a new Bitboard with the requested bit set to the value
   */
  fun set(index: Int, value: Boolean = true): Bitboard {
    val mask = mask(index)
    return Bitboard(if (value) data or mask else data and mask.inv())
  }

  /**
   * @brief      Tests the bit at specified index. True for 1 and false for 0
   *
   * @param      index  Index ϵ [0,8] of the bit to be retrieved
   *
   * @return     True for 1 and false for 0
   */
  fun test(index: Int) = data and mask(index) != 0.toShort()
  val count get() = data.countOneBits()

  infix fun or(rhs: Bitboard) = Bitboard(data or rhs.data)
  infix fun xor(rhs: Bitboard) = Bitboard(data xor rhs.data)
  infix fun and(rhs: Bitboard) = Bitboard(data and rhs.data)
  fun inv() = Bitboard(data.inv() and MAX)

  private fun shiftUnsafe(direction: Direction, steps: Int = 1): Bitboard {
    require(steps >= 0) { "steps must be non-negative, was $steps" }
    if (steps == 0) return this
    if (steps >= LENGTH) return Bitboard()

    return Bitboard(
      when (direction) {
        Direction.North -> data.toUInt() shl (LENGTH * steps)
        Direction.NorthEast -> data.toUInt() shl ((LENGTH + 1) * steps)
        Direction.NorthWest -> data.toUInt() shl ((LENGTH - 1) * steps)
        Direction.East -> data.toUInt() shl steps
        Direction.West -> data.toUInt() shr steps
        Direction.South -> data.toUInt() shr (LENGTH * steps)
        Direction.SouthEast -> data.toUInt() shr ((LENGTH - 1) * steps)
        Direction.SouthWest -> data.toUInt() shr ((LENGTH + 1) * steps)
      }
    )
  }

  fun shift(direction: Direction, steps: Int = 1) =
    if (steps == 0) this else (this and blockWrappingMask(direction, steps)).shiftUnsafe(
      direction,
      steps
    )

  fun positions() = (0 until Bitboard.AREA).filter { test(it) }.toIntArray()

  companion object {
    const val LENGTH = 3
    const val AREA = LENGTH * LENGTH

    /**
     * Creates a Bitboard mask which prevents wrapping when iterating in a
     * direction on a Bitboard. Since a Bitboard is a 1-Dimensional container
     * representing a 2-Dimensional board, blindly iterating in a direction will
     * cause the iteration to wrap onto another column or row. Applying the mask
     * from this function will zero out boundary coordinates so that iteration
     * will not go past these boundaries.
     *
     * For example when iterating in Direction::East then the right most column
     * (Column H) is masked to 0 so that iteration will stop once the right most
     * column is reached and prevents wrapping on to the next row.
     *
     * @param direction - The cardinal direction in which wrapping should be
     * prevented
     * @param steps - The number of steps the bitboard is being shifted. The mask will
     * expand in the opposite direction according to the number of steps that will be shifted
     * @return - Bitboard mask which should be applied to the Board before
     * iterating
     */
    private fun blockWrappingMask(direction: Direction, steps: Int = 1) = Bitboard(
      when (direction) {
        Direction.North -> 0b000111111U
        Direction.NorthEast -> 0b000011011U
        Direction.East -> 0b011011011U
        Direction.SouthEast -> 0b011011000U
        Direction.South -> 0b111111000U
        Direction.SouthWest -> 0b110110000U
        Direction.West -> 0b110110110U
        Direction.NorthWest -> 0b000110110U
      }
    ).shiftUnsafe(direction.opposite(), steps - 1)
  }
}

val BOTTOM_ROW = Bitboard(0 until Bitboard.LENGTH)
val LEFT_COLUMN = Bitboard(0 until Bitboard.AREA step Bitboard.LENGTH)

// Bottom left to top right (similar to line with positive slope)
val POSITIVE_DIAGONAL = Bitboard(0 until Bitboard.AREA step (Bitboard.LENGTH + 1))

// Top left to bottom right (similar to line with negative slope)
val NEGATIVE_DIAGONAL =
  Bitboard(Bitboard.LENGTH - 1 until Bitboard.AREA - 1 step Bitboard.LENGTH - 1)


