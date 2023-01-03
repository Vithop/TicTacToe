package com.siyaman.tictactoe

enum class Direction {
  North,
  NorthEast,
  East,
  SouthEast,
  South,
  SouthWest,
  West,
  NorthWest;

  fun opposite() = when (this) {
    North -> South
    NorthEast -> SouthWest
    East -> West
    SouthEast -> NorthWest
    South -> North
    SouthWest -> NorthEast
    West -> East
    NorthWest -> SouthEast
  }
};