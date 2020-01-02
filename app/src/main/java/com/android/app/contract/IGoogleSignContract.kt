package com.android.app.contract

import android.app.Activity
import android.content.Intent
import com.android.app.data.model.BaseUser

interface IGoogleSignContract {

    interface View {
        fun showProgress()
        fun hideProgress()
        fun onFailure(message: String)

        fun onSuccess(user: BaseUser)
    }

    interface Listener {
        fun onFailure(message: String)
        fun onSuccess(user: BaseUser)
    }

    interface Presenter {
        fun onDestroy()

        fun createGoogleClient(activity: Activity)
        fun onSignIn()
        fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    }

    interface Service {
        fun createGoogleClient(activity: Activity)
        fun signIn()
        fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    }

}