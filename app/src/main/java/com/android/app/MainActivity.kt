package com.android.app

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.android.app.contract.IUser
import com.android.app.data.model.BaseUser
import com.android.app.presenter.login.UserPresenter
import com.android.app.utils.PermissionUtils
import com.android.app.view.chat.ChatsFragment
import com.android.app.view.home.HomeFragment
import com.android.app.view.notifications.NotificationsFragment
import com.android.app.view.settings.SettingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity: AppCompatActivity() {
    private val TAG = javaClass.simpleName

    lateinit var navView: BottomNavigationView

    companion object {
        var currentFragment: Fragment? = null
        var homeFragment = HomeFragment()
        var chatsFragment = ChatsFragment()
        var notificationsFragment = NotificationsFragment()
        var settingsFragment = SettingsFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val appBarConfiguration = AppBarConfiguration.Builder(
//            R.id.navigation_home,
//            R.id.navigation_chats,
//            R.id.navigation_notifications,
//            R.id.navigation_settings
//        ).build()

        navView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        //val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        //NavigationUI.setupWithNavController(navView, navController)
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        currentFragment = homeFragment
        setFragment(homeFragment)

        UserPresenter(object : IUser.View {
            override fun onFailure(message: String) { }
            override fun onResult(user: BaseUser?) { }
        }).updateOnline()

        PermissionUtils.requestPermissionWriteStorage(this)
    }

    override fun onBackPressed() {
        if (currentFragment == homeFragment) {
            super.onBackPressed()
        } else {
            navView.setSelectedItemId(R.id.navigation_home)
        }
    }

    fun showBadgeHome(number: Int) {
        showBadge(this, navView, R.id.navigation_home, number)
    }

    fun showBadgeChats(number: Int) {
        showBadge(this, navView, R.id.navigation_chats, number)
    }

    fun showBadgeNotifications(number: Int) {
        showBadge(this, navView, R.id.navigation_notifications, number)
    }

    fun showBadgeSettings(number: Int) {
        showBadge(this, navView, R.id.navigation_settings, number)
    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment, fragment.javaClass.getSimpleName())
            .commit()
    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
        when (menuItem.itemId) {
            R.id.navigation_home -> {
                //showBadge(this, navView, R.id.navigation_home, 0)
                currentFragment = homeFragment
                setFragment(homeFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_chats -> {
                //showBadge(this, navView, R.id.navigation_chats, 5)
                currentFragment = chatsFragment
                setFragment(chatsFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                //showBadge(this, navView, R.id.navigation_notifications, 0)
                currentFragment = notificationsFragment
                setFragment(notificationsFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_settings -> {
                //showBadge(this, navView, R.id.navigation_settings, 0)
                currentFragment = settingsFragment
                setFragment(settingsFragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun showBadge(context: Context, navView: BottomNavigationView, @IdRes itemId: Int, number: Int) {
        Handler().post(Runnable {
            removeBadge(navView, itemId)
            if (number > 0) {
                val itemView: BottomNavigationItemView = navView.findViewById(itemId)
                val badge = LayoutInflater.from(context).inflate(R.layout.layout_news_badge, navView, false)
                val text = badge.findViewById<TextView>(R.id.badge_text_view)
                text.text = number.toString()
                itemView.addView(badge)
            }
        })
    }

    private fun removeBadge(navView: BottomNavigationView, @IdRes itemId: Int) {
        val itemView: BottomNavigationItemView = navView.findViewById(itemId)
        if (itemView.childCount == 3) {
            itemView.removeViewAt(2)
        }
    }

}