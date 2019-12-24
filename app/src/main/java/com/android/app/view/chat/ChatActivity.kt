package com.android.app.view.chat

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.app.R
import com.android.app.data.model.Chat
import com.android.app.data.model.Message
import com.android.app.utils.KeyboardUtils
import com.android.app.view.adapter.Adapter
import com.android.app.view.adapter.AdapterMessage
import kotlinx.android.synthetic.main.activity_chat.*
import java.util.*
import kotlin.collections.ArrayList

class ChatActivity : AppCompatActivity(), Adapter.Actions {
    private val TAG = javaClass.simpleName

    lateinit var adapter: AdapterMessage
    lateinit var chat: Chat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        chat = intent.getSerializableExtra("chat") as Chat

        adapter = AdapterMessage(ArrayList<Message>(), this, this)
        val layout = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerMensagens.layoutManager = layout
        recyclerMensagens.adapter = adapter

        supportNaviagteUp()

        KeyboardUtils.addKeyboardToggleListener(this) { isVisible ->
            recyclerMensagens.smoothScrollToPosition(adapter.itemCount)
        }

        showChatData(chat)
        carregarMensagens(chat)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun supportNaviagteUp() {
        setSupportActionBar(toolbar)
        //getSupportActionBar()!!.setDisplayShowHomeEnabled(true)
        //getSupportActionBar()!!.setHomeButtonEnabled(true)
        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()!!.setDisplayShowTitleEnabled(false)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        getMenuInflater().inflate(R.menu.menu_chat, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_settings -> {

                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showChatData(chat: Chat) {
        txtToolbarTitle.setText(chat.nome)
    }

    fun carregarMensagens(chat: Chat) {
        recyclerMensagens.visibility = View.INVISIBLE
        layoutCarregando.visibility = View.VISIBLE
        Handler().postDelayed(Runnable {

            if (chat.nome.equals("Frei")) {
                layoutCarregando.visibility = View.GONE
                recyclerMensagens.visibility = View.VISIBLE
                txtSemMensagens.visibility = View.VISIBLE
            } else {
                adapter.add(Message("Obrigada Cel \uD83D\uDE18\uD83D\uDE18", Message.Tipo.TEXT, Date()))
                adapter.add(Message("E da especial @Celina Computação ?", Message.Tipo.TEXT, Date(), true))
                adapter.add(Message("Tb ta dando problema pq Catia não fechou as notas", Message.Tipo.TEXT, Date()))
                adapter.add(Message("Queria saber a nota da final de lfa tbm", Message.Tipo.TEXT, Date()))
                adapter.add(Message("Ai da n ta no sistema \uD83D\uDE42", Message.Tipo.TEXT, Date(), true))
                adapter.add(Message("\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02", Message.Tipo.TEXT, Date()))
                adapter.add(Message("Confirmação de matrícula já disponível", Message.Tipo.TEXT, Date()))
                adapter.add(Message("Cátia ainda não lançou as notas de ap1 e ap2 não apareceu, vai ter como adicionar depois?", Message.Tipo.TEXT, Date()))
                adapter.add(Message("No dia da presencial tu pode pedir para adicionar", Message.Tipo.TEXT, Date()))
                adapter.add(Message("Bom dia povo. Alguém sabe de algum apartamento alugando no candeias?", Message.Tipo.TEXT, Date()))
                adapter.add(Message("Cátia não colocou as notas de ap1 no sagres\n" +
                        "A matrícula de ap2 então será só no primeiro dia de aula?", Message.Tipo.TEXT, Date(), true))
                adapter.add(Message("Obrigada Cel \uD83D\uDE18\uD83D\uDE18", Message.Tipo.TEXT, Date()))
                adapter.add(Message("E da especial @Celina Computação ?", Message.Tipo.TEXT, Date(), true))
                adapter.add(Message("Tb ta dando problema pq Catia não fechou as notas", Message.Tipo.TEXT, Date()))
                adapter.add(Message("Queria saber a nota da final de lfa tbm", Message.Tipo.TEXT, Date()))
                adapter.add(Message("Ai da n ta no sistema \uD83D\uDE42", Message.Tipo.TEXT, Date(), true))
                adapter.add(Message("\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02", Message.Tipo.TEXT, Date()))

                layoutCarregando.visibility = View.GONE
                recyclerMensagens.visibility = View.VISIBLE
                recyclerMensagens.smoothScrollToPosition(adapter.itemCount)
            }

        }, 2000)
    }

    override fun onClickItem(view: View) {

    }

    override fun onLongClickItem(view: View) {

    }

    fun onAppBarClick(view: View) {
        startActivity(Intent(getApplicationContext(), ChatConfigActivity::class.java))
    }
}
