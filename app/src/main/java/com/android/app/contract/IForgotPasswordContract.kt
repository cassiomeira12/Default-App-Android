package com.android.app.contract

interface IForgotPasswordContract {

    interface View {
        fun onSuccessResult()
        fun onFailureResult(message: String)
    }

    interface Presenter {
        fun onDestroy()
        fun onSend(email: String)
    }

    interface Service {
        fun onSend(email: String)
    }

    interface Listener {
        fun onSuccess()
        fun onFailure(message: String)
    }

}