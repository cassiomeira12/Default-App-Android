package com.android.app.presenter.login

import android.content.Context
import com.android.app.contract.IUser
import com.android.app.data.model.BaseUser
import com.android.app.data.services.login.FirebaseUserService

class UserPresenter (view: IUser.View) : IUser.Presenter, IUser.Listener {
    var view: IUser.View? = view
    var service: IUser.Service = FirebaseUserService(this)

    override fun currentUser(context: Context) {
        service.currentUser(context)
    }

    override fun signOut(context: Context) {
        service.signOut(context)
    }

    override fun updateOnline() {
        service.updateOnline()
    }

    override fun onChangePassword(email: String, password: String, newPassword: String) {
        service.onChangePassword(email, password, newPassword)
    }

    override fun onChangeEmail(email: String) {
        service.onChangeEmail(email)
    }

    override fun onDestroy() {
        this.view = null
    }

    override fun onFailure(message: String) {
        view?.onFailure(message)
    }

    override fun onResult(user: BaseUser?) {
        view?.onResult(user)
    }
}