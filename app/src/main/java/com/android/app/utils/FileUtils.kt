package com.android.app.utils

import android.content.Context
import android.os.Environment
import android.util.Log
import android.widget.Toast
import com.android.app.R
import java.io.*
import java.lang.Exception

class FileUtils(val context: Context) {
    private val TAG = javaClass.simpleName

    fun getPathRoot(): String {
        val pathRoot = Environment.getExternalStorageDirectory().absolutePath + "/${context.getString(R.string.app_name)}/"
        val file = File(pathRoot)
        if (!file.exists()) {
            file.mkdir()
        }
        return pathRoot
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

//    private fun lerArquivo() {
//        val file = "arquivo.txt"
//        var conteudo = ""
//        try {
//            val inp = openFileInput(file)
//            val reader = BufferedReader(InputStreamReader(inp))
//            conteudo = reader.readLine()
//            reader.close()
//            Toast.makeText(this, conteudo, Toast.LENGTH_LONG).show()
//        } catch (ex: Exception) {
//            Toast.makeText(this, "erro leitura", Toast.LENGTH_LONG).show()
//            ex.printStackTrace()
//        }
//    }
//
//    private fun gravarArquivo() {
//        val file = "arquivo.txt"
//        val conteudo = "conteudo do arquivo"
//        try {
//            val out = openFileOutput(file, Context.MODE_PRIVATE)
//            val writter = BufferedWriter(OutputStreamWriter(out))
//            writter.write(conteudo)
//            writter.close()
//            Toast.makeText(this, "gravou", Toast.LENGTH_LONG).show()
//        } catch (ex: Exception) {
//            Toast.makeText(this, "erro", Toast.LENGTH_LONG).show()
//            ex.printStackTrace()
//        }
//    }

    fun writeFileInStorage(fileName: String, bytes: ByteArray): Boolean {
        Log.d(TAG, "fileName: $fileName")
        try {
            val path = getPathRoot()
            val file = File(path, fileName)
            Log.d(TAG, "path: ${file.absolutePath}")
            val out = FileOutputStream(file)
            out.write(bytes)
            out.close()
            Log.d(TAG, "write file success")
            return true
        } catch (ex: IOException) {
            Log.e(TAG, "write file error")
            ex.printStackTrace()
            return false
        }
    }

    fun readFileInStorage(fileName: String): File? {
        Log.d(TAG, "fileName: $fileName")
        try {
            val path = getPathRoot()
            val file = File(path, fileName)
            Log.d(TAG, "path: ${file.absolutePath}")
            Log.d(TAG, "read file success")
            Log.d(TAG, "file exists ${file.exists()}")
            return file
        } catch (ex: IOException) {
            Log.e(TAG, "read file error")
            ex.printStackTrace()
            return null
        }
    }

//    fun writeImage(fileName: String, img: Bitmap): Boolean {
//        val stream = ByteArrayOutputStream()
//        img.compress(Bitmap.CompressFormat.PNG, 50, stream)
//        val bytes = stream.toByteArray()
//        return this.writeFileInStorage(fileName.plus(".png"), bytes)
//    }
//
//    fun readImage(fileName: String): Bitmap? {
//        try {
//            val file = readFileInStorage(fileName.plus(".png"))
//            if (file == null) {
//                return null
//            }
//            val bitmap = BitmapFactory.decodeStream(FileInputStream(file))
//            return bitmap
//        } catch (ex: IOException) {
//            ex.printStackTrace()
//            return null
//        }
//    }

}