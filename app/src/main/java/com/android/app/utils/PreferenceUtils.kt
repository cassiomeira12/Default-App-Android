package com.android.app.utils

import android.content.Context
import android.content.SharedPreferences
import com.android.app.R

class PreferenceUtils (private var context: Context?) {
    private val PREF_NAME = context!!.getString(R.string.app_name)

    private val AUTH_TOKEN = "auth-token"
    private val USER_NAME = "user_name"
    private val USER_EMAIL = "user_email"
    private val PUSH_NOTIFICATION = "push_notification"

    private fun getPreference(): SharedPreferences {
        return context!!.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun getToken(): String? {
        return getPreference().getString(AUTH_TOKEN, null)
    }

    fun setToken(value: String) {
        getPreference().edit().putString(AUTH_TOKEN, value).apply()
    }

    fun getUserName(): String? {
        return getPreference().getString(USER_NAME, null)
    }

    fun setUserName(value: String?) {
        getPreference().edit().putString(USER_NAME, value).apply()
    }

    fun getUserEmail(): String? {
        return getPreference().getString(USER_EMAIL, null)
    }

    fun setUserEmail(value: String?) {
        getPreference().edit().putString(USER_EMAIL, value).apply()
    }

    fun getPushNotification(): Boolean {
        return getPreference().getBoolean(PUSH_NOTIFICATION, true)
    }

    fun setPushNotification(value: Boolean) {
        getPreference().edit().putBoolean(PUSH_NOTIFICATION, value).apply()
    }

}