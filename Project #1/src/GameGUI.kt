import java.awt.Color
import java.awt.GridLayout
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.BorderFactory
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JLabel

class GameGUI(boardSize:Int) : JFrame("Project #1 - Game Of Life") {
    private val squares = (boardSize + 1) * 60
    private var boardLocation = (0..boardSize).map {
        (0..boardSize).map { JLabel() }
    }
    var play:Boolean = false

    init {
        layout = GridLayout(boardSize + 2, boardSize + 2)
        val content = contentPane
        content.background = Color.WHITE
        for (y in 0..boardSize) {
            for (x in 0..boardSize) {
                val location = boardLocation[y][x]
                location.background = Color.WHITE
                location.border = BorderFactory.createLineBorder(Color.BLACK, 1)
                location.isOpaque = true
                content.add(location)
            }
        }

        val playButton = JButton("Run").apply {
            actionCommand = "Run"
            addActionListener(ButtonClickListener())
        }
        val stopButton = JButton("Stop").apply {
            actionCommand = "Stop"
            addActionListener(ButtonClickListener())
        }
        val nextButton = JButton("Next").apply {
            actionCommand = "Next"
            addActionListener(ButtonClickListener())
        }
        val prevButton = JButton("Prev").apply {
            actionCommand = "Prev"
            addActionListener(ButtonClickListener())
        }
        val resetButton = JButton("Reset").apply {
            actionCommand = "Reset"
            addActionListener(ButtonClickListener())
        }
//        val clearButton = JButton("Clear").apply {
//            actionCommand = "Clear"
//            addActionListener(ButtonClickListener())
//        }

        this.add(playButton)
        this.add(stopButton)
        this.add(nextButton)
        this.add(prevButton)
        this.add(resetButton)
//        this.add(clearButton)
        setSize(squares, squares)
        isVisible = true
        defaultCloseOperation = EXIT_ON_CLOSE


    }

    private inner class ButtonClickListener : ActionListener {
        override fun actionPerformed(e: ActionEvent) {
            when (e.actionCommand) {
                "Run" -> play = true
                "Stop" -> play = false
                "Next" -> staticNext()
                "Prev" -> staticPrevious()
                "Reset" -> resetGame()
//                "Clear" -> clearBoard()
            }
        }
    }

    fun loadBoardInfo(boardInfo: List<List<Cell>>) {
        for (y in 0..boardSize) {
            for (x in 0..boardSize) {
                when(boardInfo[y][x].status) {
                    true -> boardLocation[y][x].background = Color.BLACK
                    false -> boardLocation[y][x].background = Color.WHITE
                }
            }
        }
    }
}