package com.android.app.contract

import android.app.Activity
import com.android.app.data.model.Chat
import com.android.app.data.model.Message

interface IMessagesContract {

    interface View {
        fun showProgress()
        fun hideProgress()
        fun onHideSuccess(list: List<Message>)
        fun onListSuccess(list: List<Message>)
        fun onFailure(message : String)
    }

    interface Presenter {
        fun onDestroy()
        fun sendMessage(message: Message)
        fun HideMessages(list: List<Message>)
        fun listMessages(chat: Chat)
    }

    interface Service {
        fun sendMessage(message: Message)
        fun HideMessages(list: List<Message>)
        fun listMessages(chat: Chat)
    }

    interface Listener {
        fun onHideSuccess(list: List<Message>)
        fun onListSuccess(list: List<Message>)
        fun onFailure(message: String)
    }

}