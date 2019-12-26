package com.android.app.utils

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.os.Environment
import com.android.app.R
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.lang.Exception

object FileUtils {

    private fun getPath(): String {
        return Environment.getExternalStorageDirectory().absolutePath + "/" + "cassio" + "/"
    }

    fun criarPasta() {
        val file = File(getPath())
    }

    fun saveToInternalStorage(bitmalImage: Bitmap, context: Context): String {
        val cw = ContextWrapper(context)
        val directory = cw.getDir("imageDir", Context.MODE_PRIVATE)
        val myPath = File(directory, "profile.jpg")
        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(myPath)
            bitmalImage.compress(Bitmap.CompressFormat.PNG, 100, fos)
        } catch (ex: Exception) {
            ex.printStackTrace()
        } finally {
            try {
                fos!!.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return directory.absolutePath
    }

}