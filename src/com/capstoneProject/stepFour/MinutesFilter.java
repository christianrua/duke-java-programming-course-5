package com.capstoneProject.stepFour;

import java.io.IOException;

public class MinutesFilter implements Filter {
    private int minMinutes;
    private int maxMinutes;

    public MinutesFilter(int minMin, int maxMin){
        minMinutes = minMin;
        maxMinutes = maxMin;
    }

    @Override
    public boolean satisfies(String id) throws IOException {
        int totalMinutes = MovieDatabase.getMinutes(id);
        return totalMinutes >= minMinutes && totalMinutes <= maxMinutes;
    }
}
