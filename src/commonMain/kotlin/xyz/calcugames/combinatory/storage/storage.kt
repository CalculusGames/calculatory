package xyz.calcugames.combinatory.storage

import korlibs.io.file.VfsFile
import korlibs.io.file.std.standardVfs

/**
 * The root directory for the Combinatory game and its storage.
 */
val root: VfsFile = standardVfs.userSharedCacheDir(".calcugames/combinatory")

/**
 * Loads the storage.
 */
suspend fun load() {
    if (!root.exists()) root.mkdir()
}