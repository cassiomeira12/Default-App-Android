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

    lateinit var homeFragment: HomeFragment
    lateinit var chatsFragment: ChatsFragment
    lateinit var notificationsFragment: NotificationsFragment
    lateinit var settingsFragment: SettingsFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        homeFragment = HomeFragment()
//        chatsFragment = ChatsFragment()
//        notificationsFragment = NotificationsFragment()
//        settingsFragment = SettingsFragment()

//        val appBarConfiguration = AppBarConfiguration.Builder(
//            R.id.navigation_home,
//            R.id.navigation_chats,
//            R.id.navigation_notifications,
//            R.id.navigation_settings
//        ).build()

        val navView = findViewById<BottomNavigationView>(R.id.nav_view)
        //navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        NavigationUI.setupWithNavController(navView, navController)
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

//        showBadge(this, navView, R.id.navigation_home, 0)
//        showBadge(this, navView, R.id.navigation_notifications, 11)
//        showBadge(this, navView, R.id.navigation_chats, 0)
//        showBadge(this, navView, R.id.navigation_settings, 9)

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when(destination.id) {
                R.id.navigation_home -> {
                    Handler().post(Runnable {
                        //showBadge(this, navView, R.id.navigation_home, 0);
                    })
                }
                R.id.navigation_notifications -> {
                    Handler().post(Runnable {
                        //showBadge(this, navView, R.id.navigation_notifications, 0);
                    })
                }
                R.id.navigation_chats -> {
                    Handler().post(Runnable {
                        //showBadge(this, navView, R.id.navigation_chats, 0);
                    })
                }
                R.id.navigation_settings -> {
                    Handler().post(Runnable {
                        //showBadge(this, navView, R.id.navigation_settings, 0);
                    })
                }
            }
        }

        UserPresenter(object : IUser.View {
            override fun onFailure(message: String) { }
            override fun onResult(user: BaseUser?) { }
        }).updateOnline()

        PermissionUtils.requestPermissionWriteStorage(this)
    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
        when (menuItem.itemId) {
            R.id.navigation_home -> {
                val fragment = homeFragment
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, fragment, fragment.javaClass.getSimpleName())
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_chats -> {
                val fragment = chatsFragment
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, fragment, fragment.javaClass.getSimpleName())
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                val fragment = notificationsFragment
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, fragment, fragment.javaClass.getSimpleName())
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_settings -> {
                val fragment = settingsFragment
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, fragment, fragment.javaClass.getSimpleName())
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun showBadge(context: Context, navView: BottomNavigationView, @IdRes itemId: Int, number: Int) {
        removeBadge(navView, itemId)
        if (number > 0) {
            val itemView: BottomNavigationItemView = navView.findViewById(itemId)
            val badge = LayoutInflater.from(context).inflate(R.layout.layout_news_badge, navView, false)
            val text = badge.findViewById<TextView>(R.id.badge_text_view)
            text.text = number.toString()
            itemView.addView(badge)
        }
    }

    private fun removeBadge(navView: BottomNavigationView, @IdRes itemId: Int) {
        val itemView: BottomNavigationItemView = navView.findViewById(itemId)
        if (itemView.childCount == 3) {
            itemView.removeViewAt(2)
        }
    }

}