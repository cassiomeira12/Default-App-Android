package com.android.app.data.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class BaseUser implements Serializable {
    @SerializedName("_id")
    public String uID;
    @SerializedName("avatarURL")
    public String avatarURL;
    @SerializedName("status")
    public Status status;
    @SerializedName("name")
    public String name;
    @SerializedName("email")
    public String email;
    @SerializedName("emailVerified")
    public boolean emailVerified;
    @SerializedName("password")
    public String password;
    @SerializedName("createAt")
    public Date createAt;
    @SerializedName("updateAt")
    public Date updateAt;
    @SerializedName("online")
    public Date online;

    public BaseUser() {
        //Firebase
    }

    public BaseUser(String uID, Status status, String name, String email, Boolean emailVerified, Date createAt, Date updateAt, Date online) {
        this.uID = uID;
        this.status = status;
        this.name = name;
        this.email = email;
        this.emailVerified = emailVerified;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.online = online;
    }

    public void setUser(BaseUser user) {
        this.uID = user.uID;
        this.status = user.status;
        this.name = user.name;
        this.email = user.email;
        this.emailVerified = user.emailVerified;
        this.createAt = user.createAt;
        this.updateAt = user.updateAt;
        this.online = user.online;
    }

    @NonNull
    @Override
    public String toString() {
        return "BaseUser"
                + "\nuID ".concat(uID)
                + "\nstatus ".concat(status.toString())
                + "\nname ".concat(name)
                + "\nemail ".concat(email)
                + "\nemailVerified ".concat(String.valueOf(emailVerified))
                + "\ncreateAt ".concat(createAt.toString())
                + "\nupdateAt ".concat(updateAt.toString())
                + "\nupdateAt ".concat(updateAt.toString());
    }
}
