package com.android.app.contract

import android.app.Activity
import android.content.Intent
import com.android.app.data.model.BaseUser

interface IGoogleSignContract {

    interface View {
        fun showProgress()
        fun hideProgress()
        fun onSuccess(user: BaseUser)
        fun onFailure(message: String)
    }

    interface Presenter {
        fun createGoogleClient(activity: Activity)
        fun onSignIn()
        fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
        fun onDestroy()
    }

    interface Service {
        fun createGoogleClient(activity: Activity)
        fun signIn()
        fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    }

    interface Listener {
        fun onSuccess(user: BaseUser)
        fun onFailure(message: String)
    }

}