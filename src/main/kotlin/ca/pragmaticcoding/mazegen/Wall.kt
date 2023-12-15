package ca.pragmaticcoding.mazegen

import javafx.beans.property.BooleanProperty
import javafx.beans.property.SimpleBooleanProperty
import javafx.geometry.Orientation


class Wall(val x: Int, val y: Int, val orientation: Orientation) {
    val blocked: BooleanProperty = SimpleBooleanProperty(true)


    fun block() {
        blocked.value = true
    }

    fun unblock() {
        blocked.value = false
    }
}