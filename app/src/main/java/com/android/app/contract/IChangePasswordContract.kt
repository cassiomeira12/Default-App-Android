package com.android.app.contract

interface IChangePasswordContract {

    interface View {
        fun onSuccessResult()
        fun onFailureResult(message: String)
    }

    interface Listener {
        fun onSuccess()
        fun onFailure(message: String)
    }

    interface Presenter {
        fun onDestroy()
        fun onChangePassword(password: String)
    }

    interface Service {
        fun onChangePassword(password: String)
    }

}