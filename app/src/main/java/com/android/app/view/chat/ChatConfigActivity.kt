package com.android.app.view.chat

import android.os.Bundle
import android.view.View
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.app.R
import com.android.app.data.model.BaseUser
import com.android.app.view.adapter.Adapter
import com.android.app.view.adapter.AdapterUser
import kotlinx.android.synthetic.main.activity_chat_config.*
import kotlinx.android.synthetic.main.content_chat_config.*

class ChatConfigActivity : AppCompatActivity(), Adapter.Actions {

    private lateinit var adapter: AdapterUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_config)
        setSupportActionBar(toolbar)

        adapter = AdapterUser(ArrayList(), this, this)
        val layout = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerUsers.layoutManager = layout
        recyclerUsers.adapter = adapter

        adapter.add(BaseUser())
        adapter.add(BaseUser())
        adapter.add(BaseUser())
        adapter.add(BaseUser())
        adapter.add(BaseUser())
        adapter.add(BaseUser())

    }

    override fun onLongClickItem(view: View?) {

    }

    override fun onClickItem(view: View?) {

    }

}
