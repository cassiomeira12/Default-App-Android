package com.android.app.contract

import android.app.Activity
import com.android.app.data.model.BaseUser

interface ILoginContract {

    interface View {
        fun showProgress()
        fun hideProgress()
        fun onFailure(message: String)

        fun onSuccess(user: BaseUser)
        fun setLoginNameError(message: String)
        fun setPasswordNameError(message: String)
    }

    interface Listener {
        fun onFailure(message : String)
        fun onSuccess(user: BaseUser)
    }

    interface Presenter {
        fun onDestroy()
        fun onLogin(activity: Activity, email : String, password : String)
    }

    interface Service {
        fun onLogin(activity : Activity, login : String, password: String)
    }

}