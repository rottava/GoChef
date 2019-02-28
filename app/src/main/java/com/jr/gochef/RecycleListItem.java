package com.jr.gochef;

import java.util.ArrayList;
import java.util.List;

public class RecycleListItem {
    private Integer image;
    private String name;
    private List<String> ingredients;
    private List<String> steps;

    public RecycleListItem(){
        image = 0;
        name = "";
        ingredients = new ArrayList<>();
        steps = new ArrayList<>();
    }

    public RecycleListItem(Integer image, String name, List<String> ingredients, List<String> steps){
        this.image = image;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getSteps() {
        return steps;
    }

    public void setSteps(List<String> steps) {
        this.steps = steps;
    }
}
