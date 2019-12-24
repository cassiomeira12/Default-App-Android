package com.android.app.view.login

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.app.R
import com.android.app.contract.IForgotPasswordContract
import com.android.app.presenter.login.ForgotPasswordPresenter
import kotlinx.android.synthetic.main.activity_forgot_password.*

class ForgotPasswordActivity : AppCompatActivity(), IForgotPasswordContract.View {
    internal lateinit var iForgotPasswordPresenter: IForgotPasswordContract.Presenter
    internal lateinit var email: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        iForgotPasswordPresenter = ForgotPasswordPresenter(this)
    }

    fun send(view: View) {
        if (isDataValid()) {
            btnSend.visibility = View.GONE
            email = edtEmail.text.toString().trim()
            iForgotPasswordPresenter.onSend(email)
        }
    }

    fun isDataValid(): Boolean {
        val email = edtEmail.text.toString().trim()
        val isEmailValid = (!TextUtils.isEmpty(email)) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
        if (!isEmailValid) {
            edtEmail.error = getString(R.string.email_invalido)
        }
        return isEmailValid
    }

    override fun onSuccessResult() {
        layoutForgotPassword.visibility = View.GONE
        layoutForgotPasswordSuccess.visibility = View.VISIBLE
        txtEmail.text = email
    }

    override fun onFailureResult(message: String) {
        btnSend.visibility = View.VISIBLE
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    fun onBackActivity(view: View) {
        finish()
    }

}