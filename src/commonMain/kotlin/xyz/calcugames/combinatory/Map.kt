package xyz.calcugames.combinatory

/**
 * Abstraction for a Combinatory Map
 */
interface Map {

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
     * The set of tiles on the map
     */
    val tiles: Set<Tile>

    companion object {
        /**
         * The default size of a map
         */
        const val DEFAULT_SIZE = 5
    }
}