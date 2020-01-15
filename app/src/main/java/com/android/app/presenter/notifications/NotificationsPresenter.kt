package com.android.app.presenter.notifications

import com.android.app.contract.INotificationsContract
import com.android.app.data.model.Notification
import com.android.app.data.services.notifications.FirebaseNotificationsService

class NotificationsPresenter(view: INotificationsContract.View) : INotificationsContract.Presenter, INotificationsContract.Listener {
    var view : INotificationsContract.View? = view
    var service: INotificationsContract.Service = FirebaseNotificationsService(this)

    override fun onDestroy() {
        this.view = null
    }

    override fun listNotifications() {
        view?.showProgress()
        service.listNotifications()
    }

    override fun setReadNotification(notification: Notification) {
        service.setReadNotification(notification)
    }

    override fun onFailure(message: String) {
        view?.hideProgress()
        view?.onFailure(message)
    }

    override fun onListSuccess(list: List<Notification>) {
        view?.hideProgress()
        view?.onListSuccess(list)
    }

}