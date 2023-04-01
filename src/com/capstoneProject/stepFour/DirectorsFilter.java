package com.capstoneProject.stepFour;

import java.io.IOException;

public class DirectorsFilter implements Filter {

    private String myDirectors;

    public DirectorsFilter(String directors){
        myDirectors = directors;
    }

    @Override
    public boolean satisfies(String id) throws IOException {
        String[] directors = myDirectors.split(",");
        for(int i=0; i < directors.length; i++){
            if(MovieDatabase.getDirector(id).contains(directors[i])){
                return true;
            }
        }
        return false;
    }
}
