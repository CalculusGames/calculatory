package xyz.calcugames.combinatory.generator

import kotlin.random.Random

/**
 * Generates a LevelZ seed.
 * @return Seed
 */
fun nextSeed(): Array<Long> {
    return arrayOf(
        Random.nextLong(),
        Random.nextLong(),
        Random.nextLong()
    )
}