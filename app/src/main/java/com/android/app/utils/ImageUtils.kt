package com.android.app.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.AsyncTask
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.android.app.R
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target


class ImageUtils(var context: Context) {
    private val TAG = javaClass.simpleName

    fun picassoImageUser(id: String, imgView: ImageView, url: String?) {
        if ((url == null || url.isEmpty()) || id.isEmpty()) {
            imgView.setImageResource(R.drawable.user_default_img_white_48dp)
            return
        }
        procurarFotoNaMemoriaInterna(id, imgView, url!!, null)
    }

    fun picassoImageUser(id: String, imgView: ImageView, url: String?, progressBar: ProgressBar) {
        if ((url == null || url.isEmpty()) || id.isEmpty()) {
            imgView.setImageResource(R.drawable.user_default_img_white_48dp)
            progressBar.visibility = View.INVISIBLE
            return
        }
        progressBar.visibility = View.VISIBLE
        procurarFotoNaMemoriaInterna(id, imgView, url!!, progressBar)
    }

    private fun procurarFotoNaMemoriaInterna(id: String, imgView: ImageView, url: String, progressBar: ProgressBar?) {
        object : AsyncTask<Void, Void, Bitmap>() {
            override fun doInBackground(vararg params: Void?): Bitmap? {
                return FileUtils(context).readImage(id)
            }

            override fun onPostExecute(result: Bitmap?) {
                if (result != null) {
                    imgView.setImageBitmap(result)
                    if (progressBar != null) {
                        progressBar.visibility = View.INVISIBLE
                    }
                } else {
                    downloadImagePicasso(id, imgView, url, progressBar)
                }
                super.onPostExecute(result)
            }
        }.execute()
    }

    private fun downloadImagePicasso(id: String, imgView: ImageView, url: String, progressBar: ProgressBar?) {
        Picasso.with(context)
            .load(url)
            .error(R.drawable.user_default_img_white_48dp)
            .into(object : Target {
                override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom) {
                    imgView.setImageBitmap(bitmap)
                    if (progressBar != null) {
                        progressBar.visibility = View.INVISIBLE
                    }
                    FileUtils(context).writeImage(id, bitmap)
                }
                override fun onPrepareLoad(placeHolderDrawable: Drawable?) { }
                override fun onBitmapFailed(errorDrawable: Drawable?) {
                    if (progressBar != null) {
                        progressBar.visibility = View.INVISIBLE
                    }
                }
            })
    }

}