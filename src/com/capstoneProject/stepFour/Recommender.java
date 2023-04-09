package com.capstoneProject.stepFour;

import java.util.ArrayList;

public interface Recommender {
    public ArrayList<Movie> getItemsToRate();
    public void printRecommendationsFor();
}
