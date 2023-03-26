package com.capstoneProject.stepThree;

import java.io.IOException;

public class test {

    public static void main(String[] args) throws IOException {
//        FirstRatings fr = new FirstRatings();
//        fr.testLoadMovies("ratedmoviesfull.csv");
//        fr.testLoadRaters("ratings.csv");
//        MovieRunnerAverage mra = new MovieRunnerAverage();
//        mra.printAverageRatings();
        MovieRunnerWithFilters mrwf = new MovieRunnerWithFilters("prod",3);
//        mrwf.printAverageRatings();
//        mrwf.printAverageRatingsByYear(2000);
//        mrwf.printAverageRatingsByGenre("Comedy");
//        mrwf.printAverageRatingsByMinutes(105,135);
//        mrwf.printAverageRatingsByDirectors("Clint Eastwood,Joel Coen,Martin Scorsese,Roman Polanski,Nora Ephron,Ridley Scott,Sydney Pollack");
//        mrwf.printAverageRatingsByYearAfterAndGenre(1990,"Drama");
        mrwf.printAverageRatingsByDirectorsAndMinutes("Clint Eastwood,Joel Coen,Tim Burton,Ron Howard,Nora Ephron,Sydney Pollack",90,180);
    }


}
