package com.capstoneProject.stepFour;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class MovieRunnerSimilarRatings {

    // allowed values for env are "dev", "prod"
    private String env;
    private FourthRatings fr;
    private int minimalRatings;

    public MovieRunnerSimilarRatings(String env, int raters) throws IOException {
        this.setEnvVariables(env);
        minimalRatings = raters;
    }

    private void setEnvVariables(String envValue) throws IOException {
        if(envValue.equals("dev")){
            fr = new FourthRatings("ratings_short.csv");
            MovieDatabase.initialize("ratedmovies_short.csv");
        } else {
            fr = new FourthRatings("ratings.csv");
            MovieDatabase.initialize("ratedmoviesfull.csv");
        }

    }

    private void printMoviesGeneralStatistics(){
        int numOfRatings = RaterDatabase.size();
        int numOfMovies = MovieDatabase.size();
        System.out.println("the number of movies is " + numOfMovies);
        System.out.println("the number of ratings are " + numOfRatings);
    }

    public void printAverageRatings() throws IOException {

        printMoviesGeneralStatistics();

        ArrayList<Rating> avgRatingsList = fr.getAverageRatings(minimalRatings);
        System.out.println("movies with more than " + minimalRatings + " ratings are " + avgRatingsList.size());

        Collections.sort(avgRatingsList);

        for(int i=0; i < avgRatingsList.size(); i++){
            Rating rating = avgRatingsList.get(i);
            System.out.println(rating.getValue() +" "+ MovieDatabase.getTitle(rating.getItem()));
        }

    }

    public void printAverageRatingsByYearAfterAndGenre(int year, String genre) throws IOException {
        printMoviesGeneralStatistics();

        AllFilters af = new AllFilters();
        af.addFilter(new GenreFilter(genre));
        af.addFilter(new YearAfterFilter(year));

        ArrayList<Rating> avgRatingsList = fr.getAverageRatingsByFilter(minimalRatings, af);
        System.out.println("The movies are filter by year " + year + " and genre " + genre);
        System.out.println("movies with more than " + minimalRatings + " ratings are " + avgRatingsList.size());

        Collections.sort(avgRatingsList);

        for(int i=0; i < avgRatingsList.size(); i++){
            Rating rating = avgRatingsList.get(i);
            String movieId = rating.getItem();
            int movieYear = MovieDatabase.getYear(movieId);
            String movieTitle = MovieDatabase.getTitle(movieId);
            System.out.println(rating.getValue() +" "+ movieYear + " " + movieTitle);
            System.out.println(MovieDatabase.getGenres(movieId));
        }

    }
}
