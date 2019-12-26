package com.android.app.utils

import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import java.io.File
import java.io.FileOutputStream

class ImageUtils(var context: Context) {
    var directoryName = "images"
    var fileName = "image.png"
    var external = true


    private fun getAlbumStorageDir(albumName: String): File {
        return File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), albumName)
    }

    private fun createFile() {
        val directory: File? = null
        if (external) {

        }
    }

    fun save(bitmapImage: Bitmap) {
        val fileOutputStream: FileOutputStream? = null
    }



}