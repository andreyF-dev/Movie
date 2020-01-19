package com.andreyjig.movie.utilities;

import com.andreyjig.movie.model.Movie;

import java.util.ArrayList;
import java.util.HashMap;

public class MovieUtils {

    public static ArrayList<String> getGenres (ArrayList<Movie> movies){
        ArrayList<String> result = new ArrayList<>();

        for (Movie movie: movies){
            String[] genres = movie.getGenres();
            for (int index = 0; index < genres.length; index++){
                if (!result.contains(genres[index])){
                    result.add(genres[index]);
                }
            }
        }

        return result;
    }

}
