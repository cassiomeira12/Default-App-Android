package com.android.app.data.model;

import java.io.Serializable;
import java.util.Date;

public class Notification implements Serializable {

    public String id;
    public String idDestinatario;
    public String titulo;
    public String mensagem;
    public String topico;
    public Tipo tipo;
    public Date date;
    public String avatarURL;
    public boolean lida = true;

    public Notification() {
        tipo = Tipo.TEXT;
    }

    public Notification(String mensagem, Tipo tipo) {
        this.mensagem = mensagem;
        this.tipo = tipo;
    }

    public enum Tipo {
        TEXT,
        TEXT_IMG,
        USER_SEGUIR
    }

}
