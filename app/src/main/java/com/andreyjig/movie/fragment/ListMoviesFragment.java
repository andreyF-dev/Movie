package com.andreyjig.movie.fragment;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.andreyjig.movie.BuildConfig;
import com.andreyjig.movie.R;
import com.andreyjig.movie.adapter.FilmsAdapter;
import com.andreyjig.movie.adapter.GenresAdapter;
import com.andreyjig.movie.model.Movie;
import com.andreyjig.movie.utilities.MovieUtils;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ListMoviesFragment extends Fragment
        implements GenresAdapter.GenresAdapterCallback, FilmsAdapter.FilmsAdapterCallback {

    private static final String TAG_LIST_MOVIES_FRAGMENT = "ListMovies";

    private OnFragmentInteractionListener mListener;
    private View.OnClickListener mSnackBarOnClickListener;

    private RecyclerView mRecyclerViewGenres;
    private RecyclerView mRecyclerViewFilms;
    private ProgressBar mProgressBarGenres;
    private ProgressBar mProgressBarFilms;

    private FilmsAdapter mFilmsAdapter;

    private ArrayList<Movie> mMovies;
    private String mGenre = "";

    public ListMoviesFragment() {

    }


    public static ListMoviesFragment newInstance() {
        return new ListMoviesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_movies, container, false);
        mRecyclerViewGenres = view.findViewById(R.id.fragment_list_movies_recyclerview_genres);
        mRecyclerViewFilms = view.findViewById(R.id.fragment_list_movies_recyclerview_films);
        mProgressBarGenres = view.findViewById(R.id.fragment_list_movies_progress_bar_genres);
        mProgressBarFilms = view.findViewById(R.id.fragment_list_movies_progress_bar_films);

        mRecyclerViewFilms.setLayoutManager(new GridLayoutManager(getContext(), 2));

        mSnackBarOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMovies();
            }
        };

        if (mMovies == null ) {
            mMovies = new ArrayList<>();
            getMovies();
        } else {
            setAdapter();
        }

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mRecyclerViewGenres.setLayoutManager(new GridLayoutManager(getContext(), 1));
        } else {
            mRecyclerViewGenres.setLayoutManager(new GridLayoutManager(getContext(), 3));
        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void setGenre(String genre) {
        mFilmsAdapter.getFilterMovies(genre);
        mGenre = genre;
    }

    @Override
    public void getMovieInf(Movie movie) {
        mListener.onSelectMovie(movie);
    }

    public interface OnFragmentInteractionListener {
        void onSelectMovie(Movie movie);
    }

    private void getMovies() {
        mProgressBarGenres.setVisibility(View.VISIBLE);
        mProgressBarFilms.setVisibility(View.VISIBLE);
        mRecyclerViewGenres.setVisibility(View.GONE);
        mRecyclerViewFilms.setVisibility(View.GONE);

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        String url = Movie.URL;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Gson gson = new GsonBuilder().create();
                    JSONArray array = response.getJSONArray(Movie.FILMS);

                    mMovies = gson.fromJson(array.toString(),
                            new TypeToken<ArrayList<Movie>>() {
                            }.getType());

                } catch (JSONException e) {
                    if (BuildConfig.DEBUG) {
                        Log.d(TAG_LIST_MOVIES_FRAGMENT, "JSONException " + e);
                    }
                }

                if (getContext() != null) {
                    setAdapter();
                }
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG_LIST_MOVIES_FRAGMENT, "Error " + error);
                error.printStackTrace();
                Snackbar.make(mRecyclerViewFilms, getString(R.string.error_download), Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.download_now, mSnackBarOnClickListener)
                        .show();
            }
        });
        requestQueue.add(request);
    }

    private void setAdapter(){
        ArrayList<String> genres = MovieUtils.getGenres(mMovies);
        Collections.sort(mMovies, new Comparator<Movie>() {
            @Override
            public int compare(Movie o1, Movie o2) {
                return o1.getLocalized_name().compareTo(o2.getLocalized_name());
            }
        });
        mProgressBarGenres.setVisibility(View.GONE);
        mRecyclerViewGenres.setVisibility(View.VISIBLE);

        GenresAdapter genresAdapter = new GenresAdapter(getContext(), genres, ListMoviesFragment.this, mGenre);
        mRecyclerViewGenres.setAdapter(genresAdapter);

        mProgressBarFilms.setVisibility(View.GONE);
        mRecyclerViewFilms.setVisibility(View.VISIBLE);

        mFilmsAdapter = new FilmsAdapter(getContext(), mMovies, ListMoviesFragment.this);
        mRecyclerViewFilms.setAdapter(mFilmsAdapter);
        setGenre(mGenre);
    }
}
