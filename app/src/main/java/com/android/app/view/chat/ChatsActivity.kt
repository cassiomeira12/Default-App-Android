package com.android.app.view.chat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.app.R
import com.android.app.data.model.Chat
import com.android.app.view.adapter.Adapter
import com.android.app.view.adapter.AdapterChat
import kotlinx.android.synthetic.main.activity_chats.*

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

        adapter.add(Chat("UESB Computação"))
        adapter.add(Chat("Frei"))
        adapter.add(Chat("Us Karas"))
    }

    override fun onLongClickItem(view: View?) {

    }

    override fun onClickItem(view: View?) {
        if (adapter.itensSelected.isEmpty()) {
            startActivity(Intent(getApplicationContext(), ChatActivity::class.java))
        }
    }
}
