package com.android.app.data.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class BaseUser implements Serializable {
    @SerializedName("_id")
    public String uID;
    @SerializedName("notification_token")
    public String notificationToken;
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

    public void setUser(BaseUser user) {
        this.uID = user.uID;
        this.notificationToken = user.notificationToken;
        this.avatarURL = user.avatarURL;
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
                + "\nnotificationToken ".concat(notificationToken == null ? "null" : notificationToken)
                + "\navatarURL ".concat(avatarURL == null ? "null" : avatarURL)
                + "\nstatus ".concat(status.toString())
                + "\nname ".concat(name)
                + "\nemail ".concat(email)
                + "\nemailVerified ".concat(String.valueOf(emailVerified))
                + "\ncreateAt ".concat(createAt.toString())
                + "\nupdateAt ".concat(updateAt.toString())
                + "\nupdateAt ".concat(updateAt.toString());
    }
}
