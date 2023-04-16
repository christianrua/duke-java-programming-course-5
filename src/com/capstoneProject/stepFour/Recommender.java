package com.capstoneProject.stepFour;

import java.io.IOException;
import java.util.ArrayList;

public interface Recommender {
    public ArrayList<String> getItemsToRate() throws IOException;
    public void printRecommendationsFor(String webRaterID) throws IOException;
}
