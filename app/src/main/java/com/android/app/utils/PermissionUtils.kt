package com.android.app.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat

object PermissionUtils {
    private val PERMISSION_GRANTED = PackageManager.PERMISSION_GRANTED

    val REQUEST_LOCATION = 1
    val REQUEST_STORAGE = 2

    fun checkPermissionWriteStorage(context: Context): Boolean {
        val permissionWrite = ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val permissionRead = ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
        val permission = permissionWrite.equals(PERMISSION_GRANTED) && permissionRead.equals(PERMISSION_GRANTED)
        return permission
    }

    fun requestPermissionWriteStorage(activity: Activity): Boolean {
        if (checkPermissionWriteStorage(activity)) {
            return true
        } else {
            val arrayPermissions = arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            ActivityCompat.requestPermissions(activity, arrayPermissions, REQUEST_STORAGE)
            return false
        }
    }

}