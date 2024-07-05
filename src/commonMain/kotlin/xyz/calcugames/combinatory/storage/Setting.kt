package xyz.calcugames.combinatory.storage

import korlibs.io.async.launch
import korlibs.io.serialization.json.Json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import xyz.calcugames.combinatory.comma

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
    val name: String,
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

    private val serializedDefaultValue = serializer(defaultValue)

    /**
     * Formats the value for display.
     * @param value The value to format.
     * @return The formatted value.
     */
    fun format(value: T): String
        = "$name: ${presenter(value)}"

    init {
        require(values.isNotEmpty()) { "Values must not be empty" }
    }

    operator fun invoke(): T = get(this)

    companion object {
        // Settings

        /**
         * Whether to play music.
         */
        val MUSIC = boolean("music")

        /**
         * Whether to play sound effects.
         */
        val SFX = boolean("sfx")

        /**
         * Whether to show the scoreboard, which represents your current score.
         */
        val SHOW_SCOREBOARD = boolean("show_scoreboard")

        /**
         * The GUI scale for the game.
         */
        val GUI_SCALE = numbers("gui_scale", "GUI Scale", range(0.5, 2.0, 0.25), 1.0)

        /**
         * All settings for the game.
         */
        val allSettings = setOf(
            MUSIC,
            SFX,
            SHOW_SCOREBOARD,
            GUI_SCALE
        )

        // API
        private fun boolean(
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

        private fun numbers(
            id: String,
            name: String = id.split("_").joinToString(" ") { word -> word.replaceFirstChar { it.uppercaseChar() } },
            range: Iterable<Number>,
            default: Number
        ) = Setting(
            id,
            name,
            range.map { it.toDouble() },
            { it.comma },
            { it },
            { it as? Double },
            default.toDouble()
        )

        // Storage

        internal val settings: MutableMap<String, Any> = allSettings
            .associate { setting -> setting.id to setting.serializedDefaultValue }
            .toMutableMap()

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