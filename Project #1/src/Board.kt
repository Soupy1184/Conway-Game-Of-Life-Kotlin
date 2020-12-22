import java.awt.Color

data class Cell(val x: Int, val y: Int, var status: Boolean)

class Board(private val boardSize: Int) {

    private var board = (0..boardSize).map {y->
        (0..boardSize).map { x-> Cell(x, y, false) }
    }

    private var previousState = board

    //for random setup
    fun setAlive(cell: Cell){
        getCell(cell).status = true
    }

    //get neighbours for each location
    private fun getNeighbourLocations(cell: Cell): List<Cell> {
        val x = cell.x
        val y = cell.y
        return listOf(
            Cell(x-1, y+1, cell.status), Cell(x, y+1, cell.status), Cell(x+1, y+1, cell.status),
            Cell(x-1, y, cell.status), Cell(x+1, y, cell.status),
            Cell(x-1, y-1, cell.status), Cell(x, y-1, cell.status), Cell(x+1, y-1, cell.status)
        )
    }

    //returns true or false for location
    private fun isValidLocation(cell: Cell): Boolean {
        return cell.x in 0..boardSize && cell.y in 0..boardSize
    }

    //return the current status of a location
    private fun getCell(cell: Cell) = board[cell.y][cell.x]
    private fun getCellStatus(x: Int, y: Int):Boolean = board[x][y].status

    //returns a list of neighbours, filters out non-valid locations
    private fun getNeighbours(cell: Cell): List<Cell> {
        return getNeighbourLocations(cell)
            .filter { isValidLocation(it) }
            .map { getCell(it) }
    }

    //counts alive neighbours
    private fun livingNeighbourCount(cells: List<Cell>): Int {
        var liveCount = 0
        for (i in cells){
            if(i.status)
                liveCount++
        }
        return liveCount
    }

    //changes the board to the next state of alive or dead locations
    fun generateNextState() {
        previousState = board
        board = nextState()
    }

    private fun nextState(): List<List<Cell>> {
        var status:Boolean
        return (0..boardSize).map { y ->
            (0..boardSize).map { x ->
                status = getCellStatus(x, y)
                val nextState = getState(Cell(x, y, status))
                Cell(x, y, nextState)
            }
        }
    }

    private fun getState(cell: Cell): Boolean { //returns the status of locations
        val neighboursAlive = livingNeighbourCount(getNeighbours(cell))

        return if (cell.status && neighboursAlive in 2..3) true
            else !cell.status && neighboursAlive == 3
    }

    fun getStatusUpdate(): List<List<Cell>> = board
    fun getPreviousState(): List<List<Cell>> {
        board = previousState
        return previousState
    }
}
