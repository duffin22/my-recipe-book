package com.hfad.myrecipebook;

import java.util.ArrayList;

/**
 * Created by matthewtduffin on 11/07/16.
 */
public class Recipe {
    Integer picture;
    String title, category;
    int rating;
    ArrayList<String> ingredients;

    public Recipe(String title, Integer picture, int rating, String category, ArrayList<String> ingredients) {
        this.title=title;
        this.picture=picture;
        this.category=category;
        this.rating=rating;
        this.ingredients=ingredients;
    }

}
