package com.capstoneProject.stepThree;

import java.util.ArrayList;

public interface Rater {
    public void addRating(String item, double rating);
    public boolean hasRating(String item);
    public String getID();
    public double getRating(String movieId);
    public int numRatings();
    public ArrayList<String> getItemsRated();
}
