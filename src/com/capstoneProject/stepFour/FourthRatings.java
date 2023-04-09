package com.capstoneProject.stepFour;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;


//https://github.com/SABERGLOW/Java_Programming_Build_A_Recommendation_System
public class FourthRatings {

    public FourthRatings() throws IOException {
        // default constructor
        this("ratings.csv");
    }

    public FourthRatings(String ratingsFile) throws IOException {
        RaterDatabase.initialize(ratingsFile);
        //FirstRatings fr = new FirstRatings();
        //myRaters = fr.loadRaters(ratingsFile);
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

    public double getAverageByID(String movieId, int minimalRaters){
        int numOfRaters = 0;
        double sumOfRatings = 0.0;
        for(Rater rater : RaterDatabase.getRaters()){
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

    private double dotProduct(Rater me, Rater r){
        double dotProduct = 0.0;
        ArrayList<String> myMoviesIds = me.getItemsRated();
        ArrayList<String> otherMoviesIds = r.getItemsRated();

        for(String movieId : myMoviesIds){
            if(otherMoviesIds.contains(movieId)){
                dotProduct = (me.getRating(movieId) - 5.0) * (r.getRating(movieId) - 5.0) + dotProduct;
            }
        }

        return dotProduct;
    }



    private ArrayList<Rating> getSimilarities(String id){
        ArrayList<Rating> similaritiesList = new ArrayList<>();
        Rater mainRater = RaterDatabase.getRater(id);
        ArrayList<Rater> raters = RaterDatabase.getRaters();

        for(Rater otherRater : raters){
            if(!otherRater.getID().equals(id)){
                double similarity = this.dotProduct(mainRater, otherRater);
                if(similarity >= 0){
                    similaritiesList.add(new Rating(otherRater.getID(),similarity));
                }
            }
        }

        Collections.sort(similaritiesList, Collections.reverseOrder());
        return similaritiesList;
    }

    private ArrayList<Rating> getSimilarRatingsBase(String id, int numSimilarRaters, ArrayList<String> myMovies, int minimalRaters) throws IOException {
        ArrayList<Rating> weightedRatings = new ArrayList<Rating> ();
        ArrayList<Rating> similarRaters = getSimilarities(id);

        HashMap<String,Double> accRating = new HashMap<String,Double> ();
        HashMap<String,Integer> accCount = new HashMap<String,Integer> ();

        for (String movieID : myMovies) {
            double actualRating = 0.0;
            int actualCount = 0;

            for (int k=0; k < numSimilarRaters; k++) {
                Rating r = similarRaters.get(k);
                String raterID = r.getItem();
                double weight = r.getValue();

                Rater rater = RaterDatabase.getRater(raterID);

                if (rater.hasRating(movieID)) {
                    double rating = rater.getRating(movieID) * weight;
                    actualRating += rating;
                    actualCount += 1;
                }
            }

            if (actualCount >= minimalRaters) {
                accRating.put(movieID, actualRating);
                accCount.put(movieID, actualCount);
            }
        }

        for (String movieID : accRating.keySet()) {
            double weightedRating = Math.round((accRating.get(movieID) / accCount.get(movieID)) * 100.0) / 100.0;
            Rating rating = new Rating (movieID, weightedRating);
            weightedRatings.add(rating);
        }

        Collections.sort(weightedRatings, Collections.reverseOrder());
        return weightedRatings;
    }

        public ArrayList<Rating> getSimilarRatings(String id, int numSimilarRaters, int minimalRaters) throws IOException {
        ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());
        ArrayList<Rating> similarRatings = this.getSimilarRatingsBase(id,numSimilarRaters,movies,minimalRaters);
        return similarRatings;
    }

    public ArrayList<Rating> getSimilarRatingsByFilter(String id, int numSimilarRaters, int minimalRaters, Filter filterCriteria) throws IOException {
        ArrayList<String> movies = MovieDatabase.filterBy(filterCriteria);
        ArrayList<Rating> similarRatings = this.getSimilarRatingsBase(id,numSimilarRaters,movies,minimalRaters);
        return similarRatings;
    }


}
