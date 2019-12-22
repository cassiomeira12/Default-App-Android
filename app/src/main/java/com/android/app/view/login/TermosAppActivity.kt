package com.android.app.view.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.app.R
import kotlinx.android.synthetic.main.activity_termos_uso.*

class TermosAppActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_termos_uso)

        val value = intent.getIntExtra(TERMO, 0)

        when (value) {
            TERMO_DE_USO -> {
                setTitle(resources.getString(R.string.termos_de_uso))
                txtTermos.setText(resources.getString(R.string.texto_termos_de_uso))
            }
            POLITICA_DE_PRIVACIDADE -> {
                setTitle(resources.getString(R.string.politica_de_privacidade))
                txtTermos.setText(resources.getString(R.string.texto_politica_de_privacidade))
            }
            POLITICA_DE_DADOS -> {
                setTitle(resources.getString(R.string.politica_de_dados))
                txtTermos.setText(resources.getString(R.string.texto_politica_de_dados))
            }
            else -> {
                setTitle(resources.getString(R.string.termos_de_uso))
                txtTermos.setText(resources.getString(R.string.texto_termos_de_uso))
            }
        }

    }

    companion object {
        val TERMO = "termo"
        val TERMO_DE_USO = 0
        val POLITICA_DE_PRIVACIDADE = 1
        val POLITICA_DE_DADOS = 2
    }

}