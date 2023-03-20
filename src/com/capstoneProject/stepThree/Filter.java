package com.capstoneProject.stepThree;

import java.io.IOException;

public interface Filter {
    public boolean satisfies(String id) throws IOException;
}
