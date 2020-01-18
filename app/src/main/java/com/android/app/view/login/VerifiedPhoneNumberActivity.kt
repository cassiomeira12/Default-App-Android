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
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.activity_phone_number.*
import kotlinx.android.synthetic.main.activity_verified_phone_number.*
import java.util.concurrent.TimeUnit

class VerifiedPhoneNumberActivity : AppCompatActivity() {

    lateinit var phoneNumber: PhoneNumber

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verified_phone_number)

        phoneNumber = getIntent().getSerializableExtra("phone_number") as PhoneNumber

        txtPhoneNumber.setText(phoneNumber.toString())
    }

    fun confirm(view: View) {
        PhoneAuthProvider.getInstance()
            .verifyPhoneNumber(phoneNumber.toString(), 60, TimeUnit.SECONDS, this, object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    val code = credential.smsCode
                    Log.d("cassio", code)
                }

                override fun onVerificationFailed(exception: FirebaseException) {
                    Log.e("cassio", exception.toString())
                }

                override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                    Log.d("cassio", verificationId)
                    Log.d("cassio", token.toString())
                }
            })
    }




}
