package com.andreyjig.movie.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andreyjig.movie.R;
import com.andreyjig.movie.model.Movie;
import com.squareup.picasso.Picasso;

public class MovieInfFragment extends Fragment {

    private static final String ARG_MOVIE = "MovieInfFragment";

    private Movie mMovie;
    private TextView mTextViewName;
    private TextView mTextViewYear;
    private TextView mTextViewRating;
    private TextView mTextViewDescription;
    private ImageView mImageView;

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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
            getActivity().getSupportFragmentManager().popBackStack();
            getActivity().setTitle(R.string.app_name);
        }
        return true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_inf, container, false);

        getActivity().setTitle(mMovie.getLocalized_name());

        mTextViewName = view.findViewById(R.id.fragment_movie_inf_name_text);
        mTextViewYear = view.findViewById(R.id.fragment_movie_inf_year_text);
        mTextViewRating = view.findViewById(R.id.fragment_movie_inf_rating_text);
        mTextViewDescription = view.findViewById(R.id.fragment_movie_inf_description_text);
        mImageView = view.findViewById(R.id.fragment_movie_inf_image);

        mTextViewName.setText(mMovie.getName());
        mTextViewYear.setText(mMovie.getYear());
        mTextViewRating.setText(mMovie.getRating());
        mTextViewDescription.setText(mMovie.getDescription());

        Picasso.with(getContext())
                .load(mMovie.getImage_url())
                .placeholder(R.drawable.ic_camera_roll_100dp)
                .fit().centerInside().into(mImageView);

        return view;
    }

}
