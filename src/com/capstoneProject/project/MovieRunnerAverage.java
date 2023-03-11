package com.capstoneProject.project;

import com.capstoneProject.project.SecondRatings;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class MovieRunnerAverage {

    public void printAverageRatings() throws IOException {
        SecondRatings sr = new SecondRatings("src/com/capstoneProject/data/ratedmovies_short.csv","src/com/capstoneProject/data/ratings_short.csv");
        int numOfMovies = sr.getMovieSize();
        int numOfRatings = sr.getRaterSize();
        System.out.println("the number of movies is " + numOfMovies);
        System.out.println("the number of ratings are " + numOfRatings);

        ArrayList<Rating> avgRatingsList = sr.getAverageRatings(3);
        //Collections.sort(avgRatingsList, new SortByAvgRating());
        Collections.sort(avgRatingsList);

        for(int i=0; i < avgRatingsList.size(); i++){
            Rating rating = avgRatingsList.get(i);
            System.out.println(rating.getValue() +" "+ sr.getTitle(rating.getItem()));
        }

        // I'm here trying to create the getAverageRatingOneMovie

    }
}
