package com.capstoneProject.stepThree;

import java.util.Comparator;

public class SortByAvgRating implements Comparator<Rating> {

    public int compare(Rating rating1, Rating rating2){
        double result = rating1.getValue() - rating2.getValue();
        return (int) result;
    }
}
