package com.capstoneProject.step1;

import com.capstoneProject.step1.FirstRatings;

import java.io.IOException;

public class test {

    public static void main(String[] args) throws IOException {
        FirstRatings fr = new FirstRatings();
        //fr.testLoadMovies("ratedmoviesfull.csv");
        fr.testLoadRaters("ratings.csv");
    }
}
