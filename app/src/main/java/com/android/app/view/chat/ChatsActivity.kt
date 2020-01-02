package com.android.app.view.chat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.app.R
import com.android.app.contract.IChatsContract
import com.android.app.data.UserSingleton
import com.android.app.data.model.Chat
import com.android.app.presenter.chat.ChatsPresenter
import com.android.app.view.adapter.Adapter
import com.android.app.view.adapter.AdapterChat
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_chat_config.*
import kotlinx.android.synthetic.main.activity_chats.*
import kotlinx.android.synthetic.main.activity_chats.fab
import kotlinx.android.synthetic.main.activity_chats.toolbar
import java.util.*
import kotlin.collections.ArrayList

class ChatsActivity : AppCompatActivity(), Adapter.Actions, IChatsContract.View {
    private val TAG = javaClass.simpleName

    internal lateinit var iPresenter: IChatsContract.Presenter

    lateinit var adapter: AdapterChat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chats)

        adapter = AdapterChat(ArrayList<Chat>(), this, this)
        val layout = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerChats.layoutManager = layout
        recyclerChats.adapter = adapter

        iPresenter = ChatsPresenter(this)
        iPresenter.listChats()

        fab.setOnClickListener { view ->
            val chat = Chat("Grupo", Date())
            val userID = UserSingleton.instance.uID
            chat.createdBy = userID
            chat.administradores.put(chat.administradores.size.toString(), userID)
            chat.users.put(chat.users.size.toString(), userID)
            chat.descricao = "Descricao do grupo"

            iPresenter.createChat(this, chat)
        }

        supportNaviagteUp()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        if (adapter.isItensSelectedEmpty) {
            super.onBackPressed()
        }

        adapter.clearItensSelected()
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
            R.id.action_add_user -> {
                
                return true
            }
            R.id.action_settings -> {

                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onLongClickItem(view: View?) {

    }

    override fun onClickItem(view: View?) {
        if (adapter.itensSelected.isEmpty() && adapter.objectSelected != null) {
            val intent = Intent(getApplicationContext(), ChatActivity::class.java)
            intent.putExtra("chat", adapter.objectSelected)
            startActivity(intent)
        }
    }

    override fun showProgress() {
        layoutCarregando.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        layoutCarregando.visibility = View.INVISIBLE
    }

    override fun onCreatedSuccess(chat: Chat) {
        adapter.add(chat)
    }

    override fun onRemovedSucess(chat: Chat) {
        adapter.remove(chat)
    }

    override fun onListSuccess(list: List<Chat>) {
        adapter.itensList.addAll(list)
        adapter.notifyDataSetChanged()
        if (adapter.isEmpty) {
            txtSemChats.visibility = View.VISIBLE
        } else {
            txtSemChats.visibility = View.INVISIBLE
        }
    }

    override fun onFailure(message: String) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show()
    }
}
