@file:Suppress("unchecked_cast")

package xyz.calcugames.combinatory.storage

import korlibs.io.async.launch
import korlibs.io.async.use
import korlibs.io.file.VfsFile
import korlibs.io.file.VfsOpenMode
import korlibs.io.file.std.userHomeVfs
import korlibs.io.serialization.json.Json
import korlibs.io.stream.AsyncOutputStream
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

/**
 * The root directory for the Combinatory game and its storage.
 */
val root: VfsFile = userHomeVfs[".calcugames/combinatory"]

/**
 * Loads the storage.
 */
suspend fun load() {
    if (!root.exists()) root.mkdirs()

    listOf<suspend () -> Unit>(
        { loadSettings() }
    ).forEach {
        launch(Dispatchers.IO) { it() }
    }

}

/**
 * The settings file for the game.
 */
val settingsFile: VfsFile = root["settings.json"]

/**
 * Loads the settings for the game.
 */
suspend fun loadSettings() {
    if (!settingsFile.exists())
        settingsFile.writeString("{}")

    val json = Json.parse(settingsFile.readString()) as Map<String, Any>
    Setting.settings.putAll(json)
}

/**
 * The user data file.
 */
val userData: VfsFile = root["user.dat"]

/**
 * Writes the user data to the specified path. The stream is opened in [VfsOpenMode.CREATE] and will be closed after the callback is executed.
 * @param callback The callback to write the user data.
 */
suspend fun writeUserData(callback: (AsyncOutputStream) -> Unit) {
    userData.open(VfsOpenMode.CREATE).use(callback)
}

/**
 * The level data folder.
 */
val levelData: VfsFile = root["levels"]

/**
 * Writes the level data to the specified path. The stream is opened in [VfsOpenMode.CREATE] and will be closed after the callback is executed.
 * @param path The path to write the level data to.
 * @param callback The callback to write the level data.
 */
suspend fun writeLevelData(path: String, callback: (AsyncOutputStream) -> Unit) {
    val file = levelData[path]
    file.open(VfsOpenMode.CREATE).use(callback)
}