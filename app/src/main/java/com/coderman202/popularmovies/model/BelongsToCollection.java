package com.coderman202.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Reggie on 14/03/2018.
 * Custom class to represent the collection that a movie could belong to.
 */
public class BelongsToCollection implements Parcelable {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("poster_path")
    @Expose
    private String posterPath;

    @SerializedName("backdrop_path")
    @Expose
    private String backdropPath;

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets poster path.
     *
     * @return the poster path
     */
    public String getPosterPath() {
        return posterPath;
    }

    /**
     * Sets poster path.
     *
     * @param posterPath the poster path
     */
    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    /**
     * Gets backdrop path.
     *
     * @return the backdrop path
     */
    public String getBackdropPath() {
        return backdropPath;
    }

    /**
     * Sets backdrop path.
     *
     * @param backdropPath the backdrop path
     */
    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.posterPath);
        dest.writeString(this.backdropPath);
    }

    /**
     * Instantiates a new Belongs to collection.
     */
    public BelongsToCollection() {
    }

    /**
     * Instantiates a new Belongs to collection.
     *
     * @param in the in
     */
    protected BelongsToCollection(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.posterPath = in.readString();
        this.backdropPath = in.readString();
    }

    /**
     * The constant CREATOR.
     */
    public static final Parcelable.Creator<BelongsToCollection> CREATOR = new Parcelable.Creator<BelongsToCollection>() {
        @Override
        public BelongsToCollection createFromParcel(Parcel source) {
            return new BelongsToCollection(source);
        }

        @Override
        public BelongsToCollection[] newArray(int size) {
            return new BelongsToCollection[size];
        }
    };
}