package com.android.app.data.services.login

import android.app.Activity
import android.util.Log
import com.android.app.contract.ILoginContract
import com.android.app.data.model.BaseUser
import com.android.app.utils.PreferenceUtils2
import com.android.app.utils.RetrofitInitializer
import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RestLoginService (var listener : ILoginContract.Listener) : ILoginContract.Service {
    val TAG = this::class.java.canonicalName

    override fun onLogin(activity: Activity, login: String, password: String) {
        val json = HashMap<String, String>()
        json.put("email", login)
        json.put("password", password)

        val call = RetrofitInitializer().users().login(json)
        call.enqueue(object: Callback<BaseUser> {
            override fun onFailure(call: Call<BaseUser>, t: Throwable) {
                Log.e(TAG, t.toString())
                listener.onFailure("Erro ao realizar login")
            }

            override fun onResponse(call: Call<BaseUser>, response: Response<BaseUser>) {
                Log.d(TAG, response.body().toString())
                Log.d(TAG, response.headers().toString())

                val user = response.body()
                if (user == null) {
                    listener.onFailure("Erro ao realizar login")
                    return
                }

                //Salvando o token de autenticacao
                PreferenceUtils2(activity).setToken(response.headers().get("auth-token")!!)
                listener.onSuccess(user)
            }

        })


    }

}