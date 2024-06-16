package xyz.calcugames.combinatory.calculatory

import korlibs.io.serialization.json.Json

/**
 * Serializes a [Path] to a JSON string.
 * @return JSON string
 */
fun Path.toJson(): String
    = Json.stringify(this)