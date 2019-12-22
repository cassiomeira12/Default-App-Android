package com.android.app.data

import com.android.app.data.model.BaseUser

class UserSingleton {
    companion object {
        val instance = BaseUser()
    }
}