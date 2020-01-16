package com.android.app.view.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import com.android.app.R
import com.android.app.data.model.PhoneNumber
import kotlinx.android.synthetic.main.activity_phone_number.*
import kotlinx.android.synthetic.main.activity_verified_phone_number.*

class VerifiedPhoneNumberActivity : AppCompatActivity() {

    lateinit var phoneNumber: PhoneNumber

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verified_phone_number)

        phoneNumber = getIntent().getSerializableExtra("phone_number") as PhoneNumber

        txtPhoneNumber.setText(phoneNumber.toString())
    }

    fun confirm(view: View) {

    }
}
