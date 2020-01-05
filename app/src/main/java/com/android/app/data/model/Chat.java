package com.android.app.data.model;

import androidx.annotation.Nullable;

import com.android.app.data.UserSingleton;
import com.google.firebase.firestore.Exclude;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Chat implements Serializable, Comparable<Date> {
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
    @SerializedName("administradores")
    public Map<String, String> administradores = new HashMap<>();
    @SerializedName("users")
    public Map<String, String> users = new HashMap<>();

    @Exclude
    public Date userOnline;

    public Chat() {
        //Firebase
    }

    public Chat(String nome, Date updatedAt) {
        this.nome = nome;
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object obj) {
        return ((Chat) obj).nome.equals(nome);
    }

    @Override
    public int compareTo(Date date) {
        if (updatedAt.getTime() > date.getTime()) {
            return 1;
        } else {
            return 0;
        }
    }
}
