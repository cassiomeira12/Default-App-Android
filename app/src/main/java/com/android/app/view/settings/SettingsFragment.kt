package com.android.app.view.settings

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.android.app.R
import com.android.app.contract.IUser
import com.android.app.data.UserSingleton
import com.android.app.data.model.BaseUser
import com.android.app.presenter.login.UserPresenter
import com.android.app.utils.ImageUtils
import com.android.app.view.login.LoginActivity
import com.android.app.view.login.TermosAppActivity
import com.android.app.view.notifications.NotificationsConfigActivity
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment: Fragment(), View.OnClickListener {
    private val TAG = "cassio"//javaClass.simpleName

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d(TAG, javaClass.simpleName)

        setHasOptionsMenu(true)
        getActivity()!!.invalidateOptionsMenu()

        setListeners()
        showUserData(UserSingleton.instance)
    }

    private fun setListeners() {
        layoutImgUser.setOnClickListener(this)
        layoutUserSettings.setOnClickListener(this)
        layoutNotificationsSettings.setOnClickListener(this)
        layoutAboutSettings.setOnClickListener(this)
        layoutTermosSettings.setOnClickListener(this)
        layoutDisableAccountSettings.setOnClickListener(this)
        layoutExitApp.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.layoutImgUser, R.id.layoutUserSettings -> {
                startActivity(Intent(getContext(), UserSettingsActivity::class.java))
            }
            R.id.layoutNotificationsSettings -> {
                startActivity(Intent(getContext(), NotificationsConfigActivity::class.java))
            }

            R.id.layoutAboutSettings -> {
                startActivity(Intent(getContext(), AboutAppActivity::class.java))
            }
            R.id.layoutTermosSettings -> {
                startActivity(Intent(getContext(), TermosAppActivity::class.java))
            }
            R.id.layoutDisableAccountSettings -> {
                startActivity(Intent(getContext(), DisableAccountActivity::class.java))
            }
            R.id.layoutExitApp -> {
                showDialogDeleteMessages(getActivity()!!, "Sair", "Deseja realmente sair do aplicativo ?")
            }
        }
    }

    private fun showUserData(user: BaseUser) {
        txtUserName.setText(user.name)
        ImageUtils(getContext()!!).picassoImageUser(user.uID, imgUser, "asdfasdf", progressBar)
    }

    private fun showDialogDeleteMessages(activity: Activity, title: String, message: String) {
        var alertBuilder = AlertDialog.Builder(activity)
        alertBuilder.setTitle(title)
        alertBuilder.setMessage(message)
        alertBuilder.setPositiveButton(activity.getString(R.string.sim)) { dialog, which ->
            exitToApp()
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
                Toast.makeText(getContext(), "Saiu do App", Toast.LENGTH_LONG).show()
                startActivity(Intent(getContext(), LoginActivity::class.java))
                getActivity()!!.finish()
            }
        }).signOut(getContext()!!)
    }
}