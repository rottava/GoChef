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
    private String pushId;
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

    public String getRecipeName() {
        return recipeName;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public String getImageUrl() {
        return  imageUrl;
    }

    public String getAttributes() {
        return attributes;
    }

    public String getSource() {
        return source;
    }

    public String getId() { return id; }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public void setRecipeName(String recipeName){
        this.recipeName = recipeName;
    }

    public void setIngredients(List<String> ingredients){
        this.ingredients = ingredients;
    }

    public void setImageUrl(String imageUrl){
        this.imageUrl = imageUrl;
    }

    public void setAttributes(String rating){
        this.attributes = rating;
    }

    public void setId(String id){
        this.id = id;
    }

    public void setSource(String source){
        this.source = source;
    }

    public void setSteps(List<String> steps){
        this.steps = steps;
    }

}