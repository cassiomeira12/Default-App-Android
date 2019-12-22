package com.android.app.data.model;

import java.util.Date;

public class Chat {
    public String id;
    public String avatarURL;
    public String createdBy;
    public Date createdAt;
    public Date updatedAt;
    public String nome;
    public String descricao;
    public String criptografia;

    public Chat(String nome) {
        this.nome = nome;
    }
}
