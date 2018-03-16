package com.coderman202.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Reggie on 14/03/2018.
 * Custom class to represent the country which a movie was produced in.
 */
public class ProductionCountry implements Parcelable {

    @SerializedName("iso_3166_1")
    @Expose
    private String countryISO;

    @SerializedName("name")
    @Expose
    private String name;

    /**
     * Gets country iso.
     *
     * @return the country iso
     */
    public String getCountryISO() {
        return countryISO;
    }

    /**
     * Sets country iso.
     *
     * @param countryISO the country iso
     */
    public void setCountryISO(String countryISO) {
        this.countryISO = countryISO;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.countryISO);
        dest.writeString(this.name);
    }

    /**
     * Instantiates a new Production country.
     */
    public ProductionCountry() {
    }

    /**
     * Instantiates a new Production country.
     *
     * @param in the in
     */
    protected ProductionCountry(Parcel in) {
        this.countryISO = in.readString();
        this.name = in.readString();
    }

    /**
     * The constant CREATOR.
     */
    public static final Parcelable.Creator<ProductionCountry> CREATOR = new Parcelable.Creator<ProductionCountry>() {
        @Override
        public ProductionCountry createFromParcel(Parcel source) {
            return new ProductionCountry(source);
        }

        @Override
        public ProductionCountry[] newArray(int size) {
            return new ProductionCountry[size];
        }
    };
}