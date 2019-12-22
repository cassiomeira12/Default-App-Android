package com.android.app.data.services.login

import android.app.Activity
import android.util.Log
import com.android.app.contract.ICreateAccountContract
import com.android.app.data.model.BaseUser
import com.android.app.utils.RetrofitInitializer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RestCreateAccountService (var listener : ICreateAccountContract.Listener) : ICreateAccountContract.Service {
    val TAG = this::class.java.canonicalName

    override fun register(activity: Activity, user: BaseUser, login: String, password: String) {
        val call = RetrofitInitializer().users().criar(user)
        call.enqueue(object : Callback<BaseUser> {
            override fun onFailure(call: Call<BaseUser>, t: Throwable) {
                Log.e(TAG, t.message.toString())
                listener.onFailure(t.message.toString())
            }

            override fun onResponse(call: Call<BaseUser>, response: Response<BaseUser>) {
                Log.d(TAG, response.toString())
                Log.d(TAG, response.body().toString())
                val user = response.body()
                if (user == null) {
                    listener.onFailure(response?.body().toString())
                    return
                }
                listener.onCreatedSuccess(user)
            }
        })
    }

}
