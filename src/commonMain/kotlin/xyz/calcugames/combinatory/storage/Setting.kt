package xyz.calcugames.combinatory.storage

import korlibs.io.async.launch
import korlibs.io.serialization.json.Json
import kotlinx.coroutines.Dispatchers
import xyz.calcugames.combinatory.comma
import kotlin.reflect.KClass

/**
 * Represents the settings for a game.
 */
class Setting<T : Any>(
    /**
     * The ID of the setting to be stored.
     */
    val id: String,
    /**
     * The display name of the setting.
     */
    val name: String,
    /**
     * The type of the setting.
     */
    val type: KClass<T>,
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
         * Whether to play sound effects.
         */
        val SFX = boolean("sfx", "SFX")

        /**
         * Whether to show the scoreboard, which represents your current score.
         */
        val SHOW_SCOREBOARD = boolean("show_scoreboard")

        /**
         * Whether to display animations.
         */
        val ANIMATIONS = boolean("animations")

        /**
         * All settings for the game.
         */
        val allSettings = setOf(
            SFX,
            SHOW_SCOREBOARD,
            ANIMATIONS
        )

        // API
        private fun boolean(
            id: String,
            name: String = id.split("_").joinToString(" ") { word -> word.replaceFirstChar { it.uppercaseChar() } }
        ) = Setting(
            id,
            name,
            Boolean::class,
            listOf(true, false),
            { if (it) "On" else "Off" },
            { it },
            { it as? Boolean }
        )

        private inline fun <reified T : Number> numbers(
            id: String,
            name: String = id.split("_").joinToString(" ") { word -> word.replaceFirstChar { it.uppercaseChar() } },
            range: Iterable<T>,
            default: T
        ) = Setting(
            id,
            name,
            T::class,
            range.map { it },
            { it.comma },
            { it },
            { it as? T },
            default
        )

        // Storage

        internal val settings: MutableMap<String, Any> = allSettings
            .associate { setting -> setting.id to setting.serializedDefaultValue }
            .toMutableMap()

        /**
         * Loads the settings from the specified map.
         * @param settings The settings to load.
         */
        fun load(settings: Map<String, Any>) {
            this.settings.putAll(settings)
        }

        operator fun <T : Any> get(setting: Setting<T>): T {
            return setting.deserializer(settings[setting.id]) ?: setting.defaultValue
        }

        operator fun <T : Any> set(setting: Setting<T>, value: T) {
            settings[setting.id] = setting.serializer(value)
        }
    }

}