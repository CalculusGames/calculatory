package xyz.calcugames.combinatory

/**
 * Abstraction for a Combinatory Map
 */
interface CombiMap {

    /**
     * The index of the map in the game.
     */
    val index: Int

    /**
     * The category specifier for the map.
     */
    val category: String

    /**
     * The internal identifier for this map.
     */
    val id: String
        get() = "$category-$index"

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

    /**
     * The minimum number of stars required to complete the map. If the level is generated, it will return `0`.
     */
    val minimumStars: Int

    /**
     * Whether this map is automatically generated or not.
     */
    val isGenerated: Boolean
        get() = index == -1 || category.contains("endless")

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

    /**
     * Converts the map to a 2D array.
     * @return The 2D array representation of the map
     */
    fun to2DArray(): Array<DoubleArray>
        = Array(size) { x -> DoubleArray(size) { y -> tiles.find { it.x == x && it.y == y }?.value ?: 0.0 } }

    companion object {
        /**
         * The default size of a map
         */
        const val DEFAULT_SIZE = 5

        /**
         * The maximum size of a map
         */
        const val MAX_SIZE = 13

        /**
         * All possible sizes for a map
         */
        val ALL_SIZES = (DEFAULT_SIZE..MAX_SIZE).filter { it % 2 != 0 }.sorted()
    }
}