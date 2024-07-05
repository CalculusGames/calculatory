@file:Suppress("unchecked_cast")

package xyz.calcugames.combinatory.storage

import korlibs.io.file.VfsFile
import korlibs.io.file.std.standardVfs
import korlibs.io.serialization.json.Json

/**
 * The root directory for the Combinatory game and its storage.
 */
val root: VfsFile = standardVfs.userSharedCacheDir(".calcugames/combinatory")

/**
 * The settings file for the game.
 */
val settingsFile: VfsFile = root["settings.json"]

/**
 * Loads the storage.
 */
suspend fun load() {
    if (!root.exists()) root.mkdir()

    // Settings
    if (!settingsFile.exists())
        settingsFile.writeString("{}")

    val json = Json.parse(settingsFile.readString()) as Map<String, Any>
    Setting.settings.putAll(json)
}