package xyz.torben

import xyz.torben.board.Board
import xyz.torben.board.BoardFieldValue
import xyz.torben.player.ComputerPlayer
import xyz.torben.player.GuiPlayer
import xyz.torben.player.Player

class Game {
    companion object {
        @JvmStatic fun main(args: Array<String>) {
            val board = Board(3)
            val players = listOf(GuiPlayer(BoardFieldValue.X), ComputerPlayer(BoardFieldValue.O))

            var winner: BoardFieldValue? = null
            var currentPlayer: Player = players[0]

            while (winner == null) {
                currentPlayer.makeMove(board)
                winner = board.getWinner()
                currentPlayer = if (currentPlayer == players[0]) players[1] else players[0]
            }

            players.forEach { it.informWinner(winner) }
        }
    }
}
