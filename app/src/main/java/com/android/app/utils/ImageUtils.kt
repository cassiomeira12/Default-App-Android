package com.android.app.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.AsyncTask
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.android.app.R
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import java.io.ByteArrayOutputStream
import java.io.FileInputStream
import java.lang.RuntimeException

class ImageUtils(var context: Context) {
    private val TAG = javaClass.simpleName

    companion object {
        val PNG = ".png"
    }

    fun picassoImageUser(id: String, imgView: ImageView, url: String?) {
        if ((url == null || url.isEmpty()) || id.isEmpty()) {
            Log.w(TAG, "Error: ID[$id], URL[$url]")
            imgView.setImageResource(R.drawable.user_default_img_white)
            return
        }
        findImageInternalStorage(id, imgView, url, null)
    }

    fun picassoImageUser(id: String, imgView: ImageView, url: String?, progressBar: ProgressBar) {
        if ((url == null || url.isEmpty()) || id.isEmpty()) {
            Log.w(TAG, "Error: ID[$id], URL[$url]")
            imgView.setImageResource(R.drawable.user_default_img_white)
            progressBar.visibility = View.INVISIBLE
            return
        }
        progressBar.visibility = View.VISIBLE
        findImageInternalStorage(id, imgView, url, progressBar)
    }

    private fun findImageInternalStorage(id: String, imgView: ImageView, url: String, progressBar: ProgressBar?) {
        Log.d(TAG, "findImageInternalStorage")
        Log.d(TAG, "fileName: $id")
        object : AsyncTask<Void, Void, Bitmap>() {
            override fun doInBackground(vararg params: Void?): Bitmap? {
                return readImageInternalStorage(id)
            }

            override fun onPostExecute(result: Bitmap?) {
                if (result != null) {
                    imgView.setImageBitmap(result)
                    if (progressBar != null) {
                        progressBar.visibility = View.INVISIBLE
                    }
                } else {
                    downloadImageWithPicasso(id, imgView, url, progressBar)
                }
                super.onPostExecute(result)
            }
        }.execute()
    }

    private fun downloadImageWithPicasso(id: String, imgView: ImageView, url: String, progressBar: ProgressBar?) {
        Log.d(TAG, "downloadImageWithPicasso")
        Log.d(TAG, "URL: $url")
        Picasso.with(context)
            .load(url)
            .error(R.drawable.user_default_img_white_48dp)
            .into(object : Target {
                override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom) {
                    Log.d(TAG, "Download image successful")
                    imgView.setImageBitmap(bitmap)
                    if (progressBar != null) {
                        progressBar.visibility = View.INVISIBLE
                    }
                    writeImageInternalStorage(id, bitmap)
                }
                override fun onPrepareLoad(placeHolderDrawable: Drawable?) { }
                override fun onBitmapFailed(errorDrawable: Drawable?) {
                    Log.w(TAG, "Download image failed")
                    if (progressBar != null) {
                        progressBar.visibility = View.INVISIBLE
                    }
                }
            })
    }

    fun writeImageInternalStorage(fileName: String, img: Bitmap): Boolean {
        if (!PermissionUtils.checkPermissionWriteStorage(context)) {
            Log.e(TAG, "YOUT DONT HAVE PERMISSIONS TO WRITE IN INTERNAL STORAGE")
            return false
        }
        Log.d(TAG, "writeImageInternalStorage")
        val fileNameWithExtension: String?
        if (fileName.endsWith(PNG)) {
            fileNameWithExtension = fileName
        } else {
            fileNameWithExtension = fileName.plus(PNG)
        }
        val file = FileUtils(context).readFileInStorage(fileNameWithExtension)
        val stream = ByteArrayOutputStream()
        img.compress(Bitmap.CompressFormat.PNG, 100, stream)
        val bytes = stream.toByteArray()
        return FileUtils(context).writeFileInStorage(fileNameWithExtension, bytes)
    }

    fun readImageInternalStorage(fileName: String): Bitmap? {
        if (!PermissionUtils.checkPermissionWriteStorage(context)) {
            Log.e(TAG, "YOUT DONT HAVE PERMISSIONS TO READ IN INTERNAL STORAGE")
            return null
        }
        Log.d(TAG, "readImageInternalStorage")
        val fileNameWithExtension: String?
        if (fileName.endsWith(PNG)) {
            fileNameWithExtension = fileName
        } else {
            fileNameWithExtension = fileName.plus(PNG)
        }
        val file = FileUtils(context).readFileInStorage(fileNameWithExtension)
        if (file == null || !file.exists()) {
            Log.w(TAG, "Image not find in storage")
            return null
        }
        try {
            val bitmap = BitmapFactory.decodeStream(FileInputStream(file))
            Log.d(TAG, "Image find in storage")
            return bitmap
        } catch (ex: RuntimeException) {
            Log.e(TAG, ex.toString())
            return null
        }
    }

}