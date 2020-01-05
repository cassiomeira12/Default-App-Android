package com.android.app.utils

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.util.Log
import com.android.app.R
import java.io.*


class FileUtils(val context: Context) {

    fun getPath(): String {
        return Environment.getExternalStorageDirectory().absolutePath + "/${context.getString(R.string.app_name)}/"
    }

    fun createDirectory() {
        val file = File(getPath())
        if (!file.exists()) {
            file.mkdir()
        }
    }

//    fun isExternalStorageWritable(): Boolean {
//        val state = Environment.getExternalStorageState()
//        if (Environment.MEDIA_MOUNTED.equals(state)) {
//            return true
//        }
//        return false
//    }
//
//    private fun saveArrayToInternalStorage(fileName: String, bytes: ByteArray): Boolean {
//        try {
//            val out = context.openFileOutput(fileName, Context.MODE_PRIVATE)
//            out.write(bytes)
//            out.close()
//            return true
//        } catch (ex: Exception) {
//            ex.printStackTrace()
//            return false
//        }
//    }
//
//    private fun saveArrayToSDCard(fileName: String, bytes: ByteArray): Boolean {
//        try {
//            val path = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
//            val file = File(path, fileName)
//            val out = FileOutputStream(file)
//            out.write(bytes)
//            out.close()
//            return true
//        } catch (ex: IOException) {
//            ex.printStackTrace()
//            return false
//        }
//    }
//
//    fun save() {
//        if(isExternalStorageWritable()) {
//            saveArrayToSDCard("nomeDaImagem", ByteArray(0));
//        } else {
//            saveArrayToInternalStorage("nomeDaImagem", ByteArray(0));
//        }
//    }

    fun lerArquivo(fileName: String) {
        var conteudo = ""
        try {
            val inp = context.openFileInput(fileName)
            val reader = BufferedReader(InputStreamReader(inp))
            conteudo = reader.readLine()
            reader.close()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    private fun writeFileInStorage(fileName: String, bytes: ByteArray): Boolean {
        try {
            val path = getPath()
            val file = File(path, fileName)
            val out = FileOutputStream(file)
            out.write(bytes)
            out.close()
            return true
        } catch (ex: IOException) {
            ex.printStackTrace()
            return false
        }
    }

    private fun readFileInStorage(fileName: String): File? {
        try {
            val path = getPath()
            val file = File(path, fileName)
            return file
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
    }

    fun writeImage(fileName: String, img: Bitmap): Boolean {
        val stream = ByteArrayOutputStream()
        img.compress(Bitmap.CompressFormat.PNG, 50, stream)
        val bytes = stream.toByteArray()
        return this.writeFileInStorage(fileName.plus(".png"), bytes)
    }

    fun readImage(fileName: String): Bitmap? {
        try {
            val file = readFileInStorage(fileName.plus(".png"))
            if (file == null) {
                return null
            }
            val bitmap = BitmapFactory.decodeStream(FileInputStream(file))
            return bitmap
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
    }

//    fun readImage(fileName: String, content: ContentResolver): Bitmap? {
//        try {
//            val path = getPath()
//            val parcelFile = content.openFileDescriptor(Uri.parse(path.plus(fileName)), "r")
//            val fileDescriptor = parcelFile!!.getFileDescriptor()
//            val image = BitmapFactory.decodeFileDescriptor(fileDescriptor)
//            parcelFile.close()
//            return image
//        } catch(ex: Exception) {
//            ex.printStackTrace()
//            return null
//        }
//    }

}