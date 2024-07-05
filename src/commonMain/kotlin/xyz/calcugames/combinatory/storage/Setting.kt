package xyz.calcugames.combinatory.storage

import korlibs.io.async.launch
import korlibs.io.serialization.json.Json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

/**
 * Represents the settings for a game.
 */
class Setting<T>(
    /**
     * The ID of the setting to be stored.
     */
    val id: String,
    /**
     * The display name of the setting.
     */
    val name: String = id.split("_").joinToString(" ") { word -> word.replaceFirstChar { it.uppercaseChar() } },
    /**
     * All possible values for the setting.
     */
    val values: List<T>,
    /**
     * Converts a value to a display string.
     */
    val presenter: (T) -> String,
    /**
     * Converts a value to a serialized form.
     */
    val serializer: (T) -> Any,
    /**
     * Converts a serialized form to a value.
     */
    val deserializer: (Any?) -> T?,
    /**
     * The default value for the setting.
     */
    val defaultValue: T = values.first()
) {

    companion object {
        // Settings

        val SHOW_SCOREBOARD = boolean("show_scoreboard")

        // API
        fun boolean(
            id: String,
            name: String = id.split("_").joinToString(" ") { word -> word.replaceFirstChar { it.uppercaseChar() } }
        ) = Setting(
            id,
            name,
            listOf(true, false),
            { if (it) "On" else "Off" },
            { it },
            { it as? Boolean }
        )

        internal val settings: MutableMap<String, Any> = mutableMapOf()

        operator fun <T> get(setting: Setting<T>): T {
            return setting.deserializer(settings[setting.id]) ?: setting.defaultValue
        }

        operator fun <T> set(setting: Setting<T>, value: T) {
            settings[setting.id] = setting.serializer(value)

            launch(Dispatchers.IO) {
                settingsFile.writeString(Json.stringify(settings))
            }
        }
    }

}