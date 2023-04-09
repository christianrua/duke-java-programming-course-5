package com.capstoneProject.stepFour;

import java.io.IOException;

public class test {

    public static void main(String[] args) throws IOException {
//        FirstRatings fr = new FirstRatings();
//        fr.testLoadMovies("ratedmoviesfull.csv");
//        fr.testLoadRaters("ratings.csv");
//        MovieRunnerAverage mra = new MovieRunnerAverage();
//        mra.printAverageRatings();
//        System.out.println("ThirdRatings content");
//        MovieRunnerWithFilters mrwf = new MovieRunnerWithFilters("prod",3);
//        mrwf.printAverageRatings();
//        mrwf.printAverageRatingsByYear(2000);
//        mrwf.printAverageRatingsByGenre("Comedy");
//        mrwf.printAverageRatingsByMinutes(105,135);
//        mrwf.printAverageRatingsByDirectors("Clint Eastwood,Joel Coen,Martin Scorsese,Roman Polanski,Nora Ephron,Ridley Scott,Sydney Pollack");
//        mrwf.printAverageRatingsByYearAfterAndGenre(1990,"Drama");
//        mrwf.printAverageRatingsByDirectorsAndMinutes("Clint Eastwood,Joel Coen,Tim Burton,Ron Howard,Nora Ephron,Sydney Pollack",90,180);
        System.out.println("FourthRatings content");
        MovieRunnerSimilarRatings mrsr = new MovieRunnerSimilarRatings("prod",5);
//        mrsr.printAverageRatings();
//        mrsr.printAverageRatingsByYearAfterAndGenre(1990,"Drama")
//        RaterDatabase.initialize("ratings_short.csv");
//        Rater me = RaterDatabase.getRater("2");
//        Rater r = RaterDatabase.getRater("3");
//        FourthRatings fr = new FourthRatings("ratings_short.csv");
          mrsr.printSimilarRatingsByYearAfterAndMinutes();
    }


}

