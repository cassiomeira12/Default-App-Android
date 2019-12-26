package com.android.app.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Chat implements Serializable {
    @SerializedName("_id")
    public String id;
    @SerializedName("avatarURL")
    public String avatarURL;
    @SerializedName("createdBy")
    public String createdBy;
    @SerializedName("createAt")
    public Date createdAt;
    @SerializedName("updateAt")
    public Date updatedAt;
    @SerializedName("nome")
    public String nome;
    @SerializedName("descricao")
    public String descricao;
    @SerializedName("criptografia")
    public String criptografia;
    public Map<String, String> administradores = new HashMap<>();

    public Chat() {
        //Firebase
    }

    public Chat(String nome, Date updatedAt) {
        this.nome = nome;
        this.updatedAt = updatedAt;
    }
}
