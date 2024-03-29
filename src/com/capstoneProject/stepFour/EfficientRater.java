package com.capstoneProject.stepFour;

import java.util.ArrayList;
import java.util.HashMap;

public class EfficientRater implements Rater {
    private String myID;
    private HashMap<String, Rating> myRatings;

    public EfficientRater(String id) {
        myID = id;
        myRatings = new HashMap<>();
    }

    public void addRating(String item, double rating) {
        myRatings.put(item, new Rating(item,rating));
    }

    public boolean hasRating(String item) {
        if(myRatings.containsKey(item)){
            return true;
        }

        return false;
    }

    public String getID() {
        return myID;
    }

    public double getRating(String movieId) {
        if(myRatings.containsKey(movieId)){
            return myRatings.get(movieId).getValue();
        }

        return -1;
    }

    public int numRatings() {
        return myRatings.size();
    }

    public ArrayList<String> getItemsRated() {
        ArrayList<String> list = new ArrayList<String>();
        for(String key : myRatings.keySet()){
            list.add(myRatings.get(key).getItem());
        }

        return list;
    }
}
