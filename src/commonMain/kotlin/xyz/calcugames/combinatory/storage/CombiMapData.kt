package xyz.calcugames.combinatory.storage

import korlibs.io.async.launch
import korlibs.io.file.VfsFile
import korlibs.io.serialization.json.Json
import korlibs.io.serialization.json.toJson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import xyz.calcugames.combinatory.CombiMap

/**
 * The data of a [CombiMap].
 */
@Suppress("unchecked_cast")
class CombiMapData(
    /**
     * The map this data is for.
     */
    val map: CombiMap,
    /**
     * The file where the data is stored.
     */
    val file: VfsFile
) {

    init {
        launch(Dispatchers.IO) {
            if (!file.exists()) file.writeString("{}")
            load()
        }
    }

    private val data: MutableMap<String, Any> = mutableMapOf()

    /**
     * The highest score for this map.
     */
    val best: Double
        get() = data["best"] as Double? ?: 0.0

    /**
     * Whether this map has been completed.
     */
    val completed: Boolean
        get() = data["completed"] as Boolean? ?: false

    /**
     * Loads the data from the file.
     */
    suspend fun load() {
        val json = file.readString()
        data.putAll(Json.parse(json) as Map<String, Any>)
    }

    /**
     * Saves the data to the file.
     */
    suspend fun save() {
        val json = data.toJson()
        file.writeString(json)
    }

    /**
     * The data for this [CombiMap].
     */
    val CombiMap.data: CombiMapData
        get() = CombiMapData(this, root["maps/$id.json"])
}