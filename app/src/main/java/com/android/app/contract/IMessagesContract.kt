package com.android.app.contract

import android.app.Activity
import com.android.app.data.model.Chat
import com.android.app.data.model.Message

interface IMessagesContract {

    interface View {
        fun showProgress()
        fun hideProgress()
        fun onFailure(message : String)

        fun onHideSuccess(list: List<Message>)
        fun onListSuccess(list: List<Message>)
    }

    interface Listener {
        fun onFailure(message: String)

        fun onHideSuccess(list: List<Message>)
        fun onListSuccess(list: List<Message>)
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

}