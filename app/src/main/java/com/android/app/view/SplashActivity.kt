package com.android.app.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.android.app.MainActivity
import com.android.app.R
import com.android.app.contract.IUser
import com.android.app.data.UserSingleton
import com.android.app.data.model.BaseUser
import com.android.app.presenter.login.UserPresenter
import com.android.app.view.login.ContinueUserDataActivity
import com.android.app.view.login.LoginActivity
import com.android.app.view.login.VerifiedEmailActivity

class SplashActivity : AppCompatActivity(), IUser.View {
    internal lateinit var iPresenter: IUser.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        iPresenter = UserPresenter(this)
    }

    override fun onResume() {
        super.onResume()
        Handler().postDelayed(Runnable {
            iPresenter.currentUser(this)
        }, 1000)
    }

    override fun onDestroy() {
        iPresenter.onDestroy()
        super.onDestroy()
    }

    override fun onResult(user: BaseUser?) {
        if (user == null) {
            startActivity(Intent(getApplication(), LoginActivity::class.java))
            finish()
        } else {
            UserSingleton.instance.setUser(user) //Salvar dados numa unica instancia de user
            if (user.emailVerified) {//Verificacao de email
                navigateToMainActivity()
            } else {
                navigateToVerifiedEmail()
            }
        }
    }

    private fun navigateToMainActivity() {
        startActivity(Intent(getApplication(), MainActivity::class.java))
        finish()
    }

    private fun navigateToContinueUserData() {
        startActivity(Intent(getApplication(), ContinueUserDataActivity::class.java))
        finish()
    }

    private fun navigateToVerifiedEmail() {
        startActivity(Intent(getApplication(), VerifiedEmailActivity::class.java))
        finish()
    }

}