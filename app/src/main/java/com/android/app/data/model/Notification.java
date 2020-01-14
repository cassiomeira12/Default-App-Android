package com.android.app.data.model;

import java.io.Serializable;
import java.util.Date;

public class Notification implements Serializable {

    public String id;
    public String idDestinatario;
    public String token;
    public String title;
    public String message;
    public String topic;
    public Tipo type;
    public Date date;
    public String avatarURL;
    public boolean lida = true;

    public Notification() {
        type = Tipo.TEXT;
    }

    public Notification(String message, Tipo type) {
        this.message = message;
        this.type = type;
    }

    public enum Tipo {
        TEXT,
        TEXT_IMG,
        USER_SEGUIR
    }

}
