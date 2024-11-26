package games.gameOfFifteen

import board.Cell
import board.Direction
import board.GameBoard
import board.createGameBoard
import games.game.Game

/*
 * Implement the Game of Fifteen (https://en.wikipedia.org/wiki/15_puzzle).
 * When you finish, you can play the game by executing 'PlayGameOfFifteen'.
 */
fun newGameOfFifteen(initializer: GameOfFifteenInitializer = RandomGameInitializer()): Game =
    GameOfFifteen(initializer)

class GameOfFifteen(val initializerX: GameOfFifteenInitializer): Game {
    lateinit var boardY: GameBoard<Int>
    override fun initialize() {
        boardY = createGameBoard<Int>(4)
        for (i in 1..4) {
            for (j in 1..4) {
                if (i == 4 && j == 4) boardY[Cell(i, j)] = null
                else boardY[Cell(i, j)] = initializerX.initialPermutation[ 4*i - 4 + j - 1]
            }
        }
    }

    override fun canMove(): Boolean = true

    override fun get(i: Int, j: Int): Int? = boardY[Cell(i, j)]

    override fun processMove(direction: Direction) {
        val cell = boardY.find { it == null } ?: return
        val swap: Cell = with(boardY) {
            cell.getNeighbour(direction.reversed())
        } ?: return
        val temp = boardY[cell]
        boardY[cell] = boardY[swap]
        boardY[swap] = temp
    }

    override fun hasWon(): Boolean = boardY.getAllCells().map { boardY[it] }.take(15) == List<Int>(15){it+1}

}

