package com.jr.gochef;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {

    private String name;
    private String email;
    private String id;
    private String image;
    private List<Recipe> favorites;

    User() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    String getEmail() {
        return email;
    }


    void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    String getImage() {
        return image;
    }

    void setImage(String image) {
        this.image = image;
    }

    List<Recipe> getFavorites() {
        return favorites;
    }

    void setFavorites(List<Recipe> favorites) {
        this.favorites = favorites;
    }

}
