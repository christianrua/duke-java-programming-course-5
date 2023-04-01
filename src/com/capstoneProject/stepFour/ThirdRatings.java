package com.capstoneProject.stepFour;

import java.io.IOException;
import java.util.ArrayList;

public class ThirdRatings {

    private ArrayList<EfficientRater> myRaters;

    public ThirdRatings() throws IOException {
        // default constructor
        this("ratings.csv");
    }

    public ThirdRatings(String ratingsFile) throws IOException {
        FirstRatings fr = new FirstRatings();
        myRaters = fr.loadRaters(ratingsFile);
    }

    public int getRaterSize(){
        return myRaters.size();
    }

    public double getAverageByID(String movieId, int minimalRaters){
        int numOfRaters = 0;
        double sumOfRatings = 0.0;

        for(int i=0; i < myRaters.size(); i++){
            EfficientRater rater = myRaters.get(i);
            if(rater.getRating(movieId) != -1){
                numOfRaters++;
                sumOfRatings += rater.getRating(movieId);
            }
        }

        if(numOfRaters >= minimalRaters){
            return sumOfRatings/numOfRaters;
        }

        return 0.0;
    }

    private ArrayList<Rating> buildAverageRatings(int minimalRaters, ArrayList<String> movies){
        ArrayList<Rating> avgRatings = new ArrayList<>();
        ArrayList<String> myMovies = movies;
        for(int i=0; i < myMovies.size(); i++){
            String movieId = myMovies.get(i);
            double avgRatingValue = this.getAverageByID(movieId, minimalRaters);
            if(avgRatingValue > 0.0){
                Rating rating = new Rating(movieId, avgRatingValue);
                avgRatings.add(rating);
            }
        }

        return avgRatings;
    }

    public ArrayList<Rating> getAverageRatings(int minimalRaters) throws IOException {
        ArrayList<Rating> avgRatings = new ArrayList<>();
        avgRatings =  buildAverageRatings(minimalRaters, MovieDatabase.filterBy(new TrueFilter()));

        return avgRatings;
    }

    public ArrayList<Rating> getAverageRatingsByFilter(int minimalRaters, Filter filterCriteria) throws IOException {
        ArrayList<Rating> avgRatings = new ArrayList<>();
        avgRatings =  buildAverageRatings(minimalRaters, MovieDatabase.filterBy(filterCriteria));

        return avgRatings;
    }



}
