package com.android.app.view.chat

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.app.R
import com.android.app.contract.IMessagesContract
import com.android.app.data.UserSingleton
import com.android.app.data.model.Chat
import com.android.app.data.model.Message
import com.android.app.presenter.chat.MessagePresenter
import com.android.app.utils.ImageUtils
import com.android.app.utils.KeyboardUtils
import com.android.app.view.adapter.Adapter
import com.android.app.view.adapter.AdapterMessage
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.activity_chat.imgChat
import kotlinx.android.synthetic.main.item_chat.*
import java.util.*
import kotlin.collections.ArrayList

class ChatActivity : AppCompatActivity(), Adapter.Actions, IMessagesContract.View {
    private val TAG = javaClass.simpleName

    internal lateinit var iPresenter: IMessagesContract.Presenter

    lateinit var adapter: AdapterMessage
    lateinit var chat: Chat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        chat = intent.getSerializableExtra("chat") as Chat

        adapter = AdapterMessage(ArrayList<Message>(), this, this)
        adapter.chat = chat
        val layout = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerMensagens.layoutManager = layout
        recyclerMensagens.adapter = adapter

        iPresenter = MessagePresenter(this)

        KeyboardUtils.addKeyboardToggleListener(this) { isVisible ->
            recyclerMensagens.smoothScrollToPosition(adapter.itemCount)
        }

        toolbar.setOnClickListener(View.OnClickListener {
            val intent = Intent(getApplicationContext(), ChatConfigActivity::class.java)
            intent.putExtra("chat", chat)
            startActivityForResult(intent, REQUEST_LEAVE_CHAT)
        })

        showChatData(chat)
        iPresenter.listMessages(chat)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
//        if (data == null) {
//            return
//        }
        when (resultCode) {
            REQUEST_LEAVE_CHAT -> {
                Log.d(TAG, "REQUEST_LEAVE_CHAT")
                //finish()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (adapter.isItensSelectedEmpty) {
            txtToolbarTitle.setText(chat.nome)
            getMenuInflater().inflate(R.menu.menu_chat, menu)
        } else {
            txtToolbarTitle.setText("" + adapter.itensSelected.size)
            getMenuInflater().inflate(R.menu.menu_message_selected, menu)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_settings -> {
                return true
            }
            R.id.action_copy -> {
                copyMessages()
                return true
            }
            R.id.action_delete -> {
                showDialogDeleteMessages(this, "Apagar", "Deseja realmente apagar " + adapter.itensSelected.size + " mensagens ?")
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun copyMessages() {
        var messagesCopy = ""
        for (message in adapter.itensSelected) {
            messagesCopy += message.message + "\n\n"
        }
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("", messagesCopy)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(getApplicationContext(), getString(R.string.mensagem_copiada), Toast.LENGTH_LONG).show()

        adapter.clearItensSelected()
        invalidateOptionsMenu()
    }

    private fun showDialogDeleteMessages(activity: Activity, title: String, message: String) {
        var alertBuilder = AlertDialog.Builder(activity)
        alertBuilder.setTitle(title)
        alertBuilder.setMessage(message)
        alertBuilder.setPositiveButton(activity.getString(R.string.sim)) { dialog, which ->
            deleteMessages()
            dialog.dismiss()
        }
        alertBuilder.setNegativeButton(activity.getString(R.string.cancelar)) { dialog, which ->
            dialog.cancel()
            adapter.clearItensSelected()
            invalidateOptionsMenu()
        }
        val alert = alertBuilder.create()
        alert.setCanceledOnTouchOutside(false)
        alert.show()
    }

    private fun deleteMessages() {
        adapter.itensList.removeAll(adapter.itensSelected)
        iPresenter.HideMessages(adapter.itensSelected)

        adapter.clearItensSelected()

        if (adapter.isEmpty) {
            txtSemMensagens.visibility = View.VISIBLE
        }

        invalidateOptionsMenu()
    }

    private fun showChatData(chat: Chat) {
        txtToolbarTitle.setText(chat.nome)
        ImageUtils(this).picassoImage(imgChat, chat.avatarURL)
    }

    private fun addMessage(message: Message) {
        txtSemMensagens.visibility = View.INVISIBLE
        adapter.add(message)
        recyclerMensagens.smoothScrollToPosition(adapter.itemCount)
    }

    override fun onClickItem(view: View) {
        invalidateOptionsMenu()
    }

    override fun onLongClickItem(view: View) {
        invalidateOptionsMenu()
    }

    fun sendMessage(view: View) {
        if (edtMessage.text.isEmpty()) {
            return
        }

        val message = Message(edtMessage.text.toString(), Message.Tipo.TEXT, Date(),true)

        message.idChat = chat.id
        message.hide = false
        message.remetenteID = UserSingleton.instance.uID
        message.remetenteNome = UserSingleton.instance.name

        addMessage(message)
        edtMessage.text.clear()
        iPresenter.sendMessage(message)
    }

    override fun showProgress() {
        layoutCarregando.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        layoutCarregando.visibility = View.INVISIBLE
    }

    fun checkIsListEmpty() {
        if (adapter.isEmpty) {
            txtSemMensagens.visibility = View.VISIBLE
        } else {
            txtSemMensagens.visibility = View.INVISIBLE
            recyclerMensagens.smoothScrollToPosition(adapter.itemCount)
        }
    }

    override fun onHideSuccess(list: List<Message>) {
        adapter.itensList.removeAll(list)
        adapter.notifyDataSetChanged()
        checkIsListEmpty()
    }

    override fun onListSuccess(list: List<Message>) {
        adapter.itensList.addAll(list)
        adapter.notifyDataSetChanged()
        checkIsListEmpty()
    }

    override fun onFailure(message: String) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show()
    }

    companion object {
        val REQUEST_LEAVE_CHAT = 1
    }

}
