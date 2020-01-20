package com.andreyjig.movie.utilities;

import com.andreyjig.movie.model.Movie;

import java.util.ArrayList;

public class MovieUtils {

    public static ArrayList<String> getGenres(ArrayList<Movie> movies) {
        ArrayList<String> result = new ArrayList<>();

        for (Movie movie : movies) {
            String[] genres = movie.getGenres();
            for (String genre : genres) {
                if (!result.contains(genre)) {
                    result.add(genre);
                }
            }
        }

        return result;
    }

    public static ArrayList<Integer> getFilterIndex(String genre, ArrayList<Movie> movies) {
        ArrayList<Integer> filterMoviesIndex = new ArrayList<>();
        for (int index = 0; index < movies.size(); index++) {
            if (genre.isEmpty()) {
                filterMoviesIndex.add(index);
            } else {
                Movie movie = movies.get(index);
                for (int currentGenre = 0; currentGenre < movie.getGenres().length; currentGenre++) {
                    if (movie.getGenres()[currentGenre].equals(genre)) {
                        filterMoviesIndex.add(index);
                        break;
                    }
                }
            }
        }

        return filterMoviesIndex;
    }
}
