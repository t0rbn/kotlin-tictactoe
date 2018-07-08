package xyz.torben.player

import xyz.torben.board.Board
import xyz.torben.board.BoardFieldValue
import xyz.torben.board.Coordinate
import xyz.torben.board.Field

class ComputerPlayer(val myPlayerValue: BoardFieldValue) : Player {

    override fun informWinner(winner: BoardFieldValue) {
        // nothing to do
    }

    private enum class BoardFieldScore(val numeric: Int) {
        INSTANT_WIN(4),
        BLOCK_INSTANT_WIN(3),
        OPTIONAL_WIN(2),
        BLOCK_OPTIONAL_WIN(1),
        FIRST_IN_ROW(0),
        NOT_FREE(-1);

    }

    private fun getScoreForLine(line: List<Field>, fieldValue: BoardFieldValue): BoardFieldScore {
        val contrahent = if (fieldValue == BoardFieldValue.X) BoardFieldValue.O else BoardFieldValue.X
        val ownInLine = line.count { it.value == fieldValue }
        val contrahentInLine = line.count { it.value == contrahent }

        if (ownInLine == 3 || contrahentInLine == 3) return BoardFieldScore.NOT_FREE
        if (ownInLine == 2) return BoardFieldScore.INSTANT_WIN
        if (contrahentInLine == 2) return BoardFieldScore.BLOCK_INSTANT_WIN
        if (contrahentInLine == 1) return BoardFieldScore.OPTIONAL_WIN
        if (ownInLine == 1) return BoardFieldScore.BLOCK_OPTIONAL_WIN
        if (ownInLine == 0 && contrahentInLine == 0) return BoardFieldScore.FIRST_IN_ROW
        return BoardFieldScore.NOT_FREE
    }

    private fun getBestScoreForField(field: Field, board: Board): BoardFieldScore {
        if (field.value != BoardFieldValue.NONE) return BoardFieldScore.NOT_FREE
        return board.getRelevantLines(field.pos)
                .map { getScoreForLine(it, myPlayerValue) }
                .sortedByDescending { it.numeric }
                .first()
    }

    private fun getPosWithBestScore(board: Board): Coordinate {
        val scoreMap: MutableMap<BoardFieldScore, MutableList<Field>> = mutableMapOf()
        board.getAllFields().forEach {
            val scoreForField = getBestScoreForField(it, board)
            scoreMap[scoreForField] = scoreMap[scoreForField] ?: mutableListOf()
            scoreMap[scoreForField]!!.add(it)
        }

        val highestAvailableScore = scoreMap.keys.sortedByDescending { it.numeric }.first()
        return scoreMap[highestAvailableScore]!!.shuffled().first().pos
    }

    override fun makeMove(board: Board) {
        board.setField(Field(getPosWithBestScore(board), myPlayerValue))
    }
}
