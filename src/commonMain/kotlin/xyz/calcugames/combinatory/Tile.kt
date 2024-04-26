package xyz.calcugames.combinatory

/**
 * Abstraction for a Tile on a Combinatory Map
 */
interface Tile {

    /**
     * The x coordinate of the tile
     */
    val x: Int

    /**
     * The y coordinate of the tile
     */
    val y: Int

    /**
     * The number value of the tile
     */
    val value: Double

    override fun equals(other: Any?): Boolean

}