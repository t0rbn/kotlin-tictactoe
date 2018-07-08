package xyz.torben.board

import xyz.torben.misc.BoardPrinter

class Board(val size: Int) {
    private val BOARD_MAX = size - 1
    private val fields = HashMap<Coordinate, BoardFieldValue>()
    private val printer = BoardPrinter(this)

    init {
        for (x in 0..(BOARD_MAX)) {
            for (y in 0..(BOARD_MAX)) {
                fields[Coordinate(x, y)] = BoardFieldValue.NONE
            }
        }
    }

    fun getAllFields(): List<Field> {
        return fields.keys.map { getField(it) }.toList();
    }

    fun getCol(pos: Coordinate): List<Field> {
        return fields.entries.filter { it.key.x == pos.x }.map { Field(it.key, it.value) }.sortedBy { it.pos.y }
    }

    fun getRow(pos: Coordinate): List<Field> {
        return fields.entries.filter { it.key.y == pos.y }.map { Field(it.key, it.value) }.sortedBy { it.pos.x }
    }

    private fun isInDiagonalUp(pos: Coordinate): Boolean {
        return pos.x == pos.y
    }

    private fun isInDiagonalDown(pos:Coordinate): Boolean {
        return pos.x == BOARD_MAX - pos.y
    }

    fun getDiagonalUp(): List<Field> {
        return fields.entries.filter { isInDiagonalUp(it.key) }.map { Field(it.key, it.value) }
    }

    fun getDiagonalDown(): List<Field> {
        return fields.entries.filter { isInDiagonalDown(it.key) }.map { Field(it.key, it.value) }
    }

    fun getRelevantLines(pos: Coordinate): List<List<Field>> {
        val returnList: MutableList<List<Field>> = mutableListOf()
        returnList.add(getRow(pos))
        returnList.add(getCol(pos))
        if (isInDiagonalUp(pos)) returnList.add(getDiagonalUp())
        if (isInDiagonalDown(pos)) returnList.add(getDiagonalDown())
        return returnList
    }

    fun getField(pos: Coordinate): Field {
        val value = if (fields[pos] != null) fields[pos] else BoardFieldValue.NONE
        return Field(pos, value!!)
    }

    fun setField(field: Field) {
        if (fields[field.pos] != BoardFieldValue.NONE) throw IllegalAccessException()
        fields[field.pos] = field.value
    }

    fun getWinner(): BoardFieldValue? {
        for (i in 0..BOARD_MAX) {
            val coordinate = Coordinate(i, i)
            val referenceValue = getField(coordinate).value
            val row = getRow(coordinate)
            val col = getCol(coordinate)

            if (referenceValue == BoardFieldValue.NONE) continue
            if (row.all { it.value == referenceValue} ) return referenceValue
            if (col.all { it.value == referenceValue } ) return referenceValue
        }

        val diagonalDownFirst = getDiagonalDown()[0]
        val diagonalUpFirst = getDiagonalUp()[0]

        if (getDiagonalUp().all { it.value != BoardFieldValue.NONE && it.value == diagonalUpFirst.value }) return diagonalDownFirst.value
        if (getDiagonalDown().all { it.value != BoardFieldValue.NONE && it.value == diagonalDownFirst.value }) return diagonalDownFirst.value

        if (getAllFields().none { it.value == BoardFieldValue.NONE }) return BoardFieldValue.NONE
        return null
    }

    fun print() {
        printer.print()
    }


}

