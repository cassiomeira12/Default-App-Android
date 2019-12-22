package com.android.app.presenter.login

import android.app.Activity
import android.content.Context
import android.text.TextUtils
import android.util.Patterns
import com.android.app.R
import com.android.app.contract.ILoginContract
import com.android.app.data.model.BaseUser
import com.android.app.data.services.login.FirebaseLoginService
import com.android.app.data.services.login.RestLoginService

class LoginPresenter (view: ILoginContract.View) : ILoginContract.Presenter, ILoginContract.Listener {
    var view: ILoginContract.View? = view
    var service: ILoginContract.Service = FirebaseLoginService(this)

    override fun onLogin(activity: Activity, email: String, password: String) {
        val isLoginValid = isDataValid(activity.applicationContext ,email, password)

        if (isLoginValid) {
            view?.showProgress()
            service.onLogin(activity, email, password)
        } else {
            view?.onFailure(activity.getString(R.string.dados_login_incorretos))
        }
    }

    private fun isDataValid(context: Context, email: String, password: String) : Boolean {
        val emailValid = (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches())
        val passwordValid = (password.length >= 6)

        if (!emailValid) {
            view?.setLoginNameError(context.getString(R.string.email_invalido))
        }

        if (!passwordValid) {
            view?.setPasswordNameError(context.getString(R.string.senha_curta))
        }

        return emailValid && passwordValid
    }

    override fun onDestroy() {
        this.view = null
    }

    override fun onSuccess(user: BaseUser) {
        view?.hideProgress()
        view?.onSuccess(user)
    }

    override fun onFailure(message: String) {
        view?.hideProgress()
        view?.onFailure(message)
    }

}