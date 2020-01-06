package com.android.app.view.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.android.app.R
import kotlinx.android.synthetic.main.activity_change_password.*

class ChangePasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
    }

    fun changePassword(view: View) {
        btnChangePassword.visibility = View.INVISIBLE
    }
}
