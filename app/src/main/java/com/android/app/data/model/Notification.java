package com.android.app.data.model;

import java.io.Serializable;

public class Notification implements Serializable {

    public String mensagem;
    public Tipo tipo;

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
