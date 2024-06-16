package xyz.calcugames.combinatory

import xyz.calcugames.combinatory.calculatory.combinations

/**
 * Abstraction for a Combinatory Map
 */
interface CombiMap {

    /**
     * The size of the map
     */
    val size: Int

    /**
     * The area of the map
     */
    val area: Int
        get() = size * size

    /**
     * The starting tile
     */
    val start: Tile

    /**
     * The set of tiles on the map
     */
    val tiles: Set<Tile>

    /**
     * A map of operations that can be performed on the map to their initial count
     */
    val operations: Map<Operation, Int>

    // Properties

    /**
     * All potential combinations on this map, sorted in ascending order
     */
    val combinations: List<Double>
        get() = combinations(this).sorted()

    /**
     * The minimum value of the combinations, to reach 1 star
     */
    val min: Double
        get() = combinations[combinations.size / 4]

    /**
     * The goal value of the combinations, to reach 2 stars
     */
    val goal: Double
        get() = combinations[combinations.size / 2]

    /**
     * The maximum value of the combinations, to reach 3 stars
     */
    val max: Double
        get() = combinations.maxOrNull() ?: 0.0

    // Functions

    /**
     * Gets the neighbors for a tile.
     *
     * This list will always have a length of 4, and will be represented in the follwing order:
     *
     * `[up, right, down, left]`
     * @param tile The tile to get the neighbors for
     * @return The set of neighbors for the tile
     */
    fun getNeighbors(tile: Tile): List<Tile?> = getNeighbors(tile.x, tile.y)

    /**
     * Gets the neighbors for a tile.
     *
     * This list will always have a length of 4, and will be represented in the follwing order:
     *
     * `[up, right, down, left]`
     * @param x The x coordinate of the tile
     * @param y The y coordinate of the tile
     * @return The set of neighbors for the tile
     */
    fun getNeighbors(x: Int, y: Int): List<Tile?> = listOf(
        this[x, y - 1],
        this[x + 1, y],
        this[x, y + 1],
        this[x - 1, y]
    )

    /**
     * Gets the tile at the specified coordinates.
     *
     * - Positive X moves right.
     * - Positive Y moves down.
     *
     * @param x The x coordinate
     * @param y The y coordinate
     * @return The tile at the specified coordinates, or null if the coordinates are out of bounds
     */
    operator fun get(x: Int, y: Int): Tile? = tiles.firstOrNull { it.x == x && it.y == y }

    companion object {
        /**
         * The default size of a map
         */
        const val DEFAULT_SIZE = 5

        /**
         * The maximum size of a map
         */
        const val MAX_SIZE = 11
    }
}