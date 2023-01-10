package com.siyaman.tictactoe

import kotlin.Double.Companion.NEGATIVE_INFINITY
import kotlin.Double.Companion.POSITIVE_INFINITY
import kotlin.math.max
import kotlin.math.min

private const val WIN_VALUE = 10.0

/**
 * Calculate score of the Game for a player
 *
 * @param[game]              The game for which to calculate the score
 * @param[maximizingPlayer]  The player
 *
 * @return     Score of the current Game {score ∈ ℚ}
 */
private fun evaluationFunction(game: Game, maximizingPlayer: Player) = when (game.status) {
  is Status.XWon -> if (maximizingPlayer == Player.X) WIN_VALUE else -WIN_VALUE
  is Status.OWon -> if (maximizingPlayer == Player.O) WIN_VALUE else -WIN_VALUE
  else -> 0.0
  // More pieces results in longer game
} + game.board.emptySpaces.inv().count

/**
 * Calculate the highest score possible up to the search depth
 * for given player reachable from the provided Game. This function
 * uses the minimax algorithm which assumes the maximizing player
 * picks the move with the highest score and the opposing player
 * picks the move with the lowest score. Additionally the alpha-beta
 * search optimization is used to eliminate branches that cannot
 * influence the final decision.
 *
 * @param[game]                Tic tac toe game that represents the state
 * @param[maximizingPlayer]    The maximizing player
 * @param[depth]               Maximum depth to search to find best score
 * @param[alpha]               Lower bound of score
 * @param[beta]                Upper bound of score
 *
 * @return     Highest score possible for provided Game and depth searched
 */
private fun alphabeta(
  game: Game, maximizingPlayer: Player, depth: Int,
  alpha: Double = NEGATIVE_INFINITY, beta: Double = POSITIVE_INFINITY
): Double {
  var newAlpha = alpha
  var newBeta = beta
  val moves = game.moves
  if (depth == 0 || moves.isEmpty()) {
    return evaluationFunction(game, maximizingPlayer)
  }

  val isSamePlayer = game.player == maximizingPlayer
  var result = if (isSamePlayer) NEGATIVE_INFINITY else POSITIVE_INFINITY
  for (move in moves) {
    if (newAlpha >= newBeta) break
    val nextGame = game.deepCopy()
    nextGame.executeMove(move)
    val rating = alphabeta(
      nextGame, maximizingPlayer, depth - 1,
      newAlpha, newBeta
    )
    if (isSamePlayer) {
      result = max(result, rating)
      newAlpha = max(newAlpha, result)
    } else {
      result = min(result, rating)
      newBeta = min(newBeta, result)
    }
  }
  return result
}

/**
 * Difficulty of artificial intelligence (AI) player
 */
enum class Difficulty(val depth: Int) {
  Easy(2),
  Medium(3),
  Hard(5)
}

/**
 * Select the best move given the game and difficulty
 *
 * @param[game]       Tic tac toe game for which to find move
 * @param[difficulty] Difficulty of AI player. This determines how many steps ahead the AI searches
 *                    for the best move
 *
 * @return     Best move for selected difficulty
 */
fun selectMove(game: Game, difficulty: Difficulty = Difficulty.Medium): Int {
  require(!game.isGameOver) { "Game is over, status ${game.status}" }
  val moves = game.moves
  if (moves.size == 1) return moves.first()

  var maxRating = NEGATIVE_INFINITY
  var bestMove = moves.first()
  for (move in moves) {
    val nextGame = game.deepCopy()
    nextGame.executeMove(move)
    val rating =
      alphabeta(nextGame, game.player, difficulty.depth, maxRating)
    if (maxRating < rating) {
      maxRating = rating
      bestMove = move
    }
  }
  return bestMove
}