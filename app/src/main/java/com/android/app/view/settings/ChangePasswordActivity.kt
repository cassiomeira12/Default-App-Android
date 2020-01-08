package com.android.app.view.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.android.app.R
import com.android.app.contract.IUser
import com.android.app.data.UserSingleton
import com.android.app.data.model.BaseUser
import com.android.app.presenter.login.UserPresenter
import kotlinx.android.synthetic.main.activity_change_password.*

class ChangePasswordActivity : AppCompatActivity(), IUser.View {
    private val TAG = javaClass.simpleName

    internal lateinit var iPresenter: IUser.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        iPresenter = UserPresenter(this)
    }

    fun changePassword(view: View) {
        if (isDataValid()) {
            btnChangePassword.visibility = View.INVISIBLE
            val email = UserSingleton.instance.email
            val currentPassword = edtCurrentPassword.text.toString()
            val newPassword = edtNewPassword.text.toString()
            setEnableEdt(false)
            iPresenter.onChangePassword(email, currentPassword, newPassword)
        }
    }

    private fun isDataValid(): Boolean {
        var isPassswordValid: Boolean

        val currentPassword = edtCurrentPassword.text.toString()
        isPassswordValid = currentPassword.length >= 6
        if (!isPassswordValid) {
            edtCurrentPassword.error = getString(R.string.senha_curta)
            edtCurrentPassword.requestFocus()
            return false
        }

        val newPassword = edtNewPassword.text.toString()
        isPassswordValid = newPassword.length >= 6
        if (!isPassswordValid) {
            edtNewPassword.error = getString(R.string.senha_curta)
            edtConfirmePassword.text.clear()
            edtNewPassword.requestFocus()
            return false
        } else {
            val secondPassword = edtConfirmePassword.text.toString()
            isPassswordValid = isPassswordValid && TextUtils.equals(newPassword, secondPassword)
            if (!isPassswordValid) {
                edtNewPassword.error = getString(R.string.senha_nao_sao_iguais)
                edtConfirmePassword.text.clear()
                edtNewPassword.requestFocus()
                return false
            }
        }

        return true
    }

    private fun setEnableEdt(value: Boolean) {
        edtCurrentPassword.isEnabled = value
        edtNewPassword.isEnabled = value
        edtConfirmePassword.isEnabled = value
    }

    override fun onFailure(message: String) {
        btnChangePassword.visibility = View.VISIBLE
        setEnableEdt(true)
        edtCurrentPassword.error = message
        edtNewPassword.text.clear()
        edtConfirmePassword.text.clear()
        edtCurrentPassword.requestFocus()
    }

    override fun onResult(user: BaseUser?) {
        btnChangePassword.visibility = View.VISIBLE
        Toast.makeText(getApplicationContext(), "Senha alterada com sucesso!", Toast.LENGTH_LONG).show()
        setEnableEdt(true)
        edtCurrentPassword.text.clear()
        edtNewPassword.text.clear()
        edtConfirmePassword.text.clear()
    }
}
