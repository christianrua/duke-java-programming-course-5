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

    public void printSimilarRatings() throws IOException {
        printMoviesGeneralStatistics();
        ArrayList<Rating> response = fr.getSimilarRatings("71",20,minimalRatings);
        System.out.println("The recommended movies are: ");
        for(Rating rating : response){
            String movieName = MovieDatabase.getTitle(rating.getItem());
            double movieRating = rating.getValue();
            System.out.println("movie " + movieName + " has a rating of " + movieRating);
        }
    }

    public void printSimilarRatingsByGenre() throws IOException {
        printMoviesGeneralStatistics();
        ArrayList<Rating> response = fr.getSimilarRatingsByFilter("964",20,minimalRatings,new GenreFilter("Mystery"));
        System.out.println("The recommended movies are: ");
        for(Rating rating : response){
            String movieName = MovieDatabase.getTitle(rating.getItem());
            double movieRating = rating.getValue();
            String genres = MovieDatabase.getGenres(rating.getItem());
            System.out.println("movie " + movieName + " has a rating of " + movieRating);
            System.out.println("movie genres " + genres);
            System.out.println(" ");
        }
    }

    public void printSimilarRatingsByDirector() throws IOException {
        printMoviesGeneralStatistics();
        String directorsString = "Clint Eastwood,J.J. Abrams,Alfred Hitchcock,Sydney Pollack,David Cronenberg,Oliver Stone,Mike Leigh";
        ArrayList<Rating> response = fr.getSimilarRatingsByFilter("120",10,minimalRatings,new DirectorsFilter(directorsString));
        System.out.println("The recommended movies are: ");
        for(Rating rating : response){
            String movieId = rating.getItem();
            String movieName = MovieDatabase.getTitle(movieId);
            double movieRating = rating.getValue();
            String directors = MovieDatabase.getDirector(movieId);
            System.out.println("movie " + movieName + " has a rating of " + movieRating);
            System.out.println("movie directors " + directors);
            System.out.println(" ");
        }
    }

    public void printSimilarRatingsByGenreAndMinutes() throws IOException {
        printMoviesGeneralStatistics();
        GenreFilter genreFilter = new GenreFilter("Drama");
        MinutesFilter minutesFilter = new MinutesFilter(80,160);
        AllFilters combineFilters = new AllFilters();
        combineFilters.addFilter(genreFilter);
        combineFilters.addFilter(minutesFilter);
        ArrayList<Rating> response = fr.getSimilarRatingsByFilter("168",10,minimalRatings,combineFilters);
        System.out.println("The recommended movies are: ");
        for(Rating rating : response){
            String movieId = rating.getItem();
            String movieName = MovieDatabase.getTitle(movieId);
            double movieRating = rating.getValue();
            int movieMinutes = MovieDatabase.getMinutes(movieId);
            String genres = MovieDatabase.getGenres(movieId);
            String directors = MovieDatabase.getDirector(movieId);
            System.out.println("movie " + movieName + " has a rating of " + movieRating);
            System.out.println("movie genres " + genres + " movie duration in minutes " + movieMinutes);
            System.out.println(" ");
        }
    }

    public void printSimilarRatingsByYearAfterAndMinutes() throws IOException {
        printMoviesGeneralStatistics();
        YearAfterFilter yaf = new YearAfterFilter(1975);
        MinutesFilter minutesFilter = new MinutesFilter(70,200);
        AllFilters combineFilters = new AllFilters();
        combineFilters.addFilter(yaf);
        combineFilters.addFilter(minutesFilter);
        ArrayList<Rating> response = fr.getSimilarRatingsByFilter("314",10,minimalRatings,combineFilters);
        System.out.println("The recommended movies are: ");
        for(Rating rating : response){
            String movieId = rating.getItem();
            String movieName = MovieDatabase.getTitle(movieId);
            double movieRating = rating.getValue();
            int movieMinutes = MovieDatabase.getMinutes(movieId);
            int year = MovieDatabase.getYear(movieId);
            String directors = MovieDatabase.getDirector(movieId);
            System.out.println("movie " + movieName + " has a rating of " + movieRating);
            System.out.println("movie year " + year + " movie duration in minutes " + movieMinutes);
            System.out.println(" ");
        }
    }
}
