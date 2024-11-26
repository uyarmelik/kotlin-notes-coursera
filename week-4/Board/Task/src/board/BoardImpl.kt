package board

import board.Direction.*

fun createSquareBoard(width: Int): SquareBoard = SquareBoardDirection(width)
fun <T> createGameBoard(width: Int): GameBoard<T> = GameBoardDirection(width)

open class SquareBoardDirection(final override val width: Int) : SquareBoard {

    private val cells = (1..width).flatMap { i -> (1..width).map { j -> Cell(i, j) } }

    override fun getCellOrNull(i: Int, j: Int): Cell? = cells.filter { it.i == i }.filter { it.j == j }.firstOrNull()

    override fun getCell(i: Int, j: Int): Cell = getCellOrNull(i,j)!!

    override fun getAllCells(): Collection<Cell> = cells

    override fun getRow(i: Int, jRange: IntProgression): List<Cell> {
        val row = cells.filter { it.i == i }
        return jRange.takeWhile { it <= width }.map { step -> row.find { cell -> cell.j == step  } as Cell }
    }

    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> {
        val column = cells.filter { it.j == j }
        return iRange.takeWhile { it <= width }.map { step -> column.find { cell -> cell.i == step  } as Cell }
    }

    override fun Cell.getNeighbour(direction: Direction): Cell? =  when(direction) {
        UP -> getCellOrNull(i-1, j)
        DOWN -> getCellOrNull(i+1, j)
        LEFT -> getCellOrNull(i, j-1)
        RIGHT -> getCellOrNull(i, j+1)
    }
}

class GameBoardDirection<T>(width: Int) : SquareBoardDirection(width), GameBoard<T> {

    private val board = getAllCells().map { it to null }.toMap<Cell, T?>().toMutableMap()

    override fun get(cell: Cell): T? = board[cell]

    override fun set(cell: Cell, value: T?) {
        board[cell] = value
    }

    override fun filter(predicate: (T?) -> Boolean): Collection<Cell> = board.entries.filter { predicate(it.value) }.map { it.key }


    override fun find(predicate: (T?) -> Boolean): Cell? = board.entries.find { predicate(it.value) }?.key

    override fun any(predicate: (T?) -> Boolean): Boolean = board.entries.any { predicate(it.value) }

    override fun all(predicate: (T?) -> Boolean): Boolean = board.entries.all { predicate(it.value) }

}