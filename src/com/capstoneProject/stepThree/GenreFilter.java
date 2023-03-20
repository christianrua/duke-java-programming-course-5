package com.capstoneProject.stepThree;

import java.io.IOException;

public class GenreFilter implements Filter {

    private String myGenre;

    public GenreFilter(String genre){
        myGenre = genre;
    }

    @Override
    public boolean satisfies(String id) throws IOException {
        String movieGenres = MovieDatabase.getGenres(id);
        return movieGenres.contains(myGenre);
    }
}
