package com.andreyjig.movie.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andreyjig.movie.R;
import com.andreyjig.movie.model.Movie;
import com.bumptech.glide.Glide;

public class MovieInfFragment extends Fragment {

    private static final String ARG_MOVIE = "MovieInfFragment";

    private Movie mMovie;

    public MovieInfFragment() {
    }

    public static MovieInfFragment newInstance(Movie movie) {
        MovieInfFragment fragment = new MovieInfFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_MOVIE, movie);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mMovie = getArguments().getParcelable(ARG_MOVIE);
        } else {
            mMovie = new Movie();
        }
        setHasOptionsMenu(true);
        setRetainInstance(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home && getActivity() != null) {
            getActivity().getSupportFragmentManager().popBackStack();
        }
        return true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_inf, container, false);
        getActivity().setTitle(mMovie.getLocalized_name());

        TextView textViewName = view.findViewById(R.id.fragment_movie_inf_name_text);
        TextView textViewYear = view.findViewById(R.id.fragment_movie_inf_year_text);
        TextView textViewRating = view.findViewById(R.id.fragment_movie_inf_rating_text);
        TextView textViewDescription = view.findViewById(R.id.fragment_movie_inf_description_text);
        ImageView imageView = view.findViewById(R.id.fragment_movie_inf_image);

        textViewName.setText(mMovie.getName());
        textViewYear.setText(String.format(getString(R.string.year), mMovie.getYear()));
        textViewRating.setText(String.format(getString(R.string.rating), mMovie.getRating()));
        textViewDescription.setText(mMovie.getDescription());

        Glide.with(imageView.getContext())
                .load(mMovie.getImage_url())
                .placeholder(R.drawable.ic_camera_roll_100dp)
                .dontTransform()
                .into(imageView);

        return view;
    }

}
