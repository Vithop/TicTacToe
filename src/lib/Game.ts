/**
 * @details     Encapsulates the rules of a Tic tac toe game and manages play.
 *              Keeps track of the current state of the game including the board
 *              and whose turn it is.
 * @example
 *   const game = new Game();
 *   game.executeMove(3);
 */

export const Status = {
  XTurn: 0,
  OTurn: 1,
  Draw: 2,
  XWon: class {
    constructor(winPositions){ this.winPositions = winPositions; }
    toString() { return "X Wins!"; }
  },
  OWon: class {
    constructor(winPositions){ this.winPositions = winPositions; }
    toString() { return "O Wins!"; }
  }
}

function isBoardFull(boardState: string[]) {
  return boardState.every(value => value !== '');
}

const winMap = [
  [0, 1, 2],
  [3, 4, 5],
  [6, 7, 8],
  [0, 3, 6],
  [1, 4, 7],
  [2, 5, 8],
  [0, 4, 8],
  [2, 4, 6]
];

function isWinPattern(toes: string[], winPattern: number[]) {
  return (
    toes[winPattern[0]] === toes[winPattern[1]] &&
    toes[winPattern[1]] === toes[winPattern[2]] &&
    toes[winPattern[0]] !== ''
  )
}

export default class Game{
  #isXTurn: boolean;
  #boardState: string[];
  constructor(isXTurn = true, boardState = Array(9).fill('')){
    this.#isXTurn = isXTurn;
    this.#boardState = boardState;
  }

  get isXTurn(): boolean { return this.#isXTurn; }
  get boardState(): string[] { return this.#boardState; }
  get status() {
    const winPositions = winMap.find(winPattern => isWinPattern(this.#boardState, winPattern));
    if(winPositions){
      switch(this.#boardState[winPositions.at(0)]){
        case 'X': return new Status.XWon(winPositions);
        case 'O': return new Status.OWon(winPositions);
      }
    }
    if (isBoardFull(this.#boardState)) return Status.Draw;
    return this.#isXTurn ? Status.XTurn : Status.OTurn;
  }
  get isGameOver(): boolean { 
    const status = this.status;
    return status !== Status.XTurn && status != Status.OTurn;
  }

  executeMove(i: number): void {
    console.assert(!this.isGameOver, `Game is over, status ${this.status}`);
    console.assert(this.#boardState[i] === '', 
      `Tile at index ${i} is already occupied with ${this.#boardState[i]}`);
    // Log invalid moves and then ignore
    if(this.isGameOver || this.#boardState[i] != '') return;
    
    this.#boardState[i] = this.#isXTurn ? 'X' : 'O';
    this.#isXTurn = !this.#isXTurn;
  }
  deepCopy(): Game {
    return new Game(this.#isXTurn, [...this.#boardState]);
  }
}