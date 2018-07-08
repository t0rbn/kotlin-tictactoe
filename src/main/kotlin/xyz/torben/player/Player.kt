package xyz.torben.player

import xyz.torben.board.Board
import xyz.torben.board.BoardFieldValue

interface Player {
    fun makeMove(board: Board)
    fun informWinner(winner: BoardFieldValue)
}
