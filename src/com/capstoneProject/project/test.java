package com.capstoneProject.project;

import java.io.IOException;

public class test {

    public static void main(String[] args) throws IOException {
        //FirstRatings fr = new FirstRatings();
        //fr.testLoadMovies("ratedmoviesfull.csv");
        //fr.testLoadRaters("ratings.csv");
        MovieRunnerAverage mra = new MovieRunnerAverage();
        mra.printAverageRatings();
    }
}
