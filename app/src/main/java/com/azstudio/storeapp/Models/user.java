package com.azstudio.storeapp.Models;

import com.google.gson.annotations.SerializedName;

public class user {
    @SerializedName("user_name")
    private String user_name;
    @SerializedName("user_email")
    private String user_email;
    @SerializedName("user_password")
    private String user_password;
    @SerializedName("user_role")
    private String user_role;

    public user() {
    }

    public user(String user_name, String email, String user_password, String user_role) {
        this.user_name = user_name;
        this.user_email = email;
        this.user_password = user_password;
        this.user_role = user_role;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getEmail() {
        return user_email;
    }

    public void setEmail(String email) {
        this.user_email = email;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getUser_role() {
        return user_role;
    }

    public void setUser_role(String user_role) {
        this.user_role = user_role;
    }
}
