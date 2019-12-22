package com.android.app.presenter.login

import android.app.Activity
import com.android.app.contract.ICreateAccountContract
import com.android.app.data.model.BaseUser
import com.android.app.data.services.login.FirebaseCreateAccountService

class CreateAccountPresenter (view: ICreateAccountContract.View) : ICreateAccountContract.Presenter, ICreateAccountContract.Listener {
    var view : ICreateAccountContract.View? = view
    var service: ICreateAccountContract.Service = FirebaseCreateAccountService(this)

    override fun register(activity: Activity, user: BaseUser, login: String, password: String) {
        view?.showProgress()
        service.register(activity, user, login, password)
    }

    override fun onDestroy() {
        this.view = null
    }

    override fun onCreatedSuccess(user: BaseUser) {
        view?.hideProgress()
        view?.onCreatedSuccess(user)
    }

    override fun onFailure(message: String) {
        view?.hideProgress()
        view?.onFailure(message)
    }

}