package ca.pragmaticcoding.mazegen

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage

class MazeApplication : Application() {
    override fun start(stage: Stage) {
        val scene = Scene(MazeController().view, 990.0, 900.0)
        stage.title = "Perfect Maze"
        stage.scene = scene
        stage.show()
    }
}

fun main() {
    Application.launch(MazeApplication::class.java)
}