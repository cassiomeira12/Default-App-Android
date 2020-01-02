package com.android.app.utils

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.android.app.R
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class ImageUtils(var context: Context) {
    private val TAG = javaClass.simpleName

    fun picassoImageUser(imgView: ImageView, url: String?) {
        if (url == null || url.isEmpty()) {
            imgView.setImageResource(R.drawable.user_default_img_white_48dp)
            return
        }
        Picasso.with(context)
            .load(url)
            .error(R.drawable.user_default_img_white_48dp)
            .into(imgView)
    }

    fun picassoImageUser(imgView: ImageView, url: String?, progressBar: ProgressBar?) {
        if (url == null || url.isEmpty()) {
            imgView.setImageResource(R.drawable.user_default_img_white_48dp)
            progressBar!!.visibility = View.INVISIBLE
            return
        }

        progressBar!!.visibility = View.VISIBLE

        Picasso.with(context)
            .load(url)
            .error(R.drawable.user_default_img_white_48dp)
            .into(imgView, object : Callback {
                override fun onSuccess() {
                    Log.d(TAG, "onSuccess")
                    progressBar!!.visibility = View.INVISIBLE
                }

                override fun onError() {
                    Log.d(TAG, "onError")
                    progressBar!!.visibility = View.INVISIBLE
                }
            })
    }

}