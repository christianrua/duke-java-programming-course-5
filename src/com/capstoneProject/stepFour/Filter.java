package com.capstoneProject.stepFour;

import java.io.IOException;

public interface Filter {
    public boolean satisfies(String id) throws IOException;
}
