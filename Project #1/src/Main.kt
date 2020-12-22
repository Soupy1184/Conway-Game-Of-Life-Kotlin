import kotlinx.coroutines.*
import kotlin.random.Random

const val boardSize = 9
var count = 0
val graphic = GameGUI(boardSize)
val board = Board(boardSize)

fun main() = runBlocking<Unit> {
    //random setup
    val startNumber = Random.nextInt(10, 50)
    for (i in 1..startNumber){
        board.setAlive(Cell(Random.nextInt(0, boardSize), Random.nextInt(0, boardSize), true))
    }

    //create the GUI for the board
    graphic.loadBoardInfo(board.getStatusUpdate()) //set GUI squares equal to board

    //run the loop to change board status.
    coroutineScope {
        launch(CoroutineName("run")) {update(graphic, board)}
    }
}

fun staticNext(){
    graphic.play = false
    board.generateNextState()
    graphic.loadBoardInfo(board.getStatusUpdate())
}

fun resetGame(){
    for (i in 1..Random.nextInt(10, 50)){
        board.setAlive(Cell(Random.nextInt(0, boardSize+1), Random.nextInt(0, boardSize+1), true))
    }
    graphic.loadBoardInfo(board.getStatusUpdate())
}

fun staticPrevious(){
    graphic.play = false
    graphic.loadBoardInfo(board.getPreviousState())
}

suspend fun update(graphic: GameGUI, board: Board){
    if (graphic.play){
        count++
        log("Generation $count")
        delay(1000) //wait 1 second
        board.generateNextState() //get the next state
        graphic.loadBoardInfo(board.getStatusUpdate()) //set the new state to the board GUI
        update(graphic, board)
    }
    else{
        delay(1000)
        update(graphic, board)
    }
}

fun log(msg: String) = println("[${Thread.currentThread().name}] $msg")

//    clearBoard()
//fun clearBoard(){
//    count = 0
//    for (y in 0..boardSize) {
//        for (x in 0..boardSize) {
//            board.setAlive(Cell(x, y, false))
//        }
//    }
//    graphic.loadBoardInfo(board.getStatusUpdate())
//}