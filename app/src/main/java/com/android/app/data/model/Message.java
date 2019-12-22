package com.android.app.data.model;

import java.util.Date;

public class Message {
    public boolean enviado = false;
    public Tipo tipo;
    public String message;
    public boolean hide;
    public Date sendDate;
    public String mediaURL;

    public Message(String message, Tipo tipo, Date sendDate) {
        this.message = message;
        this.tipo = tipo;
        this.sendDate = sendDate;
    }

    public Message(String message, Tipo tipo, Date sendDate, boolean enviado) {
        this.message = message;
        this.tipo = tipo;
        this.sendDate = sendDate;
        this.enviado = enviado;
    }

    public enum Tipo {
        LEAVE,
        JOIN,
        TEXT,
        PHOTO,
        VIDEO,
        AUDIO,
        ARQUIVO,
        LOCALIZACAO,
        CONTATO
    }

}
