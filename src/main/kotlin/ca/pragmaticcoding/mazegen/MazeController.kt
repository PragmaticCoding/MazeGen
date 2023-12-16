package ca.pragmaticcoding.mazegen

import javafx.concurrent.Task
import javafx.event.EventHandler
import javafx.scene.layout.Region

class MazeController {

    private val model = MazeModel()
    private val interactor = MazeInteractor(model)
    val view: Region

    init {
        interactor.initializeWallsAndCells(34, 40)
        view = MazeViewBuilder(model, this::buildMaze).build()
    }

    private fun buildMaze(algorithm: Algorithm, finished: () -> Unit) {
        Thread(object : Task<Void>() {
            override fun call(): Void? {
                when (algorithm) {
                    Algorithm.BACKTRACKING -> interactor.startRecursiveBacktrackingAlgorithm()
                    Algorithm.KRUSKAL -> TODO()
                    Algorithm.PRIM -> interactor.primsAlgorithm()
                }
                return null
            }
        }.apply { onSucceeded = EventHandler { finished.invoke() } }).apply { isDaemon = true }.start()
    }
}