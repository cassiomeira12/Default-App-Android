package com.android.app.view.notifications

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.android.app.R
import kotlinx.android.synthetic.main.activity_notifications_config.*

class NotificationsConfigActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifications_config)
    }

    fun onClick(view: View) {
        when(view.id) {
            R.id.layoutNovidadesPromocoes -> {
                switchNovidadesPromocoes.isChecked = !switchNovidadesPromocoes.isChecked
            }
        }
    }
}
