package com.android.app.data.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.firestore.Exclude;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Message {
    @SerializedName("_id")
    public String id;
    @SerializedName("idChat")
    public String idChat;
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
    @SerializedName("remetente")
    public String remetente;

    @Exclude
    public boolean enviado = false;

    public Message() {
        //Firebase
    }

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

    @Override
    public boolean equals(Object obj) {
        return ((Message) obj).id.equals(id);
    }

    @Override
    public String toString() {
        return "Message"
                + "\nid " + id
                + "\nidChat " + idChat
                + "\ntipo " + tipo
                + "\nmessage " + message
                + "\nhide " + hide
                + "\nsendDate " + sendDate
                + "\nmediaURL " + mediaURL
                + "\nremetente " + remetente;
    }
}
