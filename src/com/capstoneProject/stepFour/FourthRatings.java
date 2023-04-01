package com.capstoneProject.stepFour;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

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
        ArrayList<String> commonMovies = new ArrayList<String>(myMoviesIds);

        // leave only the movie ids that are present in both lists.
        commonMovies.retainAll(otherMoviesIds);
        for(String movieId : commonMovies){
            dotProduct = (me.getRating(movieId) - 5) * (r.getRating(movieId) - 5) + dotProduct;
        }

        return dotProduct;
    }

    private ArrayList<Rating> getSimilarities(String id){
        ArrayList<Rating> similaritiesList = new ArrayList<>();
        Rater mainRater = RaterDatabase.getRater(id);
        Set<String> ratersList = RaterDatabase.getRatersIds();
        for(String raterId : ratersList){
            if(!raterId.equals(id)){
                Rater otherRater = RaterDatabase.getRater(raterId);
                double similarity = this.dotProduct(mainRater, otherRater);
                if(similarity > 0){
                    Rating result = new Rating(raterId,similarity);
                    similaritiesList.add(result);
                }
            }
        }
        Collections.sort(similaritiesList);
        return similaritiesList;
    }

    private ArrayList<Rating> getSimilarRatingsBase(String id, int numSimilarRaters, int minimalRaters, Filter movieFilter) throws IOException {
        ArrayList<Rating> similarRatings = new ArrayList<>();
        //id representing a rater ID
        ArrayList<Rating> similarityRating = this.getSimilarities(id);
        ArrayList<String> myMovies = MovieDatabase.filterBy(movieFilter);

        for(String movie: myMovies){
            int numOfRatersWithMovieRating = 0;
            double weightedRating = 0;
            double avgWeightedRating = 0;
            for(int i=0; i <= numSimilarRaters; i++){
                Rating similarRater = similarityRating.get(i);
                String raterId = similarRater.getItem();
                double similarity = similarRater.getValue();
                double movieRating = RaterDatabase.getRater(raterId).getRating(movie);

                if(movieRating != -1){
                    numOfRatersWithMovieRating++;
                    weightedRating =  weightedRating + (similarity * movieRating);
                }
            }
            avgWeightedRating = weightedRating / numOfRatersWithMovieRating;
            Rating similarRating = new Rating(movie, avgWeightedRating);
            similarRatings.add(similarRating);
        }
        Collections.sort(similarRatings);

        return similarRatings;
    }

    public ArrayList<Rating> getSimilarRatings(String id, int numSimilarRaters, int minimalRaters) throws IOException {
        ArrayList<Rating> similarRatings = new ArrayList<>();
        //id representing a rater ID
        ArrayList<Rating> similarityRating = this.getSimilarities(id);
        ArrayList<String> myMovies = MovieDatabase.filterBy(new TrueFilter());
        // those movies that have at least minimalRaters ratings from those most similar raters (not just minimalRaters ratings overall).
        for(String movie: myMovies){
            int numOfRatersWithMovieRating = 0;
            double weightedRating = 0;
            double avgWeightedRating = 0;
            for(int i=0; i <= numSimilarRaters; i++){
                Rating similarRater = similarityRating.get(i);
                String raterId = similarRater.getItem();
                double similarity = similarRater.getValue();
                double movieRating = RaterDatabase.getRater(raterId).getRating(movie);

                if(movieRating != -1){
                    numOfRatersWithMovieRating++;
                    weightedRating =  weightedRating + (similarity * movieRating);
                }
            }
            avgWeightedRating = weightedRating / numOfRatersWithMovieRating;
            Rating similarRating = new Rating(movie, avgWeightedRating);
            similarRatings.add(similarRating);
        }
        Collections.sort(similarRatings);

        return similarRatings;
    }


}
