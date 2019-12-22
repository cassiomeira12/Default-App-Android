package com.android.app.data.model;

import java.io.Serializable;
import java.util.Date;

public class Chat implements Serializable {
    public String id;
    public String avatarURL;
    public String createdBy;
    public Date createdAt;
    public Date updatedAt;
    public String nome;
    public String descricao;
    public String criptografia;

    public Chat(String nome, Date updatedAt) {
        this.nome = nome;
        this.updatedAt = updatedAt;
    }
}
