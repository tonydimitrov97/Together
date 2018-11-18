
package com.example.together.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("activeEvent")
    @Expose
    private int activeEvent;

    /**
     * No args constructor for use in serialization
     *
     */
    public User() {
    }

    /**
     *
     * @param id
     * @param email
     * @param name
     * @param activeEvent
     * @param password
     */
    public User(Integer id, String name, String password, String email, int activeEvent) {
        super();
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.activeEvent = activeEvent;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getActiveEvent() {
        return activeEvent;
    }

    public void setActiveEvent(int activeEvent) {
        this.activeEvent = activeEvent;
    }

}
