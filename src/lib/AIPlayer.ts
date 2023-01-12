// Need to define Player and Game
// import {Player} from "./Player.ts";
// import {Game} from "./Game.ts";

const WIN_VALUE = 10.0;

/**
 * Calculate score of the Game for a player
 *
 * @param[game]              The game for which to calculate the score
 * @param[maximizingPlayer]  The player
 *
 * @return     Score of the current Game {score ∈ ℚ}
 */
function evaluationFunction(game: Game, maximizingPlayer: Player) {
  let score = 0;
  switch (game.status) {
    case Status.XWon
      score = maximizingPlayer == Player.X ? WIN_VALUE : -WIN_VALUE;
      break;
    case Status.OWon
      score = maximizingPlayer == Player.O ? WIN_VALUE : -WIN_VALUE;
      break;
  }
  // More pieces results in longer game
  return score + game.board.emptySpaces.inv().count;
} 

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
function alphabeta(
  game: Game, maximizingPlayer: Player, depth: Int,
  alpha: Double = NEGATIVE_INFINITY, beta: Double = POSITIVE_INFINITY
): Double {
  let newAlpha = alpha;
  let newBeta = beta;
  const moves = game.moves;
  if (depth == 0 || moves.isEmpty()) {
    return evaluationFunction(game, maximizingPlayer);
  }

  const isSamePlayer = game.player == maximizingPlayer;
  let result = isSamePlayer ? Number.NEGATIVE_INFINITY : Number.POSITIVE_INFINITY;
  for (const move of moves) {
    if (newAlpha >= newBeta) break;
    const nextGame = game.cloneDeep()
    nextGame.executeMove(move)
    const rating = alphabeta(
      nextGame, maximizingPlayer, depth - 1,
      newAlpha, newBeta
    )
    if (isSamePlayer) {
      result = Math.max(result, rating)
      newAlpha = Math.max(newAlpha, result)
    } else {
      result = Math.min(result, rating)
      newBeta = Math.min(newBeta, result)
    }
  }
  return result
}

/**
 * Difficulty of artificial intelligence (AI) player
 */
const Difficulty = {
  Easy: 2,
  Medium: 3,
  Hard: 5
} as const;

/**
 * Select the best move given the game and difficulty
 *
 * @param[game]       Tic tac toe game for which to find move
 * @param[difficulty] Difficulty of AI player. This determines how many steps ahead the AI searches
 *                    for the best move
 *
 * @return     Best move for selected difficulty
 */
export function selectMove(game: Game, difficulty: Difficulty = Difficulty.Medium): number {
  console.assert(!game.isGameOver, `Game is over, status ${game.status}`)
  const moves = game.moves
  if (moves.size == 1) return moves.first()

  let maxRating = NEGATIVE_INFINITY
  let bestMove = moves.first()
  for (move in moves) {
    const nextGame = game.cloneDeep()
    nextGame.executeMove(move)
    const rating =
      alphabeta(nextGame, game.player, difficulty.depth, maxRating)
    if (maxRating < rating) {
      maxRating = rating
      bestMove = move
    }
  }
  return bestMove
}