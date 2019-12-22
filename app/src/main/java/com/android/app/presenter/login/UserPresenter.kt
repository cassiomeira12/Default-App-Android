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

    override fun onDestroy() {
        this.view = null
    }

    override fun onResult(user: BaseUser?) {
        view?.onResult(user)
    }

}