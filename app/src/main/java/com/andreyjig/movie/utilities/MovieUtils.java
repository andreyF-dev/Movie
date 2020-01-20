package com.andreyjig.movie.utilities;

import com.andreyjig.movie.model.Movie;
import java.util.ArrayList;

public class MovieUtils {

    public static ArrayList<String> getGenres (ArrayList<Movie> movies){
        ArrayList<String> result = new ArrayList<>();

        for (Movie movie: movies){
            String[] genres = movie.getGenres();
            for (String genre : genres) {
                if (!result.contains(genre)) {
                    result.add(genre);
                }
            }
        }

        return result;
    }

}
