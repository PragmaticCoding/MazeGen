package ca.pragmaticcoding.mazegen

import javafx.beans.binding.Bindings
import javafx.beans.property.BooleanProperty
import javafx.beans.property.SimpleBooleanProperty
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Orientation
import javafx.scene.control.Button
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.shape.Line
import javafx.scene.shape.Rectangle
import javafx.util.Builder

class MazeViewBuilder(
    private val model: MazeModel,
    private val mazeBuilder: (algorithm: Algorithm, finished: () -> Unit) -> Unit
) :
    Builder<Region> {
    private val hSize = 22.0
    private val vSize = 20.0
    private val mazeRunning: BooleanProperty = SimpleBooleanProperty(false)

    override fun build(): Region = BorderPane().apply {
        right = buttonBox()
        center = StackPane(drawMaze()).apply { padding = Insets(12.0) }
    }

    private fun buttonBox(): Region =
        VBox(10.0, mazeButton("Backtracking", Algorithm.BACKTRACKING), mazeButton("Prim", Algorithm.PRIM))

    private fun mazeButton(name: String, algorithm: Algorithm) = Button(name).apply {
        onAction = EventHandler {
            mazeRunning.value = true
            mazeBuilder.invoke(algorithm) { mazeRunning.value = false }
        }
        disableProperty().bind(mazeRunning)
    }

    private fun drawMaze(): Region = Pane().apply {
        model.walls.forEach { wall ->
            children += when (wall.orientation) {
                Orientation.HORIZONTAL -> Line(
                    wall.x * hSize,
                    wall.y * vSize,
                    (wall.x + 1) * hSize,
                    wall.y * vSize
                )

                Orientation.VERTICAL -> Line(
                    wall.x * hSize,
                    wall.y * vSize,
                    (wall.x) * hSize,
                    (wall.y + 1) * vSize
                )
            }.apply { visibleProperty().bind(wall.blocked) }
        }
        model.cells.forEach { cell ->
            children += Rectangle((cell.x * hSize) + 2.0, (cell.y * vSize) + 2.0, hSize - 4, vSize - 4).apply {
                fillProperty().bind(
                    Bindings.createObjectBinding(
                        { determineColour(model.currentCell.value === cell, model.backtracking.value) },
                        model.currentCell
                    )
                )
            }
        }
        background = Background(BackgroundFill(Color.ANTIQUEWHITE, null, null), null, null)
        maxWidthProperty().bind(model.maxX.add(1).multiply(hSize))
        maxHeightProperty().bind(model.maxY.add(1).multiply(vSize))
    }

    private fun determineColour(isCurrentCell: Boolean, isBacktracking: Boolean): Color = if (isCurrentCell) {
        if (isBacktracking) Color.LIGHTPINK else Color.LIGHTSKYBLUE
    } else Color.ANTIQUEWHITE
}