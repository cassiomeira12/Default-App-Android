package com.android.app.contract

import android.app.Activity
import com.android.app.data.model.Chat

interface IChatsContract {

    interface View {
        fun showProgress()
        fun hideProgress()
        fun onCreatedSuccess(chat: Chat)
        fun onRemovedSucess(chat: Chat)
        fun onListSuccess(list: List<Chat>)
        fun onFailure(message : String)
    }

    interface Presenter {
        fun onDestroy()
        fun createChat(activity: Activity, chat: Chat)
        fun removeChat(chat: Chat)
        fun listChats()
    }

    interface Service {
        fun createChat(activity: Activity, chat: Chat)
        fun removeChat(chat: Chat)
        fun listChats()
    }

    interface Listener {
        fun onCreatedSuccess(chat: Chat)
        fun onRemovedSuccess(chat: Chat)
        fun onListSuccess(list: List<Chat>)
        fun onFailure(message: String)
    }

}