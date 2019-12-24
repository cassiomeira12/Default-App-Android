package com.android.app.view.chat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.app.R
import com.android.app.data.model.Chat
import com.android.app.view.adapter.Adapter
import com.android.app.view.adapter.AdapterChat
import kotlinx.android.synthetic.main.activity_chats.*
import java.util.*
import kotlin.collections.ArrayList

class ChatsActivity : AppCompatActivity(), Adapter.Actions {
    private val TAG = javaClass.simpleName

    lateinit var adapter: AdapterChat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chats)

        adapter = AdapterChat(ArrayList<Chat>(), this, this)
        val layout = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerChats.layoutManager = layout
        recyclerChats.adapter = adapter

        adapter.add(Chat("UESB Computação", Date()))
        adapter.add(Chat("Frei", Date()))
        adapter.add(Chat("Us Karas", Date()))

        supportNaviagteUp()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun supportNaviagteUp() {
        setSupportActionBar(toolbar)
        //getSupportActionBar()!!.setDisplayShowHomeEnabled(true)
        //getSupportActionBar()!!.setHomeButtonEnabled(true)
        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()!!.setDisplayShowTitleEnabled(false)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        getMenuInflater().inflate(R.menu.menu_chat, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_settings -> {

                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onLongClickItem(view: View?) {

    }

    override fun onClickItem(view: View?) {
        if (adapter.itensSelected.isEmpty()) {
            val intent = Intent(getApplicationContext(), ChatActivity::class.java)
            intent.putExtra("chat", adapter.objectSelected)
            startActivity(intent)
        }
    }
}
