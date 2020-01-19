package com.andreyjig.movie.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.andreyjig.movie.R;

import java.util.ArrayList;

public class GenresAdapter extends RecyclerView.Adapter<GenresAdapter.GenresHolder>{

    private static final String TAG = "GenresAdapter";

    private Context mContext;
    private ArrayList<String> mGenres;
    private int selectedGenres;
    private GenresAdapterCallback mAdapterCallback;

    public GenresAdapter(Context context, ArrayList<String> genres, GenresAdapterCallback adapterCallback) {
        mContext = context;
        mGenres = genres;
        selectedGenres = -1;
        mAdapterCallback = adapterCallback;
    }

    @NonNull
    @Override
    public GenresHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).
                inflate(R.layout.item_genres, parent,false);
        return new GenresHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GenresHolder holder, int position) {
        String genre = mGenres.get(position);
        holder.mTextView.setText(genre);
        if (position == selectedGenres){
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
                holder.mTextView.setBackgroundColor(Color.GRAY);
            } else {
                holder.mTextView.setBackgroundColor(mContext.getColor(R.color.background_text_genres_selected));
            }
        } else {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
                holder.mTextView.setBackgroundColor(Color.WHITE);
            } else {
                holder.mTextView.setBackgroundColor(mContext.getColor(R.color.background_text_genres));
            }
        }
    }

    @Override
    public int getItemCount() {
        return mGenres.size();
    }

    public void updateAdapter (int position){
        if (selectedGenres == position) {
            selectedGenres = -1;
            mAdapterCallback.setGenre("");
        } else {
            selectedGenres = position;
            mAdapterCallback.setGenre(mGenres.get(position));
        }
        notifyDataSetChanged();
    }

    class GenresHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView mTextView;

        GenresHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.item_genres_text);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            updateAdapter(getAdapterPosition());
        }
    }


    public interface GenresAdapterCallback {
        void setGenre (String genre);
    }
}
