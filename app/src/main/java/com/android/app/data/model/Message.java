package com.android.app.data.model;

import com.google.firebase.firestore.Exclude;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Message {
    @SerializedName("tipo")
    public Tipo tipo;
    @SerializedName("message")
    public String message;
    @SerializedName("hide")
    public boolean hide;
    @SerializedName("sendDate")
    public Date sendDate;
    @SerializedName("mediaURL")
    public String mediaURL;

    @Exclude
    public boolean enviado = false;

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
