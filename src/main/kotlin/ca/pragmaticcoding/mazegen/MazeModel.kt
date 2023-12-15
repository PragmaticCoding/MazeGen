package ca.pragmaticcoding.mazegen

import javafx.beans.property.*
import javafx.collections.FXCollections
import javafx.collections.ObservableSet
import javafx.geometry.Orientation

class MazeModel {

    val maxX: IntegerProperty = SimpleIntegerProperty(0)
    val maxY: IntegerProperty = SimpleIntegerProperty(0)

    val cells: ObservableSet<Cell> = FXCollections.observableSet()
    val walls: ObservableSet<Wall> = FXCollections.observableSet()
    val currentCell: ObjectProperty<Cell> = SimpleObjectProperty()
    val backtracking: BooleanProperty = SimpleBooleanProperty(false)

    fun getCell(x: Int, y: Int): Cell? = if ((x >= 0) && (x <= maxX.value) && (y >= 0) && (y <= maxY.value)) {
        cells.first { cell -> (cell.x == x) && (cell.y == y) }
    } else null

    fun getWall(x: Int, y: Int, orientation: Orientation): Wall =
        walls.first { wall -> (wall.x == x) && (wall.y == y) && (wall.orientation == orientation) }
}

enum class Algorithm {
    BACKTRACKING, KRUSKAL, PRIM;
}