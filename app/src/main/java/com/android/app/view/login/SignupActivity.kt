package com.android.app.view.login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.android.app.R
import com.android.app.data.UserSingleton
import kotlinx.android.synthetic.main.activity_signup.*

class SignupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
    }

    override fun onBackPressed() {
        startActivity(Intent(getApplication(), LoginActivity::class.java))
        finish()
    }

    fun onSignUp(view: View) {
        if (isDataValid()) {
            startActivity(Intent(getApplication(), CreateAccountActivity::class.java))
            finish()
        }
    }

    fun isDataValid(): Boolean {
        val name = edtName.text.toString().trim()
        val isNameValid = !TextUtils.isEmpty(name)
        if (!isNameValid) {
            edtName.error = "Digite seu nome"
        }

        val email = edtEmail.text.toString().trim()
        val isEmailValid = (!TextUtils.isEmpty(email)) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
        if (!isEmailValid) {
            edtEmail.error = "Email inválido"
        }

//        val phone = txtPhone.text.toString()
//        var isPhoneValid = (!TextUtils.isEmpty(phone)) && Patterns.PHONE.matcher(phone).matches()
//        if (!isPhoneValid) {
//            txtPhone?.error = "Telefone inválido"
//        }

        val firstPassword = edtPassword.text.toString()
        var isPassswordValid = firstPassword.length >= 6
        if (!isPassswordValid) {
            edtPassword.error = "Senha muito curta"
            edtPasswordComfirmed.text.clear()
            edtPassword.requestFocus()
        } else {
            val secondPassword = edtPasswordComfirmed.text.toString()
            isPassswordValid = isPassswordValid && TextUtils.equals(firstPassword, secondPassword)
            if (!isPassswordValid) {
                edtPasswordComfirmed.error = "Senha nao confere"
            }
        }

        val isDataValid = isNameValid && isEmailValid && isPassswordValid

        if (isDataValid) {
            UserSingleton.instance.name = name
            UserSingleton.instance.email = email
            UserSingleton.instance.password = firstPassword
        }

        return isDataValid
    }

    fun termosUso(view: View) {
        val intent = Intent(getApplication(), TermosAppActivity::class.java)
        intent.putExtra(TermosAppActivity.TERMO, TermosAppActivity.TERMO_DE_USO)
        startActivity(intent)
    }

    fun politicaPrivacidade(view: View) {
        val intent = Intent(getApplication(), TermosAppActivity::class.java)
        intent.putExtra(TermosAppActivity.TERMO, TermosAppActivity.POLITICA_DE_PRIVACIDADE)
        startActivity(intent)
    }

    fun politicaDados(view: View) {
        val intent = Intent(getApplication(), TermosAppActivity::class.java)
        intent.putExtra(TermosAppActivity.TERMO, TermosAppActivity.POLITICA_DE_DADOS)
        startActivity(intent)
    }

}