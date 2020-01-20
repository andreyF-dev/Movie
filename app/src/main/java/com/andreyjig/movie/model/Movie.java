package com.andreyjig.movie.model;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {

    public static final String URL = "https://s3-eu-west-1.amazonaws.com/sequeniatesttask/films.json";
    public static final String FILMS = "films";

    private String id;
    private String localized_name;
    private String name;
    private String year;
    private String rating;
    private String image_url;
    private String description;
    private String[] genres;

    public Movie() {
    }

    public Movie(String id, String localized_name,
                 String name, String year, String rating,
                 String image_url, String description, String[] genres) {
        this.id = id;
        this.localized_name = localized_name;
        this.name = name;
        this.year = year;
        this.rating = rating;
        this.image_url = image_url;
        this.description = description;
        this.genres = genres;
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public void setId(String id) {
        this.id = id;
    }

    public void setLocalized_name(String localized_name) {
        this.localized_name = localized_name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setGenres(String[] genres) {
        this.genres = genres;
    }

    public String getId() {
        return id;
    }

    public String getLocalized_name() {
        return localized_name;
    }

    public String getName() {
        return name;
    }

    public String getYear() {
        return year;
    }

    public String getRating() {
        return rating;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getDescription() {
        return description;
    }

    public String[] getGenres() {
        return genres;
    }

    protected Movie(Parcel in) {
        id = in.readString();
        localized_name = in.readString();
        name = in.readString();
        year = in.readString();
        rating = in.readString();
        image_url = in.readString();
        description = in.readString();
        genres = in.createStringArray();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(localized_name);
        dest.writeString(name);
        dest.writeString(year);
        dest.writeString(rating);
        dest.writeString(image_url);
        dest.writeString(description);
        dest.writeArray(genres);
    }
}
