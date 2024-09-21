package xyz.calcugames.combinatory

/**
 * Abstraction for a Tile on a Combinatory Map
 */
interface Tile {

    /**
     * The x coordinate of the tile
     */
    var x: Int

    /**
     * The y coordinate of the tile
     */
    var y: Int

    /**
     * The number value of the tile
     */
    var value: Double

    // Util

    operator fun component1(): Int = x
    operator fun component2(): Int = y
    operator fun component3(): Double = value

    override fun equals(other: Any?): Boolean

    /**
     * Converts the tile to a double array. [value] is stored first, followed by [x] and [y].
     * @return The double array representation of the tile.
     */
    fun toDoubleArray(): DoubleArray = doubleArrayOf(value, x.toDouble(), y.toDouble())

    /**
     * Copies the tile with the given parameters.
     * @param x The x coordinate of the new tile.
     * @param y The y coordinate of the new tile.
     * @param value The value of the new tile.
     * @return The new tile.
     */
    fun copy(x: Int = this.x, y: Int = this.y, value: Double = this.value): Tile

    /**
     * Gets the neighbors for a tile.
     * @param grid The grid to get the neighbors from.
     * @return The set of neighbors for the tile.
     */
    fun getNeighbors(grid: List<Tile>) = grid
        .filter { it.x in x - 1..x + 1 && it.y in y - 1..y + 1 && it != this }
        .filter { it.value > 0.0 }
}