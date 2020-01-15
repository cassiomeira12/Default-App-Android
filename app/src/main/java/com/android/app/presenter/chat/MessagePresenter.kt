package com.android.app.presenter.chat

import com.android.app.contract.IMessagesContract
import com.android.app.data.model.Chat
import com.android.app.data.model.Message
import com.android.app.data.services.chat.FirebaseMessageService

class MessagePresenter(view: IMessagesContract.View) : IMessagesContract.Presenter, IMessagesContract.Listener {
    var view : IMessagesContract.View? = view
    var service: IMessagesContract.Service = FirebaseMessageService(this)

    override fun onDestroy() {
        this.view = null
    }

    override fun sendMessage(message: Message) {
        service.sendMessage(message)
    }

    override fun HideMessages(list: List<Message>) {
        service.HideMessages(list)
    }

    override fun listMessages(chat: Chat) {
        view?.showProgress()
        service.listMessages(chat)
    }

    override fun onHideSuccess(list: List<Message>) {
        view?.onHideSuccess(list)
    }

    override fun onListSuccess(list: List<Message>) {
        //view?.hideProgress()
        view?.onListSuccess(list)
    }

    override fun onFailure(message: String) {
        view?.hideProgress()
        view?.onFailure(message)
    }

}