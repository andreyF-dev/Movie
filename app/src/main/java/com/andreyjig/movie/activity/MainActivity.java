package com.andreyjig.movie.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.net.Uri;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.andreyjig.movie.R;
import com.andreyjig.movie.adapter.FilmsAdapter;
import com.andreyjig.movie.fragment.ListMoviesFragment;
import com.andreyjig.movie.fragment.MovieInfFragment;
import com.andreyjig.movie.model.Movie;

public class MainActivity extends AppCompatActivity implements ListMoviesFragment.OnFragmentInteractionListener{

    private FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ft = getSupportFragmentManager().beginTransaction();
        ListMoviesFragment listMoviesFragment = ListMoviesFragment.newInstance();
        ft.add(R.id.main_activity_container, listMoviesFragment);
        ft.commit();
    }

    @Override
    public void onSelectMovie(Movie movie) {
        MovieInfFragment movieInfFragment = MovieInfFragment.newInstance(movie);
        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.main_activity_container, movieInfFragment);
        ft.commit();
    }
}
