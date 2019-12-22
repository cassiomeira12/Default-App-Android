package com.android.app.presenter.login

import com.android.app.contract.IForgotPasswordContract
import com.android.app.data.services.login.FirebaseForgotPasswordService

class ForgotPasswordPresenter (view: IForgotPasswordContract.View) : IForgotPasswordContract.Presenter, IForgotPasswordContract.Listener {
    var view: IForgotPasswordContract.View? = view
    var service: IForgotPasswordContract.Service = FirebaseForgotPasswordService(this)

    override fun onSend(email: String) {
        service.onSend(email)
    }

    override fun onDestroy() {
        this.view = null
    }

    override fun onSuccess() {
        view?.onSuccessResult()
    }

    override fun onFailure(message: String) {
        view?.onFailureResult(message)
    }

}