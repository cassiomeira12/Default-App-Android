package com.android.app.presenter.chat

import android.content.Context
import com.android.app.contract.IChatsContract
import com.android.app.data.model.Chat
import com.android.app.data.services.chat.FirebaseChatsService

class ChatsPresenter (view: IChatsContract.View) : IChatsContract.Presenter, IChatsContract.Listener {
    var view : IChatsContract.View? = view
    var service: IChatsContract.Service = FirebaseChatsService(this)

    override fun onDestroy() {
        this.view = null
    }

    override fun createChat(context: Context, chat: Chat) {
        view!!.showProgress()
        service.createChat(context, chat)
    }

    override fun removeChat(chat: Chat) {
        view!!.showProgress()
        service.removeChat(chat)
    }

    override fun listChats() {
        view!!.showProgress()
        service.listChats()
    }

    override fun onCreatedSuccess(chat: Chat) {
        view!!.hideProgress()
        view!!.onCreatedSuccess(chat)
    }

    override fun onRemovedSuccess(chat: Chat) {
        view!!.hideProgress()
        view!!.onRemovedSucess(chat)
    }

    override fun onListSuccess(list: List<Chat>) {
        view!!.hideProgress()
        view!!.onListSuccess(list)
    }

    override fun onFailure(message: String) {
        view!!.hideProgress()
        view!!.onFailure(message)
    }
}