package ca.pragmaticcoding.mazegen

import javafx.application.Platform
import javafx.geometry.Orientation

class MazeInteractor(val model: MazeModel) {

    fun initializeWallsAndCells(maxX: Int, maxY: Int) {
        model.maxX.value = maxX
        model.maxY.value = maxY
        println("Building walls")
        buildElements(maxX + 1, maxY + 1) { x, y ->
            if (x <= maxX) model.walls.add(Wall(x, y, Orientation.HORIZONTAL))
            if (y <= maxY) model.walls.add(Wall(x, y, Orientation.VERTICAL))
        }
        println("Building Cells")
        buildElements { x, y ->
            model.cells.add(
                Cell(
                    x,
                    y,
                    model.getWall(x, y, Orientation.HORIZONTAL),
                    model.getWall(x, y + 1, Orientation.HORIZONTAL),
                    model.getWall(x + 1, y, Orientation.VERTICAL),
                    model.getWall(x, y, Orientation.VERTICAL)
                )
            )
        }
        println("How many cells: ${model.cells.size}")
        println("found cell ${model.getCell(15, 15)}")
        buildElements { x, y ->
            model.getCell(x, y)?.apply {
                northCell = model.getCell(x, y - 1)
                southCell = model.getCell(x, y + 1)
                westCell = model.getCell(x - 1, y)
                eastCell = model.getCell(x + 1, y)
            }
        }
        model.currentCell.value = model.getCell(maxX, maxY)
    }

    private fun buildElements(
        maxX: Int = model.maxX.value,
        maxY: Int = model.maxY.value,
        function: (x: Int, y: Int) -> Unit
    ) {
        for (x in 0..maxX) {
            for (y in 0..maxY) {
                function.invoke(x, y)
            }
        }
    }

    fun startRecursiveBacktrackingAlgorithm() {
        resetGrid()
        model.getCell(model.maxX.value, model.maxY.value)?.let {
            Platform.runLater { it.southWall.unblock() }
            recursiveBacktracking(it)
        }
    }

    private fun resetGrid() {
        Platform.runLater {
            model.cells.forEach { cell -> cell.isVisited = false }
            model.walls.forEach { wall -> wall.block() }
            model.backtracking.value = false
        }
    }

    private fun recursiveBacktracking(currentCell: Cell) {
        Thread.sleep(40)
        Platform.runLater {
            model.currentCell.value = currentCell
            model.backtracking.value = false
        }
        currentCell.isVisited = true
        while (currentCell.getAdjacentCells().any { cell -> !cell.isVisited }) {
            with(currentCell.getUnvisitedAdjacentCells().shuffled()[0]) {
                Platform.runLater { currentCell.unblock(this) }
                recursiveBacktracking(this)
            }
        }
        Platform.runLater {
            model.currentCell.value = currentCell
            model.backtracking.value = true
        }
        Thread.sleep(70)
    }

    fun primsAlgorithm() {
        resetGrid()
        model.getCell(model.maxX.value, model.maxY.value)?.let {
            val frontierCells: MutableSet<Cell> = mutableSetOf(it)
            it.isVisited = true
            while (frontierCells.isNotEmpty()) {
                val currentCell = frontierCells.shuffled()[0]
                if (currentCell.getUnvisitedAdjacentCells().isNotEmpty()) {
                    Thread.sleep(30)
                    Platform.runLater {
                        model.currentCell.value = currentCell
                    }
                    with(currentCell.getUnvisitedAdjacentCells().shuffled()[0]) {
                        Platform.runLater { currentCell.unblock(this) }
                        isVisited = true
                        if (this.getUnvisitedAdjacentCells().isNotEmpty()) {
                            frontierCells += this
                        }
                    }
                }
                if (currentCell.getUnvisitedAdjacentCells().isEmpty()) {
                    frontierCells -= currentCell
                }
            }
        }
    }
}