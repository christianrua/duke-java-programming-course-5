package com.capstoneProject.project;

import java.io.IOException;
import java.util.*;
import com.capstoneProject.project.FirstRatings;

public class SecondRatings {
    private ArrayList<Movie> myMovies;
    private ArrayList<Rater> myRaters;

    public SecondRatings() throws IOException {
        // default constructor
        this("ratedmoviesfull.csv", "ratings.csv");
    }

    public SecondRatings(String movieFile, String ratingsFile) throws IOException {
        FirstRatings fr = new FirstRatings();
        myMovies = fr.loadMovies(movieFile);
        myRaters = fr.loadRaters(ratingsFile);
    }

    public int getMovieSize(){
        return myMovies.size();
    }

    public int getRaterSize(){
        return myRaters.size();
    }

    public double getAverageByID(String movieId, int minimalRaters){
        int numOfRaters = 0;
        double sumOfRatings = 0.0;

        for(int i=0; i < myRaters.size(); i++){
            Rater rater = myRaters.get(i);
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

    public ArrayList<Rating> getAverageRatings(int minimalRaters){
        ArrayList<Rating> avgRatings = new ArrayList<>();
        for(int i=0; i < myMovies.size(); i++){
            String movieId = myMovies.get(i).getID();
            double avgRatingValue = this.getAverageByID(movieId, minimalRaters);
            if(avgRatingValue > 0.0){
                Rating rating = new Rating(movieId, avgRatingValue);
                avgRatings.add(rating);
            }
        }

        return avgRatings;
    }

    public String getTitle(String movieId){
        for(int i=0; i < myMovies.size(); i++){
            Movie movie = myMovies.get(i);
            if(movie.getID().equals(movieId)){
                return movie.getTitle();
            }
        }

        return "the requested movie does not exist";
    }

    public String getId(String movieTitle){
        for(int i=0; i < myMovies.size(); i++){
            Movie movie = myMovies.get(i);
            if(movie.getTitle().equals(movieTitle)){
                return movie.getID();
            }
        }

        return "NO SUCH TITLE";
    }
}
