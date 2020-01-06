package com.android.app.view.notifications

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.android.app.R
import com.android.app.utils.PreferenceUtils
import kotlinx.android.synthetic.main.activity_notifications_config.*

class NotificationsConfigActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifications_config)

        showData()
    }

    fun showData() {
        switchNovidadesPromocoes.isChecked = PreferenceUtils(this).getPushNotification()
    }

    fun onClick(view: View) {
        when(view.id) {
            R.id.layoutNovidadesPromocoes -> {
                switchNovidadesPromocoes.isChecked = !switchNovidadesPromocoes.isChecked
                PreferenceUtils(this).setPushNotification(switchNovidadesPromocoes.isChecked)
                if (switchNovidadesPromocoes.isChecked) {
                    toast("Notificações ativadas")
                } else {
                    toast("Notificações desativadas")
                }
            }
        }
    }

    private fun toast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
}
