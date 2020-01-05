package com.android.app.utils

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat

object PermissionUtils {

    val REQUEST_LOCATION = 1
    val REQUEST_STORAGE = 2

    private fun requestPermissionWriteStorage(activity: Activity): Boolean {
        val permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        return permission == PackageManager.PERMISSION_GRANTED
    }

    fun checkPermissionWriteStorage(activity: Activity): Boolean {
        if (requestPermissionWriteStorage(activity)) {
            return true
        } else {
            val arrayPermissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            ActivityCompat.requestPermissions(activity, arrayPermissions, REQUEST_STORAGE)
            return false
        }
    }

}