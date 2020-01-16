package com.android.app.view.login

import android.content.Intent
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

class PhoneNumberActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_number)


        edtPhoneNumber.addTextChangedListener(phoneNumber(edtPhoneNumber))
    }

    fun confirm(view: View) {
        if (phoneNumber.PHONE_VALID) {
            val phoneNumber = PhoneNumber(edtPhoneNumber.text.toString())
            val intent = Intent(getApplicationContext(), VerifiedPhoneNumberActivity::class.java)
            intent.putExtra("phone_number", phoneNumber)
            startActivity(intent)
        }
    }

    class phoneNumber(var edtText: EditText): TextWatcher {

        companion object {
            val MAX_DIGITS = 16
            var WRINTING = true
            var PHONE_VALID = false
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            WRINTING = (count < 1) //Enquanto estiver digitando
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }

        override fun afterTextChanged(s: Editable?) {
            if (s!!.length > MAX_DIGITS) {
                edtText.removeTextChangedListener(this)
                edtText.setText(s.toString().substring(0, MAX_DIGITS))
                edtText.setSelection(MAX_DIGITS)
                edtText.addTextChangedListener(this)
                if (!verifiedPhoneNumber(s.toString().substring(0, MAX_DIGITS))) {
                    edtText.error = "Número inválido"
                }
                return
            }

            if (WRINTING) {
                var string = s.toString()
                edtText.removeTextChangedListener(this)
                string = formatText(string)
                edtText.setText(string)
                edtText.setSelection(string.length)
                edtText.addTextChangedListener(this)
                if (string.length >= MAX_DIGITS) {
                    if (!verifiedPhoneNumber(string)) {
                        edtText.error = "Número inválido"
                    }
                }
            }
        }

        fun isPhoneNumberValid(): Boolean {
            return verifiedPhoneNumber(edtText.toString())
        }

        private fun verifiedPhoneNumber(value: String): Boolean {
            // (XX)_X_XXXX-XXXX
            PHONE_VALID = false
            var index = 0
            var char = value[index]

            if (!char.equals('(')) { // (
                return false
            }
            while (++index < 3) { // (XX
                char = value[index]
                if (!char.isDigit()) {
                    return false
                }
            }
            char = value[index++]
            if (!char.equals(')')) { // (XX)
                return false
            }
            if (!value[index++].isWhitespace()) { // (XX)_
                return false
            }
            if (!value[index++].isDigit()) { // (XX)_X
                return false
            }
            if (!value[index++].isWhitespace()) { // (XX)_X_
                return false
            }
            while (++index <= 10) { // (XX)_X_XXXX
                char = value[index]
                if (!char.isDigit()) {
                    return false
                }
            }
            if (!value[index].equals('-')) { // (XX)_X_XXXX-
                return false
            }
            while (++index < MAX_DIGITS) { // (XX)_X_XXXX-XXXX
                char = value[index]
                if (!char.isDigit()) {
                    return false
                }
            }
            PHONE_VALID = true
            return true
        }

        private fun formatText(value: String): String {
            var newValue = ""
            if (value.length == 1) {
                newValue = "($value"//Abrir parenteses + primeiro digito
            } else if (value.length == 2) {
                newValue = value
            } else if (value.length == 3) {
                newValue = "$value) 9 "//Segundo digito + fecha parentese
            }
            if (value.length == 4) {
                val char = value[3]
                if (char.isDigit()) {
                    newValue = "${value.substring(0, 3)}) 9 $char"
                }
            }
            if (value.length == 5) {
                val char = value[4]
                if (char.isDigit()) {
                    newValue = "${value.substring(0, 4)} 9 $char"
                }
            }
            if (value.length == 6) {
                val char = value[5]
                newValue = "${value.substring(0, 5)}9 $char"
            }
            if (value.length == 7) {
                val char = value[6]
                if (char.isDigit()) {
                    newValue = "${value.substring(0, 6)} $char"
                }
            }
            if (value.length > 7 && value.length < 11) {
                newValue = value
            } else if (value.length == 11) {
                newValue = value.plus("-")
            }
            if (value.length == 12) {
                val char = value[11]
                if (char.isDigit()) {
                    newValue = "${value.substring(0, 11)}-$char"
                }
            }
            if (value.length > 12 && value.length <= MAX_DIGITS) {
                newValue = value
            }
            return newValue
        }
    }
}
