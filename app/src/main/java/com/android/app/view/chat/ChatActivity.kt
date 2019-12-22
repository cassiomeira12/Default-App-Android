package com.android.app.view.chat

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.app.R
import com.android.app.data.model.Message
import com.android.app.utils.KeyboardUtils
import com.android.app.view.adapter.Adapter
import com.android.app.view.adapter.AdapterMessage
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.activity_chats.*
import java.util.*
import kotlin.collections.ArrayList


class ChatActivity : AppCompatActivity(), Adapter.Actions {
    private val TAG = javaClass.simpleName

    lateinit var adapter: AdapterMessage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        adapter = AdapterMessage(ArrayList<Message>(), this, this)
        val layout = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerMensagens.layoutManager = layout
        recyclerMensagens.adapter = adapter

//        if (!adapter.isEmpty()) {
//            txtSemMensagens.visibility = View.GONE
//        }

        KeyboardUtils.addKeyboardToggleListener(this) { isVisible ->
            recyclerMensagens.smoothScrollToPosition(adapter.itemCount)
        }
    }

    override fun onResume() {
        super.onResume()
        carregarMensagens()
    }

    fun carregarMensagens() {
        recyclerMensagens.visibility = View.INVISIBLE
        layoutCarregando.visibility = View.VISIBLE
        Handler().postDelayed(Runnable {
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
        }, 2000)
    }

    override fun onClickItem(view: View) {

    }

    override fun onLongClickItem(view: View) {

    }
}