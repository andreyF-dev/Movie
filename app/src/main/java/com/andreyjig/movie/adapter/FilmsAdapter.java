package com.andreyjig.movie.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.andreyjig.movie.R;
import com.andreyjig.movie.model.Movie;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class FilmsAdapter extends RecyclerView.Adapter<FilmsAdapter.MovieInfHolder> {

    private Context mContext;
    private ArrayList<Movie> mMovies;
    private ArrayList<Integer> mFilterMoviesIndex;
    private FilmsAdapterCallback mAdapterCallback;


    public FilmsAdapter(Context context, ArrayList<Movie> movies, FilmsAdapterCallback adapterCallback) {
        mContext = context;
        mMovies = movies;
        getFilterMovies("");
        mAdapterCallback = adapterCallback;
    }

    @NonNull
    @Override
    public MovieInfHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_movie,
                parent, false);
        return new MovieInfHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieInfHolder holder, int position) {
        Movie movie = mMovies.get(mFilterMoviesIndex.get(position));
        holder.mTextView.setText(movie.getLocalized_name());

        Glide.with(holder.mImageView.getContext())
                .load(movie.getImage_url())
                .placeholder(R.drawable.ic_camera_roll_100dp)
                .centerCrop()
                .into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mFilterMoviesIndex.size();
    }

    class MovieInfHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView mImageView;
        TextView mTextView;

        MovieInfHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.item_movie_image);
            mTextView = itemView.findViewById(R.id.item_movie_text);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mAdapterCallback.getMovieInf(mMovies.get(mFilterMoviesIndex.get(getAdapterPosition())));
        }
    }

    public void getFilterMovies (String genre){
        mFilterMoviesIndex = new ArrayList<>();
        for (int index = 0; index < mMovies.size(); index++){
            if (genre.isEmpty()){
                mFilterMoviesIndex.add(index);
            } else {
                Movie movie = mMovies.get(index);
                for (int currentGenre = 0; currentGenre < movie.getGenres().length; currentGenre++){
                    if (movie.getGenres()[currentGenre].equals(genre)){
                        mFilterMoviesIndex.add(index);
                        break;
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    public interface FilmsAdapterCallback {
        void getMovieInf (Movie movie);
    }
}
