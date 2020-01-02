package com.android.app.presenter.chat

import com.android.app.contract.IChatContract
import com.android.app.data.model.BaseUser
import com.android.app.data.model.Chat
import com.android.app.data.services.chat.FirebaseChatService

class ChatPresenter (view: IChatContract.View) : IChatContract.Presenter, IChatContract.Listener {
    var view : IChatContract.View? = view
    var service: IChatContract.Service = FirebaseChatService(this)

    override fun onFailure(message: String) {

    }

    override fun onListSuccess(list: List<BaseUser>) {
        view!!.hideProgress()
        view!!.onListSuccess(list)
    }

    override fun onAddUserSuccess(user: BaseUser) {
        view!!.hideProgress()
        view!!.onAddUserSuccess(user)
    }

    override fun onRemoveUserSuccess(user: BaseUser) {

    }

    override fun onAddAdministratorSuccess(user: BaseUser) {

    }

    override fun onRemoveAdministratorSuccess(user: BaseUser) {

    }

    override fun onChangeNameSuccess(name: String) {
        view!!.hideProgress()
        view!!.onChangeNameSuccess(name)
    }

    override fun onChangeImgURLSuccess(imgURL: String) {
        view!!.hideProgress()
        view!!.onChangeImgURLSuccess(imgURL)
    }

    override fun onChangeDescriptionSuccess(description: String) {
        view!!.hideProgress()
        view!!.onChangeDescriptionSuccess(description)
    }

    override fun onLeaveSuccess(chat: Chat) {
        view!!.hideProgress()
        view!!.onLeaveSuccess(chat)
    }

    override fun onDestroy() {
        this.view = null
    }

    override fun listUsers(chat: Chat) {
        view!!.showProgress()
        service.listUsers(chat)
    }

    override fun addUser(chat: Chat, user: BaseUser) {
        view!!.showProgress()
        service.addUser(chat, user)
    }

    override fun removeUser(chat: Chat, user: BaseUser) {

    }

    override fun addAdministrator(chat: Chat, user: BaseUser) {

    }

    override fun removeAdministrator(chat: Chat, user: BaseUser) {
    }


    override fun changeName(chat: Chat, name: String) {
        view!!.showProgress()
        service.changeName(chat, name)
    }

    override fun changeImgURL(chat: Chat, imgURL: String) {
        view!!.showProgress()
        service.changeImgURL(chat, imgURL)
    }

    override fun changeDescription(chat: Chat, description: String) {
        view!!.showProgress()
        service.changeDescription(chat, description)
    }

    override fun leaveChat(chat: Chat, user: BaseUser) {
        view!!.showProgress()
        service.leaveChat(chat, user)
    }

}