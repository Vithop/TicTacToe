package com.siyaman.tictactoe

import org.junit.Assert.*
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class GameTest {
  @Test
  fun constructorTest() {
    val game = Game()
    assertEquals(game.player, Player.X)
    assertEquals(game.board, Board())
    assertEquals(game.status, Status.XTurn)

    val game2 = Game(Player.X, Board(intArrayOf(6), intArrayOf(0)))
    assertEquals(game2.player, Player.X)
    assertEquals(game2.board, Board(0b1000000U, 0b1U))
    assertEquals(game2.status, Status.XTurn)
  }

  @Test
  fun midGameTest() {
    val game = Game(Player.X, Board(intArrayOf(0, 6), intArrayOf(3, 5)))
    assertEquals(game.player, Player.X)
    assertEquals(game.status, Status.XTurn)
    assertEquals(game.board, Board(intArrayOf(0, 6), intArrayOf(3, 5)))
    game.executeMove(4)

    assertEquals(game.player, Player.O)
    assertEquals(game.status, Status.OTurn)
    assertEquals(game.board, Board(intArrayOf(0, 4, 6), intArrayOf(3, 5)))
    game.executeMove(8)

    assertEquals(game.player, Player.X)
    assertEquals(game.status, Status.XTurn)
    assertEquals(game.getScore(Player.X), 3)
    assertEquals(game.getScore(Player.O), 3)
    assertEquals(game.board, Board(intArrayOf(0, 4, 6), intArrayOf(3, 5, 8)))
  }

  @Test
  fun midGamePlayerOTest() {
    val game = Game(Player.O, Board(intArrayOf(1, 2, 4), intArrayOf(0, 8)))
    assertEquals(game.player, Player.O)
    assertEquals(game.status, Status.OTurn)
    assertEquals(game.board, Board(intArrayOf(1, 2, 4), intArrayOf(0, 8)))
    game.executeMove(7)

    assertEquals(game.player, Player.X)
    assertEquals(game.status, Status.XTurn)
    assertEquals(game.board, Board(intArrayOf(1, 2, 4), intArrayOf(0, 7, 8)))
    game.executeMove(6)
  }

  @Test
  fun xWinsTest() {
    val game = Game(Player.X, Board(intArrayOf(0, 4, 6), intArrayOf(3, 5, 8)))
    game.executeMove(2)
    assertEquals(game.player, Player.O)
    assertEquals(game.status, Status.XWon(Bitboard(intArrayOf(2, 4, 6))))
    assertEquals(game.board, Board(intArrayOf(0, 2, 4, 6), intArrayOf(3, 5, 8)))
  }

  @Test
  fun oWonTest() {
    val game = Game(Player.X, Board(intArrayOf(1, 2, 4), intArrayOf(0, 7, 8)))
    game.executeMove(6)
    assertEquals(game.player, Player.O)
    assertEquals(game.status, Status.XWon(Bitboard(intArrayOf(2, 4, 6))))
    assertEquals(game.board, Board(intArrayOf(1, 2, 4, 6), intArrayOf(0, 7, 8)))
  }

  @Test
  fun statusTest() {
    assertEquals(
      Game(Player.O, Board(intArrayOf(0, 3, 6), intArrayOf(5, 8))).status,
      Status.XWon(Bitboard(intArrayOf(0, 3, 6)))
    )
    assertEquals(
      Game(Player.X, Board(intArrayOf(3, 4, 7), intArrayOf(0, 1, 2))).status,
      Status.OWon(Bitboard(intArrayOf(0, 1, 2)))
    )
    assertEquals(
      Game(Player.O, Board(intArrayOf(0, 2, 3, 4, 7), intArrayOf(1, 5, 6, 8))).status,
      Status.Draw
    )
  }

  @Test
  fun equalsTest() {
    assertEquals(
      Game(Player.X, Board(intArrayOf(1, 2, 4), intArrayOf(0, 7, 8))),
      Game(Player.X, Board(intArrayOf(1, 2, 4), intArrayOf(0, 7, 8)))
    )

    val game = Game(Player.X, Board(intArrayOf(1, 2, 4), intArrayOf(0, 7, 8)))
    game.executeMove(6)
    assertEquals(
      game,
      Game(Player.O, Board(intArrayOf(1, 2, 4, 6), intArrayOf(0, 7, 8)))
    )
    assert(game == Game(Player.O, Board(intArrayOf(1, 2, 4, 6), intArrayOf(0, 7, 8))))

    val game2 = Game(Player.X, Board(intArrayOf(1, 2, 4), intArrayOf(0, 7, 8)))
    val newGame = game2.copy()
    assert(game2 == newGame)
  }

  @Test
  fun notEqualTest() {
    val game = Game(Player.X, Board(intArrayOf(1, 2, 4), intArrayOf(0, 7, 8)))
    val gameCopy = game.copy()
    gameCopy.executeMove(3)
    assertNotEquals(game, gameCopy)
  }

  @Test
  fun noMovesAfterGameOverTest() {
    val game = Game(Player.O, Board(intArrayOf(0, 2, 4, 6), intArrayOf(3, 5, 8)))
    assertThrows(IllegalArgumentException::class.java) { game.executeMove(1) }
  }
}