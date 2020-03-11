package com.android.app.view.chat

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.app.R
import com.android.app.contract.IChatsContract
import com.android.app.data.UserSingleton
import com.android.app.data.model.BaseUser
import com.android.app.data.model.Chat
import com.android.app.presenter.chat.ChatsPresenter
import com.android.app.view.SearchComponent
import com.android.app.view.adapter.Adapter
import com.android.app.view.adapter.AdapterChat
import com.android.app.view.adapter.AdapterUser2
import kotlinx.android.synthetic.main.fragment_chats.*
import java.util.*
import kotlin.collections.ArrayList

class ChatsFragment: Fragment(), Adapter.Actions, IChatsContract.View, View.OnClickListener {
    private val TAG = javaClass.simpleName

    internal lateinit var iPresenter: IChatsContract.Presenter
    lateinit var adapter: AdapterChat

    companion object {
        var listItens = ArrayList<Chat>()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_chats, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configFragmentAppBar()
        configAdapter()

        iPresenter = ChatsPresenter(this)

        fab.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        if (adapter.isEmpty) {
            iPresenter.listChats()
        }
    }

    override fun onDestroyView() {
        iPresenter.onDestroy()
        super.onDestroyView()
    }

    private fun configFragmentAppBar() {
        (getActivity() as AppCompatActivity).setSupportActionBar(toolbar)
        setHasOptionsMenu(true)
        getActivity()!!.invalidateOptionsMenu()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_chat, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_add_user -> {

                return true
            }
            R.id.action_settings -> {

                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun configAdapter() {
        adapter = AdapterChat(listItens, getContext()!!, this)
        val layout = LinearLayoutManager(getContext()!!, LinearLayoutManager.VERTICAL, false)
        recyclerChats.layoutManager = layout
        recyclerChats.adapter = adapter
    }

    override fun onClick(view: View?) {
        val chat = Chat("Grupo", Date())
        val userID = UserSingleton.instance.uID
        chat.createdBy = userID
        chat.administradores.put(userID, userID)
        chat.users.put(userID, userID)
        chat.descricao = "Descricao do grupo"
        //iPresenter.createChat(getContext()!!, chat)
    }

    override fun onLongClickItem(view: View?) {

    }

    override fun onClickItem(view: View?) {
        if (adapter.itensSelected.isEmpty() && adapter.objectSelected != null) {
            val intent = Intent(getContext(), ChatActivity::class.java)
            intent.putExtra("chat", adapter.objectSelected)
            startActivity(intent)
        }
    }

    override fun showProgress() {
        layoutCarregando.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        layoutCarregando.visibility = View.INVISIBLE
    }

    override fun onCreatedSuccess(chat: Chat) {
        adapter.add(chat)
    }

    override fun onRemovedSucess(chat: Chat) {
        adapter.remove(chat)
    }

    override fun onListSuccess(list: List<Chat>) {
        listItens.addAll(list.sortedWith(compareBy({ it.updatedAt.time })).reversed())
        //adapter.itensList.addAll(list.sortedWith(compareBy({ it.updatedAt.time })).reversed())
        //adapter.itensList.addAll(list)
        adapter.notifyDataSetChanged()
        if (adapter.isEmpty) {
            txtSemChats.visibility = View.VISIBLE
        } else {
            txtSemChats.visibility = View.INVISIBLE
        }
    }

    override fun onFailure(message: String) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show()
    }

}