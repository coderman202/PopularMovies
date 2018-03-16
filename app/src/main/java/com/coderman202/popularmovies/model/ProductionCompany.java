package com.coderman202.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Reggie on 14/03/2018.
 * Custom class to represent the company which produced the movie
 */
public class ProductionCompany implements Parcelable {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("logo_path")
    @Expose
    private String logoPath;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("origin_country")
    @Expose
    private String originCountry;

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
     * Gets logo path.
     *
     * @return the logo path
     */
    public String getLogoPath() {
        return logoPath;
    }

    /**
     * Sets logo path.
     *
     * @param logoPath the logo path
     */
    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
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
     * Gets origin country.
     *
     * @return the origin country
     */
    public String getOriginCountry() {
        return originCountry;
    }

    /**
     * Sets origin country.
     *
     * @param originCountry the origin country
     */
    public void setOriginCountry(String originCountry) {
        this.originCountry = originCountry;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.logoPath);
        dest.writeString(this.name);
        dest.writeString(this.originCountry);
    }

    /**
     * Instantiates a new Production company.
     */
    public ProductionCompany() {
    }

    /**
     * Instantiates a new Production company.
     *
     * @param in the in
     */
    protected ProductionCompany(Parcel in) {
        this.id = in.readInt();
        this.logoPath = in.readString();
        this.name = in.readString();
        this.originCountry = in.readString();
    }

    /**
     * The constant CREATOR.
     */
    public static final Parcelable.Creator<ProductionCompany> CREATOR = new Parcelable.Creator<ProductionCompany>() {
        @Override
        public ProductionCompany createFromParcel(Parcel source) {
            return new ProductionCompany(source);
        }

        @Override
        public ProductionCompany[] newArray(int size) {
            return new ProductionCompany[size];
        }
    };
}
