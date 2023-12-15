package ca.pragmaticcoding.mazegen

class Cell(val x: Int, val y: Int, val northWall: Wall, val southWall: Wall, val eastWall: Wall, val westWall: Wall) {

    var isVisited = false
    var northCell: Cell? = null
    var southCell: Cell? = null
    var eastCell: Cell? = null
    var westCell: Cell? = null

    fun getAdjacentCells(): List<Cell> = listOfNotNull(northCell, southCell, eastCell, westCell)

    fun unblock(otherCell: Cell) {
        when (otherCell) {
            northCell -> northWall.unblock()
            southCell -> southWall.unblock()
            eastCell -> eastWall.unblock()
            westCell -> westWall.unblock()
        }
    }

    override fun toString(): String = "[$x, $y]"
}