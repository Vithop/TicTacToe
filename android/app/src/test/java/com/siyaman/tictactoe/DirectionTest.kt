package com.siyaman.tictactoe

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class DirectionTest {
  @Test
  fun oppositeTest() {
    assertEquals(Direction.North.opposite(), Direction.South)
    assertEquals(Direction.NorthEast.opposite(), Direction.SouthWest)
    assertEquals(Direction.East.opposite(), Direction.West)
    assertEquals(Direction.SouthEast.opposite(), Direction.NorthWest)
    assertEquals(Direction.South.opposite(), Direction.North)
    assertEquals(Direction.SouthWest.opposite(), Direction.NorthEast)
    assertEquals(Direction.West.opposite(), Direction.East)
    assertEquals(Direction.NorthWest.opposite(), Direction.SouthEast)

    Direction.values().forEach { assertEquals(it.opposite().opposite(), it) }
  }
}