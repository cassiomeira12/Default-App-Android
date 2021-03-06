package com.android.app.contract

import android.app.Activity
import android.content.Context
import com.android.app.data.model.Chat

interface IChatsContract {

    interface View {
        fun showProgress()
        fun hideProgress()
        fun onFailure(message : String)

        fun onCreatedSuccess(chat: Chat)
        fun onRemovedSucess(chat: Chat)
        fun onListSuccess(list: List<Chat>)
    }

    interface Listener {
        fun onFailure(message: String)

        fun onCreatedSuccess(chat: Chat)
        fun onRemovedSuccess(chat: Chat)
        fun onListSuccess(list: List<Chat>)
    }

    interface Presenter {
        fun onDestroy()

        fun createChat(context: Context, chat: Chat)
        fun removeChat(chat: Chat)
        fun listChats()
    }

    interface Service {
        fun createChat(context: Context, chat: Chat)
        fun removeChat(chat: Chat)
        fun listChats()
    }

}