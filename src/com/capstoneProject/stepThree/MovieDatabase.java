package com.capstoneProject.stepThree;

import java.io.IOException;
import java.util.*;
import org.apache.commons.csv.*;
import edu.duke.FileResource;

public class MovieDatabase {
    private static HashMap<String, Movie> ourMovies;
    private static String rootPath = "src/com/capstoneProject/data";

    public static void initialize(String moviefile) throws IOException {
        if (ourMovies == null) {
            ourMovies = new HashMap<String,Movie>();
            loadMovies(rootPath+ "/" + moviefile);
        }
    }

    private static void initialize() throws IOException {
        if (ourMovies == null) {
            ourMovies = new HashMap<String,Movie>();
            loadMovies(rootPath+ "/ratedmoviesfull.csv");
        }
    }


    private static void loadMovies(String filename) throws IOException {
        FirstRatings fr = new FirstRatings();
        ArrayList<Movie> list = fr.loadMovies(filename);
        for (Movie m : list) {
            ourMovies.put(m.getID(), m);
        }
    }

    public static boolean containsID(String id) throws IOException {
        initialize();
        return ourMovies.containsKey(id);
    }

    public static int getYear(String id) throws IOException {
        initialize();
        return ourMovies.get(id).getYear();
    }

    public static String getGenres(String id) throws IOException {
        initialize();
        return ourMovies.get(id).getGenres();
    }

    public static String getTitle(String id) throws IOException {
        initialize();
        return ourMovies.get(id).getTitle();
    }

    public static Movie getMovie(String id) throws IOException {
        initialize();
        return ourMovies.get(id);
    }

    public static String getPoster(String id) throws IOException {
        initialize();
        return ourMovies.get(id).getPoster();
    }

    public static int getMinutes(String id) throws IOException {
        initialize();
        return ourMovies.get(id).getMinutes();
    }

    public static String getCountry(String id) throws IOException {
        initialize();
        return ourMovies.get(id).getCountry();
    }

    public static String getDirector(String id) throws IOException {
        initialize();
        return ourMovies.get(id).getDirector();
    }

    public static int size() {
        return ourMovies.size();
    }

    public static ArrayList<String> filterBy(Filter f) throws IOException {
        initialize();
        ArrayList<String> list = new ArrayList<String>();
        for(String id : ourMovies.keySet()) {
            if (f.satisfies(id)) {
                list.add(id);
            }
        }

        return list;
    }

}
