package org.vansoft.karmanyak.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.nio.file.Watchable;

public class LoginResponse {

    @SerializedName("name")
    @Expose
    String name;
    @SerializedName("email")
    @Expose
    String email;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public User getUser(String name, String email) {
//        return new User( name,  email,  tokens,  joinedDate,  walletAddress);
        return new User(name,email);
    }


}
