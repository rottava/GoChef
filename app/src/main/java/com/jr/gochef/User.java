package com.jr.gochef;

import android.net.Uri;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {

    private String name;
    private String email;
    private String id;
    private String image;
    private boolean type;
    private List<Recipe> favorites;

    public User() {}

    public User(String name, String email, String id, String image) {
        this.name = name;
        this.email = email;
        this.id = id;
        this.image = image;
        this.favorites = new ArrayList<>();
    }

    public User(String name, String email, String id, String image, boolean type) {
        this.name = name;
        this.email = email;
        this.id = id;
        this.image = image;
        this.type = type;
        this.favorites = new ArrayList<>();
    }

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Recipe> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<Recipe> favorites) {
        this.favorites = favorites;
    }

    public boolean getType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }
}
