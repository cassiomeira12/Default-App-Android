package com.android.app.view.login

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.android.app.MainActivity
import com.android.app.R
import com.android.app.data.model.BaseUser
import com.android.app.contract.ICreateAccountContract
import com.android.app.data.UserSingleton
import com.android.app.presenter.login.CreateAccountPresenter
import kotlinx.android.synthetic.main.activity_create_account.*

class CreateAccountActivity : AppCompatActivity(), ICreateAccountContract.View {
    internal lateinit var iPresenter: ICreateAccountContract.Presenter

    internal lateinit var _user: BaseUser
    internal lateinit var _login: String
    internal lateinit var _password: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        _user = UserSingleton.instance
        _login = _user.email
        _password = _user.password

        iPresenter = CreateAccountPresenter(this)
    }

    override fun onResume() {
        super.onResume()
        createAccount()
    }

    override fun onDestroy() {
        iPresenter.onDestroy()
        super.onDestroy()
    }

    private fun createAccount() {
        iPresenter.register(this, _user, _login, _password)
    }

    override fun showProgress() {
        imgResult.visibility = View.INVISIBLE
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar.visibility = View.INVISIBLE
        imgResult.visibility = View.VISIBLE
    }

    override fun onCreatedSuccess(user: BaseUser) {
        UserSingleton.instance.setUser(user) //Atualizando instancia do usuario
        txtMessage.text = getString(R.string.conta_criada_com_sucesso)
        Handler().postDelayed(
                Runnable {
                    startActivity(Intent(getApplicationContext(), VerifiedEmailActivity::class.java))
                    finish()
                }, 1500
        )
    }

    override fun onFailure(message: String) {
        imgResult.setImageResource(R.drawable.error)
        txtMessage.text = message
    }

}