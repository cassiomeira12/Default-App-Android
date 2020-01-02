package com.android.app.contract

import com.android.app.data.model.BaseUser
import com.android.app.data.model.Chat

interface IChatContract {

    interface View {
        fun showProgress()
        fun hideProgress()
        fun onFailure(message : String)

        fun onListSuccess(list: List<BaseUser>)
        fun onAddUserSuccess(user: BaseUser)
        fun onRemoveUserSuccess(user: BaseUser)
        fun onAddAdministratorSuccess(user: BaseUser)
        fun onRemoveAdministratorSuccess(user: BaseUser)
        fun onChangeNameSuccess(name: String)
        fun onChangeImgURLSuccess(imgURL: String)
        fun onChangeDescriptionSuccess(description: String)
        fun onLeaveSuccess(user: BaseUser)
    }

    interface Listener {
        fun onFailure(message: String)

        fun onListSuccess(list: List<BaseUser>)
        fun onAddUserSuccess(user: BaseUser)
        fun onRemoveUserSuccess(user: BaseUser)
        fun onAddAdministratorSuccess(user: BaseUser)
        fun onRemoveAdministratorSuccess(user: BaseUser)
        fun onChangeNameSuccess(name: String)
        fun onChangeImgURLSuccess(imgURL: String)
        fun onChangeDescriptionSuccess(description: String)
        fun onLeaveSuccess(user: BaseUser)
    }

    interface Presenter {
        fun onDestroy()

        fun listUsers(chat: Chat)
        fun addUser(chat: Chat, user: BaseUser)
        fun removeUser(chat: Chat, user: BaseUser)
        fun addAdministrator(chat: Chat, user: BaseUser)
        fun removeAdministrator(chat: Chat, user: BaseUser)
        fun changeName(chat: Chat, name: String)
        fun changeImgURL(chat: Chat, imgURL: String)
        fun changeDescription(chat: Chat, description: String)
        fun leaveChat(chat: Chat)
    }

    interface Service {
        fun listUsers(chat: Chat)
        fun addUser(chat: Chat, user: BaseUser)
        fun addAdministrator(chat: Chat, user: BaseUser)
        fun removeAdministrator(chat: Chat, user: BaseUser)
        fun changeName(chat: Chat, name: String)
        fun changeImgURL(chat: Chat, imgURL: String)
        fun changeDescription(chat: Chat, description: String)
        fun removeUser(chat: Chat, user: BaseUser)
        fun leaveChat(chat: Chat)
    }

}