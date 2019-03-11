package com.jr.gochef;

import org.parceler.Parcel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Parcel
public class Recipe implements Serializable {
    String recipeName;
    List<String> ingredients = new ArrayList<>();
    List<String> steps = new ArrayList<>();
    String imageUrl;
    String attributes;
    String source;
    String id;
    String index;

    public Recipe() {}

    public Recipe(String recipeName, ArrayList<String> ingredients, String imageUrl, String attributes, String source, String id) {
        this.recipeName = recipeName;
        this.ingredients = ingredients;
        this.imageUrl = imageUrl;
        this.attributes = attributes;
        this.source = source;
        this.id = id;
        this.index = "not_specified";
        this.steps.add("not_specified");
    }

    String getRecipeName() {
        return recipeName;
    }

    List<String> getIngredients() {
        return ingredients;
    }

    String getImageUrl() {
        return  imageUrl;
    }

    String getAttributes() {
        return attributes;
    }

    public String getId() { return id; }

    void setRecipeName(String recipeName){
        this.recipeName = recipeName;
    }

    void setIngredients(List<String> ingredients){
        this.ingredients = ingredients;
    }

    void setImageUrl(String imageUrl){
        this.imageUrl = imageUrl;
    }

    void setAttributes(String attributes){
        this.attributes = attributes;
    }

    public void setId(String id){
        this.id = id;
    }

    List<String> getSteps(){
        return steps;
    }

    void setSteps(List<String> steps){
        this.steps = steps;
    }

}