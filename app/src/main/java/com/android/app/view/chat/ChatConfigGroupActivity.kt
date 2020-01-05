package com.android.app.view.chat

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.app.R
import com.android.app.contract.IChatContract
import com.android.app.data.UserSingleton
import com.android.app.data.model.BaseUser
import com.android.app.data.model.Chat
import com.android.app.presenter.chat.ChatPresenter
import com.android.app.utils.ImageUtils
import com.android.app.utils.UserService
import com.android.app.view.adapter.Adapter
import com.android.app.view.adapter.AdapterUser
import kotlinx.android.synthetic.main.activity_chat_group_config.*
import kotlinx.android.synthetic.main.content_chat_group_config.*

class ChatConfigGroupActivity : AppCompatActivity(), Adapter.Actions, IChatContract.View {
    private val TAG = javaClass.simpleName

    internal lateinit var iPresenter: IChatContract.Presenter

    private lateinit var adapter: AdapterUser
    private lateinit var chat: Chat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_group_config)
        setSupportActionBar(toolbar)

        chat = intent.getSerializableExtra("chat") as Chat

        adapter = AdapterUser(ArrayList(), this, this)
        adapter.chat = chat
        val layout = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerUsers.layoutManager = layout
        recyclerUsers.adapter = adapter

        iPresenter = ChatPresenter(this)

        showChatData(chat)
        listUsers()
    }

    private fun showChatData(chat: Chat) {
        toolbarLayout.setTitle(chat.nome)
        txtChatName.setText(chat.nome)
        txtDescription.setText(chat.descricao)
        ImageUtils(this).picassoImageUser(chat.id, imgChat, chat.avatarURL, progressBar)
    }

    private fun listUsers() {
        iPresenter.listUsers(chat)
    }

    override fun onLongClickItem(view: View?) {

    }

    override fun onClickItem(view: View?) {

    }

    override fun showProgress() {

    }

    override fun hideProgress() {

    }

    override fun onFailure(message: String) {

    }

    override fun onListSuccess(list: List<BaseUser>) {
        adapter.itensList.addAll(list)
        adapter.notifyDataSetChanged()
    }

    override fun onAddUserSuccess(user: BaseUser) {

    }

    override fun onRemoveUserSuccess(user: BaseUser) {

    }

    override fun onAddAdministratorSuccess(user: BaseUser) {

    }

    override fun onRemoveAdministratorSuccess(user: BaseUser) {

    }

    override fun onChangeNameSuccess(name: String) {

    }

    override fun onChangeImgURLSuccess(imgURL: String) {

    }

    override fun onChangeDescriptionSuccess(description: String) {

    }

    override fun onLeaveSuccess(chat: Chat) {
        Toast.makeText(getApplicationContext(), "Removido com sucesso!", Toast.LENGTH_LONG).show()
        this.chat = chat
        listUsers()
    }

    fun leave(view: View) {
        val user = UserSingleton.instance
        iPresenter.leaveChat(chat, user)
        setResult(ChatActivity.REQUEST_LEAVE_CHAT)
        finish()
    }

    fun onChangeName(view: View) {

    }

    fun onChangeDescription(view: View) {

    }

}
