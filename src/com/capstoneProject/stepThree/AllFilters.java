package com.capstoneProject.stepThree;

import java.io.IOException;
import java.util.ArrayList;

public class AllFilters implements Filter {
    ArrayList<Filter> filters;

    public AllFilters() {
        filters = new ArrayList<Filter>();
    }

    public void addFilter(Filter f) {
        filters.add(f);
    }

    @Override
    public boolean satisfies(String id) throws IOException {
        for(Filter f : filters) {
            if (! f.satisfies(id)) {
                return false;
            }
        }

        return true;
    }

}

