package com.andreyjig.movie.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;

import com.andreyjig.movie.R;
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
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                } else {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                }
            }
        });
        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.main_activity_container, movieInfFragment);
        ft.addToBackStack("MovieInf");
        ft.commit();
    }
}
