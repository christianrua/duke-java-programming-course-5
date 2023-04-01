package com.capstoneProject.stepFour;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class MovieRunnerWithFilters {

    // allowed values for env are "dev", "prod"
    private String env;
    private ThirdRatings tr;
    private int minimalRatings;

    public MovieRunnerWithFilters(String env, int raters) throws IOException {
        this.setEnvVariables(env);
        minimalRatings = raters;
    }

    private void setEnvVariables(String envValue) throws IOException {
        if(envValue.equals("dev")){
            tr = new ThirdRatings("ratings_short.csv");
            MovieDatabase.initialize("ratedmovies_short.csv");
        } else {
            tr = new ThirdRatings("ratings.csv");
            MovieDatabase.initialize("ratedmoviesfull.csv");
        }
    }

    private void sortRatingArray(ArrayList<Rating> avgRatingsList){
        Collections.sort(avgRatingsList, new SortByAvgRating());
    }

    private void printMoviesGeneralStatistics(){
        int numOfRatings = tr.getRaterSize();
        int numOfMovies = MovieDatabase.size();
        System.out.println("the number of movies is " + numOfMovies);
        System.out.println("the number of ratings are " + numOfRatings);
    }

    public void printAverageRatings() throws IOException {

        printMoviesGeneralStatistics();

        ArrayList<Rating> avgRatingsList = tr.getAverageRatings(minimalRatings);
        System.out.println("movies with more than " + minimalRatings + " ratings are " + avgRatingsList.size());

        Collections.sort(avgRatingsList);

        for(int i=0; i < avgRatingsList.size(); i++){
            Rating rating = avgRatingsList.get(i);
            System.out.println(rating.getValue() +" "+ MovieDatabase.getTitle(rating.getItem()));
        }

    }

    public void printAverageRatingsByYear(int year) throws IOException {
        printMoviesGeneralStatistics();

        YearAfterFilter yaf = new YearAfterFilter(year);
        ArrayList<Rating> avgRatingsList = tr.getAverageRatingsByFilter(minimalRatings, yaf);
        System.out.println("The movies are filter by the year " + year);
        System.out.println("movies with more than " + minimalRatings + " ratings are " + avgRatingsList.size());

        sortRatingArray(avgRatingsList);

        for(int i=0; i < avgRatingsList.size(); i++){
            Rating rating = avgRatingsList.get(i);
            String movieId = rating.getItem();
            System.out.println(rating.getValue() +" " + MovieDatabase.getYear(movieId) + " " + MovieDatabase.getTitle(movieId));
        }
    }

    public void printAverageRatingsByGenre(String genre) throws IOException {
        printMoviesGeneralStatistics();

        GenreFilter gf = new GenreFilter(genre);
        ArrayList<Rating> avgRatingsList = tr.getAverageRatingsByFilter(minimalRatings, gf);
        System.out.println("The movies are filter by the genre " + genre);
        System.out.println("movies with more than " + minimalRatings + " ratings are " + avgRatingsList.size());

        sortRatingArray(avgRatingsList);

        for(int i=0; i < avgRatingsList.size(); i++){
            Rating rating = avgRatingsList.get(i);
            String movieId = rating.getItem();
            System.out.println(rating.getValue() +" " + MovieDatabase.getGenres(movieId) + " " + MovieDatabase.getTitle(movieId));
        }
    }

    public void printAverageRatingsByMinutes(int min, int max) throws IOException {
        printMoviesGeneralStatistics();

        MinutesFilter mf = new MinutesFilter(min, max);
        ArrayList<Rating> avgRatingsList = tr.getAverageRatingsByFilter(minimalRatings, mf);
        System.out.println("The movies are filter by minutes with length between min " + min + " max " + max);
        System.out.println("movies with more than " + minimalRatings + " ratings are " + avgRatingsList.size());

        Collections.sort(avgRatingsList);

        for(int i=0; i < avgRatingsList.size(); i++){
            Rating rating = avgRatingsList.get(i);
            String movieId = rating.getItem();
            System.out.println(rating.getValue() +" " + MovieDatabase.getMinutes(movieId) + " " + MovieDatabase.getTitle(movieId));
        }
    }

    public void printAverageRatingsByDirectors(String directors) throws IOException {
        printMoviesGeneralStatistics();

        DirectorsFilter df = new DirectorsFilter(directors);
        ArrayList<Rating> avgRatingsList = tr.getAverageRatingsByFilter(minimalRatings, df);
        System.out.println("The movies are filter by directors" + directors);
        System.out.println("movies with more than " + minimalRatings + " ratings are " + avgRatingsList.size());

        sortRatingArray(avgRatingsList);

        for(int i=0; i < avgRatingsList.size(); i++){
            Rating rating = avgRatingsList.get(i);
            String movieId = rating.getItem();
            System.out.println(rating.getValue() +" "+ MovieDatabase.getTitle(movieId));
            System.out.println(MovieDatabase.getDirector(movieId));
        }
    }

    public void printAverageRatingsByYearAfterAndGenre(int year, String genre) throws IOException {
        printMoviesGeneralStatistics();

        AllFilters af = new AllFilters();
        af.addFilter(new GenreFilter(genre));
        af.addFilter(new YearAfterFilter(year));

        ArrayList<Rating> avgRatingsList = tr.getAverageRatingsByFilter(minimalRatings, af);
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

    public void printAverageRatingsByDirectorsAndMinutes(String directors, int min, int max) throws IOException {
        printMoviesGeneralStatistics();

        AllFilters af = new AllFilters();
        af.addFilter(new DirectorsFilter(directors));
        af.addFilter(new MinutesFilter(min, max));

        ArrayList<Rating> avgRatingsList = tr.getAverageRatingsByFilter(minimalRatings, af);
        System.out.println("The movies are filter by Directors " + directors + " and time length between min " + min + " max " + max );
        System.out.println("movies with more than " + minimalRatings + " ratings are " + avgRatingsList.size());

        sortRatingArray(avgRatingsList);

        for(int i=0; i < avgRatingsList.size(); i++){
            Rating rating = avgRatingsList.get(i);
            String movieId = rating.getItem();
            int timeLength = MovieDatabase.getMinutes(movieId);
            String movieTitle = MovieDatabase.getTitle(movieId);
            System.out.println(rating.getValue() +" time: "+ timeLength+ " " + movieTitle);
            System.out.println(MovieDatabase.getDirector(movieId));
        }
    }

}
