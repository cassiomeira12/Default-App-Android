package com.android.app.view.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.app.MainActivity
import com.android.app.R
import com.android.app.contract.IGoogleSignContract
import com.android.app.contract.ILoginContract
import com.android.app.data.UserSingleton
import com.android.app.data.model.BaseUser
import com.android.app.presenter.login.GoogleSignPresenter
import com.android.app.presenter.login.LoginPresenter
import com.android.app.view.dialog.ProgressDialog
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), ILoginContract.View, IGoogleSignContract.View {
    internal lateinit var progressDialog: ProgressDialog
    internal lateinit var loginPresenter: ILoginContract.Presenter
    internal lateinit var googlePresenter: IGoogleSignContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        progressDialog = ProgressDialog(this)

        loginPresenter = LoginPresenter(this)
        googlePresenter = GoogleSignPresenter(this)
        googlePresenter.createGoogleClient(this)
    }

    override fun onDestroy() {
        loginPresenter.onDestroy()
        googlePresenter.onDestroy()
        super.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        googlePresenter.onActivityResult(requestCode, resultCode, data)
    }

    fun signIn(view: View) {
        val email = edtLogin.text.toString().trim()
        val password = edtPassword.text.toString()
        loginPresenter.onLogin(this, email, password)
    }

    fun recuperarSenha(view: View) {
        startActivity(Intent(getApplication(), ForgotPasswordActivity::class.java))
    }

    fun googleSignIn(view: View) {
        googlePresenter.onSignIn()
    }

    fun facebookSignIn(view: View) {

    }

    fun createAccount(view: View) {
        startActivity(Intent(getApplication(), SignupActivity::class.java))
        finish()
    }

    override fun showProgress() {
        progressDialog.show()
    }

    override fun hideProgress() {
        progressDialog.dismiss()
    }

    override fun setLoginNameError(message: String) {
        edtLogin.error = message
    }

    override fun setPasswordNameError(message: String) {
        edtPassword.error = message
    }

    fun navigateToHome(user: BaseUser) {
        if (user.emailVerified) {
            navigateToMainActivity(user)
        } else {
            navigateToVerifiedEmail(user)
        }
    }

    private fun navigateToMainActivity(user: BaseUser) {
        startActivity(Intent(getApplication(), MainActivity::class.java))
        finish()
    }

    private fun navigateToContinueUserData(user: BaseUser) {
        startActivity(Intent(getApplication(), ContinueUserDataActivity::class.java))
        finish()
    }

    private fun navigateToVerifiedEmail(user: BaseUser) {
        startActivity(Intent(getApplication(), VerifiedEmailActivity::class.java))
        finish()
    }

    override fun onSuccess(user: BaseUser) {
        UserSingleton.instance.setUser(user)
        navigateToHome(user)
    }

    override fun onFailure(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

}