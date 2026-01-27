package com.ralphmarondev.core.common

import android.content.Context
import android.net.Uri
import android.util.Log
import java.io.File
import java.io.InputStream
import java.util.UUID

object FileManager {

    enum class Directory {
        PROFILE,
        WALLPAPER,
        APPS
    }

    fun save(
        context: Context,
        uri: Uri,
        directory: Directory,
        fileName: String? = null
    ): String {
        val inputStream = context.contentResolver.openInputStream(uri)
            ?: throw IllegalStateException("Unable to open input stream from Uri")

        return saveInternal(
            context = context,
            inputStream = inputStream,
            directory = directory,
            fileName = fileName
        )
    }

    fun saveFromDrawable(
        context: Context,
        drawableResId: Int,
        directory: Directory,
        fileName: String? = null
    ): String {
        val inputStream = context.resources.openRawResource(drawableResId)

        return saveInternal(
            context = context,
            inputStream = inputStream,
            directory = directory,
            fileName = fileName
        )
    }

    fun delete(path: String): Boolean {
        return try {
            val deleted = File(path).delete()
            if (deleted) {
                Log.d("FileManager", "File deleted successfully.")
            } else {
                Log.w("FileManager", "File not found or could not be deleted.")
            }
            deleted
        } catch (e: Exception) {
            Log.e("FileManager", "Failed deleting file. Error: ${e.message}")
            false
        }
    }

    fun generateFileName(
        prefix: String = "img",
        extension: String = "jpg"
    ): String {
        return "${prefix}_${UUID.randomUUID()}.$extension"
    }

    private fun saveInternal(
        context: Context,
        inputStream: InputStream,
        directory: Directory,
        fileName: String?
    ): String {
        val dir = File(
            context.filesDir,
            when (directory) {
                Directory.PROFILE -> "profile"
                Directory.WALLPAPER -> "wallpapers"
                Directory.APPS -> "apps"
            }
        )

        if (!dir.exists()) dir.mkdirs()

        val finalFileName = fileName ?: generateFileName()
        val file = File(dir, finalFileName)

        inputStream.use { input ->
            file.outputStream().use { output ->
                input.copyTo(output)
            }
        }

        return file.absolutePath
    }
}