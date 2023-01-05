package com.siyaman.tictactoe

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class AIPlayerTest {
  @Test
  fun winAfter2Moves() {
    val game = Game(Player.X, Board(intArrayOf(6), intArrayOf(8)))
    assertEquals(0, selectMove(game))
  }

  @Test
  fun blockXWin() {
    val game = Game(Player.O, Board(intArrayOf(0, 6), intArrayOf(8)))
    assertEquals(3, selectMove(game))
  }

  @Test
  fun dontLose() {
    val game = Game(Player.O, Board(intArrayOf(0), intArrayOf()))
    for (xMove in intArrayOf(8, 7, 2, 3)) {
      assertEquals(Status.OTurn, game.status)
      game.executeMove(selectMove(game))
      game.executeMove(xMove)
    }
    assertEquals(true, game.status == Status.Draw || game.status is Status.OWon)
  }
}