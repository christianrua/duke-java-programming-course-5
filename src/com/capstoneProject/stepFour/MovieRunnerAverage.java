package com.capstoneProject.stepThree;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class MovieRunnerAverage {

    private String shortMoviesPath = "src/com/capstoneProject/data/ratedmoviesfull.csv";
    private String shortRaterPath = "src/com/capstoneProject/data/ratings.csv";
    private SecondRatings sr = new SecondRatings(shortMoviesPath,shortRaterPath );

    public MovieRunnerAverage() throws IOException {
    }

    public void printAverageRatings() throws IOException {
        int numOfMovies = sr.getMovieSize();
        int numOfRatings = sr.getRaterSize();
        System.out.println("the number of movies is " + numOfMovies);
        System.out.println("the number of ratings are " + numOfRatings);

        ArrayList<Rating> avgRatingsList = sr.getAverageRatings(12);
        //System.out.println("movies with more than 50 ratings are " + avgRatingsList.size());
        //Collections.sort(avgRatingsList, new SortByAvgRating());
        Collections.sort(avgRatingsList);

        for(int i=0; i < avgRatingsList.size(); i++){
            Rating rating = avgRatingsList.get(i);
            System.out.println(rating.getValue() +" "+ sr.getTitle(rating.getItem()));
        }

    }

    public void getAverageRatingOneMovie(){
        String movieTitle = "Vacation";
        String movieId = sr.getId(movieTitle);
        double avgRating = sr.getAverageByID(movieId,1);
        System.out.println("The average rating value for " + movieTitle + " is " + avgRating);
    }
}
