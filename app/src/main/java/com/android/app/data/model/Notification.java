package com.android.app.data.model;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Date;

public class Notification implements Serializable {

    public String id;
    public String idDestinatario;
    public String title;
    public String message;
    public String token;
    public String topic;
    public Tipo type;
    public Date date;
    public String avatarURL;
    public boolean lida;

    public Notification() {
        //Firebase
    }

    public Notification(String message, Tipo type) {
        this.message = message;
        this.type = type;
    }

    public enum Tipo {
        TEXT {
            @Override
            public String toString() { return "TEXT"; }
        },
        TEXT_IMG {
            @Override
            public String toString() { return "TEXT_IMG"; }
        },
        USER_SEGUIR {
            @Override
            public String toString() { return "USER_SEGUIR"; }
        }
    }

    @Override
    public String toString() {
        return "Notificarions"
                + "\nid ".concat(id)
                + "\nidDestinatario ".concat(idDestinatario)
                + "\ntitle ".concat(title)
                + "\nmessage ".concat(message)
                + "\ntoken ".concat(token == null ? "null" : token)
                + "\ntopic ".concat(topic == null ? "null" : topic)
                + "\ntype ".concat(type.toString())
                + "\ndate ".concat(date.toString())
                + "\navatarURL ".concat(avatarURL == null ? "null" : avatarURL)
                + "\nlida ".concat(String.valueOf(lida));

    }
}
