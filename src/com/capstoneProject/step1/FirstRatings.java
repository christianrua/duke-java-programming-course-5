package com.capstoneProject.step1;

import edu.duke.*;
import java.util.*;
import org.apache.commons.csv.*;
import com.capstoneProject.step1.Movie;

public class FirstRatings {

    public FirstRatings() {

    }

    private ArrayList<Movie> loadMovies(String filename){
        ArrayList<Movie> movieInformation = new ArrayList<>();
        FileResource fr = new FileResource(filename);
        for(String line : fr.lines()){

            String[] fields = line.split(",");

            if(fields[0].equals("id")){
                continue;
            }
            String id = fields[0];
            String title = fields[1];
            String year = fields[2];
            String country = fields[3];
            String genre = fields[4];
            String director = fields[5];
            int minutes = Integer.parseInt(fields[6]);
            String poster = fields[7];

            Movie movie = new Movie (id, title, year, genre, director,country, poster, minutes);
            movieInformation.add(movie);
        }

        return movieInformation;
    }

}
