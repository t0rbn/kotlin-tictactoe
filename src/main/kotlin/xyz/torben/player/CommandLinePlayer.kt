package xyz.torben.player

import xyz.torben.board.Board
import xyz.torben.board.BoardFieldValue
import xyz.torben.board.Coordinate
import xyz.torben.board.Field
import xyz.torben.misc.BoardPrinter

class CommandLinePlayer(val fieldValue: BoardFieldValue): Player {

    override fun makeMove(board: Board) {
        println("Current board is:")
        board.print()

        println("Enter coords for ${fieldValue.getPrintableString()}")
        val input = readLine().orEmpty().toCharArray().map{Integer.parseInt(it.toString())}
        board.setField(Field(Coordinate(input[0], input[1]), fieldValue))
    }

    override fun informWinner(winner: BoardFieldValue) {
        if (winner == BoardFieldValue.NONE) {
            println("Tie!")
        } else {
            println("${winner.getPrintableString()} wins!")
        }
    }
}
