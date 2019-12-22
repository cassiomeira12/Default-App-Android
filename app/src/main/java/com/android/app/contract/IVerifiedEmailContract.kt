package com.android.app.contract

import com.android.app.data.model.BaseUser

interface IVerifiedEmailContract {

    interface View {
        fun showProgress()
        fun hideProgress()
        fun onSuccess(message: String)
        fun onFailure(message : String)
    }

    interface Presenter {
        fun onDestroy()
        fun sendEmailVerification()
        fun isEmailVerified(user: BaseUser): Boolean
    }

    interface Service {
        fun sendEmailVerification()
        fun isEmailVerified(user: BaseUser): Boolean
    }

    interface Listener {
        fun onSuccess(message: String)
        fun onFailure(message: String)
    }

}
