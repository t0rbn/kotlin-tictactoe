package xyz.torben.misc

import xyz.torben.board.Board
import xyz.torben.board.Coordinate

class BoardPrinter(val board: Board) {
    fun print() {
        val size = board.getCol(Coordinate(0, 0)).size
        val printString = (0..(size - 1)).map {
            board.getRow(Coordinate(0, it)).map { it.value.getPrintableString() }.joinToString(" | ")
        }.joinToString( "\n---------\n")
        println(printString)
    }
}
