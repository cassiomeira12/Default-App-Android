package com.android.app.view.settings

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.app.R
import com.android.app.contract.IUser
import com.android.app.data.UserSingleton
import com.android.app.data.model.BaseUser
import com.android.app.presenter.login.UserPresenter
import com.android.app.utils.ImageUtils
import com.android.app.view.login.TermosAppActivity
import com.android.app.view.notifications.NotificationsConfigActivity
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        showUserData(UserSingleton.instance)
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.layoutImgUser, R.id.layoutUserSettings -> {
                startActivity(Intent(this, UserSettingsActivity::class.java))
            }
            R.id.layoutNotificationsSettings -> {
                startActivity(Intent(this, NotificationsConfigActivity::class.java))
            }

            R.id.layoutAboutSettings -> {
                startActivity(Intent(this, AboutAppActivity::class.java))
            }
            R.id.layoutTermosSettings -> {
                startActivity(Intent(this, TermosAppActivity::class.java))
            }
            R.id.layoutDisableAccountSettings -> {
                startActivity(Intent(this, DisableAccountActivity::class.java))
            }
            R.id.layoutExitApp -> {
                showDialogDeleteMessages(this, "Sair", "Deseja realmente sair do aplicativo ?")
            }
        }
    }

    private fun showUserData(user: BaseUser) {
        txtUserName.setText(user.name)
        ImageUtils(this).picassoImageUser(user.uID, imgUser, "asdfasdf", progressBar)
    }

    private fun showDialogDeleteMessages(activity: Activity, title: String, message: String) {
        var alertBuilder = AlertDialog.Builder(activity)
        alertBuilder.setTitle(title)
        alertBuilder.setMessage(message)
        alertBuilder.setPositiveButton(activity.getString(R.string.sim)) { dialog, which ->
            //exitToApp()
            dialog.dismiss()
        }
        alertBuilder.setNegativeButton(activity.getString(R.string.cancelar)) { dialog, which ->
            dialog.cancel()
        }
        val alert = alertBuilder.create()
        alert.setCanceledOnTouchOutside(false)
        alert.show()
    }

    private fun exitToApp() {
        UserPresenter(object : IUser.View {
            override fun onResult(user: BaseUser?) {
                Toast.makeText(applicationContext, "Saiu do App", Toast.LENGTH_LONG).show()
            }
        }).signOut(this)
    }
}
