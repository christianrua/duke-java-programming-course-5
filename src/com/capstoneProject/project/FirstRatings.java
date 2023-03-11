package com.capstoneProject.project;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import org.apache.commons.csv.*;

public class FirstRatings {

    private int numOfComedy;
    private int greaterThan150Min;
    private HashMap<String, Integer> directorsByMovie;
    private int maxMoviesByDirector;
    private ArrayList<Rater> ratersList;
    private ArrayList<Movie> movieInformation;
    private HashMap<String, Rater> ratersAndRatings;
    private HashMap<String, Integer> ratingsByRaterDic;
    private HashMap<String, Integer> ratingsByMovie;

    public FirstRatings() {
        numOfComedy = 0;
        greaterThan150Min = 0;
        directorsByMovie = new HashMap<>();
        maxMoviesByDirector = 0;
        ratersList = new ArrayList<>();
        movieInformation = new ArrayList<>();
        ratersAndRatings = new HashMap<>();
        ratingsByRaterDic = new HashMap<>();
        ratingsByMovie = new HashMap<>();
    }

    private void addRatingToMovie(String movie_id){
        if(ratingsByMovie.containsKey(movie_id)){
            ratingsByMovie.put(movie_id,ratingsByMovie.get(movie_id) + 1);
        } else {
            ratingsByMovie.put(movie_id, 1);
        }
    }

    public  HashMap<String, Integer>  getMaxValuesFromDict(HashMap<String, Integer> dict, Integer maxValue){
        //System.out.println("From getMaxValuesFromDict");
        HashMap<String, Integer> response = new HashMap<>();
        for(String key : dict.keySet()){

            if(dict.get(key) == maxValue){
//                System.out.println("key: " + key);
//                System.out.println("value: " + dict.get(key));
                response.put(key, maxValue);
            }
        }

        return response;

//        return dict.entrySet()
//                .stream().filter(x-> x.getValue() == maxValue)
//                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Iterable<CSVRecord> readCsv(String csvPath) throws IOException {
        Reader reader = Files.newBufferedReader(Paths.get(csvPath));
        return CSVFormat.DEFAULT.parse(reader);
    }

    public ArrayList<Movie> loadMovies(String fileName) throws IOException {
        Iterable<CSVRecord> records = readCsv(fileName);
        for (CSVRecord record : records) {

            if (record.get(0).equals("id")) {
                continue;
            }

            String id = record.get(0);
            String title = record.get(1);
            String year = record.get(2);
            String country = record.get(3);
            String genre = record.get(4);
            String directors = record.get(5);
            int minutes = Integer.parseInt(record.get(6));
            String poster = record.get(7);

            if(genre.indexOf("Comedy") != -1){
                numOfComedy++;
            }

            if(minutes > 150){
                greaterThan150Min++;
            }

            String[] directorsArray = directors.split(",");
            int currentNumOfMovies = 0;
            for(String director : directorsArray){
                if(directorsByMovie.containsKey(director)){
                    currentNumOfMovies = directorsByMovie.get(director) + 1;
                    directorsByMovie.put(director,currentNumOfMovies);

                } else {
                    directorsByMovie.put(director, 1);
                    currentNumOfMovies = 1;
                }

                if(currentNumOfMovies > maxMoviesByDirector){
                    maxMoviesByDirector = currentNumOfMovies;
                }
            }

            Movie movie = new Movie(id, title, year, genre, directors, country, poster, minutes);
            movieInformation.add(movie);
        }

        HashMap<String, Integer> filteredDirectors = getMaxValuesFromDict(directorsByMovie,maxMoviesByDirector);

        System.out.println("From loadMovies");
        System.out.println("Number of movies " + movieInformation.size());
        System.out.println("Number of Comedies " + numOfComedy);
        System.out.println("Number of movies with length greater than 150 min " + greaterThan150Min);
        System.out.println("The maximum number of movies is " + maxMoviesByDirector);
        System.out.println("Directors with max movies are " + filteredDirectors);

        return movieInformation;
    }

    public ArrayList<Rater> loadRaters(String fileName) throws IOException {
        Iterable<CSVRecord> records = readCsv(fileName);
        for (CSVRecord record : records) {
            if (record.get(0).equals("rater_id")) {
                continue;
            }

            String rater_id = record.get(0);
            String movie_id = record.get(1);
            double rating = Double.parseDouble(record.get(2));
            String time = record.get(3);

            if(ratersAndRatings.containsKey(rater_id)){
                Rater rater = ratersAndRatings.get(rater_id);
                rater.addRating(movie_id, rating);
                addRatingToMovie(movie_id);
            } else {
                Rater rater = new Rater(rater_id);
                rater.addRating(movie_id, rating);
                ratersAndRatings.put(rater_id, rater);
                addRatingToMovie(movie_id);
            }
        }

        for(Rater rater : ratersAndRatings.values()){
            ratersList.add(rater);
        }

        return ratersList;
    }

    private void ratingsByRater(String rater_id){
        for(Rater rater : ratersList){
            if(rater.getID().equals(rater_id)){
                System.out.println("The rater id " + rater_id + " has this amount of ratings " + rater.getItemsRated().size());
            }
        }
    }

    private void ratingsByMovie(String movie_id){
        System.out.println("From ratingsByMovie");
        System.out.println("The movie " + movie_id + " has this amount of ratings: " + ratingsByMovie.get(movie_id));
    }

    private void totalMoviesRated(){
        System.out.println("From totalMoviesRated");
        System.out.println("the total movies rated by all raters are " + ratingsByMovie.size());
    }

    private void maxRatingsByRater (){
        int maxNumberOfRatings = 0;
        for(Rater rater : ratersList){
            int numOfRatings = rater.getItemsRated().size();
            ratingsByRaterDic.put(rater.getID(),numOfRatings);
            if(numOfRatings > maxNumberOfRatings){
                maxNumberOfRatings = numOfRatings;
            }
        }

        System.out.println(ratingsByRaterDic);
        HashMap<String, Integer> filteredRaters = getMaxValuesFromDict(ratingsByRaterDic, maxNumberOfRatings);

        System.out.println("From maxRatingsByRater");
        System.out.println("the maximum number of ratings is " + maxNumberOfRatings);
        System.out.println("the number of raters who has this maximum number of ratings is " + filteredRaters.size());
        System.out.println("The raters ids are : ");
        for(String key : filteredRaters.keySet()){
            System.out.println(key);
        }

    }

    public void testLoadMovies(String file_name) throws IOException {
        ArrayList<Movie> result = loadMovies("src/com/capstoneProject/data/"+file_name);
//        for(int i=0; i < result.size(); i++){
//            Movie movie = result.get(i);
//            System.out.println(movie.toString());
//        }
    }

    public void testLoadRaters(String file_name) throws IOException {
        ArrayList<Rater> response = loadRaters("src/com/capstoneProject/data/"+file_name);
        System.out.println("The total number of raters is " + response.size());
        ratingsByRater("193");
        maxRatingsByRater();
        ratingsByMovie("1798709");
        totalMoviesRated();
//        for(Rater rater : response){
//            System.out.println("rater's ID " + rater.getID() + " number of ratings " + rater.getItemsRated().size());
//            for(String movie_id : rater.getItemsRated()){
//                System.out.println("movie id " + movie_id + " rated with value " + rater.getRating(movie_id));
//            }
//        }
    }

}
