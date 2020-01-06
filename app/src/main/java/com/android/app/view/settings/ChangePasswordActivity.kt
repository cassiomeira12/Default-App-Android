package com.android.app.view.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.android.app.R
import kotlinx.android.synthetic.main.activity_change_password.*

class ChangePasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
    }

    fun changePassword(view: View) {
        if (isDataValid()) {
            btnChangePassword.visibility = View.INVISIBLE
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
}
