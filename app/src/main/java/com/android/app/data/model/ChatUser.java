package com.android.app.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class ChatUser implements Serializable {
    @SerializedName("_id")
    public String id;
    @SerializedName("name")
    public String name;
    @SerializedName("avatarURL")
    public String avatarURL;
    @SerializedName("joinAt")
    public Date jointAt;
    @SerializedName("seeMessageAfter")
    public Date seeMessageAfter;
}
