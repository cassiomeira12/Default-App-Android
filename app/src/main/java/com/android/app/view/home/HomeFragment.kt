package com.android.app.view.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.app.R
import com.android.app.data.UserSingleton
import com.android.app.data.model.BaseUser
import com.android.app.data.model.Chat
import com.android.app.view.SearchComponent
import com.android.app.view.adapter.Adapter
import com.android.app.view.adapter.AdapterUser2
import com.android.app.view.chat.ChatActivity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment: Fragment(), Adapter.Actions {
    private val TAG = javaClass.simpleName

    lateinit var adapter: AdapterUser2

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, javaClass.simpleName)

        configAdapter()
        listUsers()

        var search = SearchComponent(getContext()!!, viewSearch, adapter as Adapter<Any>)
    }

    private fun configAdapter() {
        adapter = AdapterUser2(ArrayList<BaseUser>(), getContext()!!, this)
        val l2 = LinearLayoutManager(getContext()!!, LinearLayoutManager.HORIZONTAL, false)
        recyclerUsers.layoutManager = l2
        recyclerUsers.adapter = adapter
    }

    private fun listUsers() {
        val db = FirebaseFirestore.getInstance()
            .collection("users")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    adapter.itensList.addAll(task.result!!.toObjects(BaseUser::class.java))
                    adapter.notifyDataSetChanged()
                }
            }
    }

    override fun onLongClickItem(view: View?) {

    }

    override fun onClickItem(view: View?) {
        val userID = UserSingleton.instance.uID
        val userSelected = adapter.objectSelected

        val db = FirebaseFirestore.getInstance()
            .collection("conversas")
            .whereEqualTo("users.$userID", userID)
            //.orderBy("updatedAt", Query.Direction.DESCENDING)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Itens Chatsss " + task.result!!.size())
                    var chatUser: Chat? = null
                    for (chat in task.result!!.toObjects(Chat::class.java)) {
                        if (chat.users.size == 2) {
                            if (chat.users.containsValue(userSelected.uID)) {
                                Log.d(TAG, "achou")
                                chatUser = chat
                                chatUser.nome = userSelected.name
                                chatUser.avatarURL = userSelected.avatarURL
                                break
                            }
                        }
                    }
                    if (chatUser == null) {
                        Log.d(TAG, "precisa criar")
                        chatUser = Chat()
                        chatUser!!.nome = userSelected.name
                        chatUser!!.avatarURL = userSelected.avatarURL
                        chatUser!!.descricao = "decricao"
                        chatUser!!.createdAt = Date()
                        chatUser!!.updatedAt = Date()
                        chatUser!!.createdBy = userID
                        chatUser!!.users.put(userID, userID)
                        chatUser!!.users.put(userSelected.uID, userSelected.uID)
                        val intent = Intent(getContext(), ChatActivity::class.java)
                        intent.putExtra("chat", chatUser)
                        startActivity(intent)
                    } else {
                        Log.d(TAG, chatUser.toString())
                        val intent = Intent(getContext(), ChatActivity::class.java)
                        intent.putExtra("chat", chatUser)
                        startActivity(intent)
                    }
                }
            }
    }

}