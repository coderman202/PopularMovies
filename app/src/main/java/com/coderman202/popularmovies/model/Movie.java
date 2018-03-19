package com.coderman202.popularmovies.model;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.coderman202.popularmovies.builders.ApiUrlBuilder;
import com.coderman202.popularmovies.utilities.FormatUtils;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Reggie on 25/02/2018.
 * A class to represent movies.
 */
public class Movie implements Parcelable {

    @SerializedName("original_title")
    @Expose
    private String originalTitle;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("poster_path")
    @Expose
    private String posterImageLink;

    @SerializedName("backdrop_path")
    @Expose
    private String backdropImageLink;

    @SerializedName("overview")
    @Expose
    private String overview;

    @SerializedName("vote_average")
    @Expose
    private double userRating;

    @SerializedName("vote_count")
    @Expose
    private int voteCount;

    @SerializedName("release_date")
    @Expose
    private String releaseDate;

    @SerializedName("genres")
    @Expose
    private List<Genre> genres;

    @SerializedName("runtime")
    @Expose
    private int runtime;

    @SerializedName("tagline")
    @Expose
    private String tagline;

    @SerializedName("id")
    @Expose
    private int tmdbID;

    @SerializedName("imdb_id")
    @Expose
    private String imdbID;

    @SerializedName("budget")
    @Expose
    private int budget;

    @SerializedName("revenue")
    @Expose
    private int revenue;

    @SerializedName("original_language")
    @Expose
    private String mainLanguage;

    @SerializedName("homepage")
    @Expose
    private String homepageLink;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("adult")
    @Expose
    private boolean adult;

    @SerializedName("popularity")
    @Expose
    private double popularity;

    @SerializedName("production_companies")
    @Expose
    private List<ProductionCompany> productionCompanies = null;

    @SerializedName("production_countries")
    @Expose
    private List<ProductionCountry> productionCountries = null;

    @SerializedName("spoken_languages")
    @Expose
    private List<SpokenLanguage> spokenLanguages = null;

    @SerializedName("video")
    @Expose
    private boolean video;

    @SerializedName("belongs_to_collection")
    @Expose
    private BelongsToCollection belongsToCollection;


    public Movie(){
        this.genres = new ArrayList<>();
        this.productionCompanies = new ArrayList<>();
        this.productionCountries = new ArrayList<>();
        this.spokenLanguages = new ArrayList<>();
    }

    /**
     * Gets original title.
     *
     * @return the original title
     */
    public String getOriginalTitle() {
        return originalTitle;
    }

    /**
     * Sets original title.
     *
     * @param originalTitle the original title
     */
    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    /**
     * Is adult boolean.
     *
     * @return the boolean
     */
    public boolean isAdult() {
        return adult;
    }

    /**
     * Sets adult.
     *
     * @param adult the adult
     */
    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    /**
     * Gets popularity.
     *
     * @return the popularity
     */
    public double getPopularity() {
        return popularity;
    }

    /**
     * Sets popularity.
     *
     * @param popularity the popularity
     */
    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    /**
     * Gets production companies.
     *
     * @return the production companies
     */
    public List<ProductionCompany> getProductionCompanies() {
        return productionCompanies;
    }

    /**
     * Sets production companies.
     *
     * @param productionCompanies the production companies
     */
    public void setProductionCompanies(List<ProductionCompany> productionCompanies) {
        this.productionCompanies = productionCompanies;
    }

    /**
     * Gets production countries.
     *
     * @return the production countries
     */
    public List<ProductionCountry> getProductionCountries() {
        return productionCountries;
    }

    /**
     * Sets production countries.
     *
     * @param productionCountries the production countries
     */
    public void setProductionCountries(List<ProductionCountry> productionCountries) {
        this.productionCountries = productionCountries;
    }

    /**
     * Gets spoken languages.
     *
     * @return the spoken languages
     */
    public List<SpokenLanguage> getSpokenLanguages() {
        return spokenLanguages;
    }

    /**
     * Sets spoken languages.
     *
     * @param spokenLanguages the spoken languages
     */
    public void setSpokenLanguages(List<SpokenLanguage> spokenLanguages) {
        this.spokenLanguages = spokenLanguages;
    }

    /**
     * Is video boolean.
     *
     * @return the boolean
     */
    public boolean isVideo() {
        return video;
    }

    /**
     * Sets video.
     *
     * @param video the video
     */
    public void setVideo(boolean video) {
        this.video = video;
    }

    /**
     * Gets belongs to collection.
     *
     * @return the belongs to collection
     */
    public BelongsToCollection getBelongsToCollection() {
        return belongsToCollection;
    }

    /**
     * Sets belongs to collection.
     *
     * @param belongsToCollection the belongs to collection
     */
    public void setBelongsToCollection(BelongsToCollection belongsToCollection) {
        this.belongsToCollection = belongsToCollection;
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Get title detailed string.
     *
     * @return the string
     */
    public String getTitleDetailed(){
        return title + " (" + this.getYear() + ")";
    }

    /**
     * Sets title.
     *
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets poster image link.
     *
     * @return the poster image link
     */
    public String getPosterImageLink() {
        return posterImageLink;
    }

    /**
     * Sets poster image link.
     *
     * @param posterImageLink the poster image link
     */
    public void setPosterImageLink(String posterImageLink) {
        this.posterImageLink = posterImageLink;
    }

    /**
     * Gets backdrop image link.
     *
     * @return the backdrop image link
     */
    public String getBackdropImageLink() {
        return backdropImageLink;
    }

    /**
     * Sets backdrop image link.
     *
     * @param backdropImageLink the backdrop image link
     */
    public void setBackdropImageLink(String backdropImageLink) {
        this.backdropImageLink = backdropImageLink;
    }

    /**
     * Gets overview.
     *
     * @return the overview
     */
    public String getOverview() {
        return overview;
    }

    /**
     * Sets overview.
     *
     * @param overview the overview
     */
    public void setOverview(String overview) {
        this.overview = overview;
    }

    /**
     * Gets user rating.
     *
     * @return the user rating
     */
    public double getUserRating() {
        return userRating;
    }

    /**
     * Get user rating to string string.
     *
     * @return the string
     */
    public String getUserRatingToString(){
        return DecimalFormat.getInstance().format(this.userRating);
    }

    /**
     * Sets user rating.
     *
     * @param userRating the user rating
     */
    public void setUserRating(double userRating) {
        this.userRating = userRating;
    }

    /**
     * Gets vote count.
     *
     * @return the vote count
     */
    public int getVoteCount() {
        return this.voteCount;
    }

    /**
     * Get vote count to string string.
     *
     * @return the string
     */
    public String getVoteCountToString(){
        return NumberFormat.getInstance().format(voteCount);
    }

    /**
     * Sets vote count.
     *
     * @param voteCount the vote count
     */
    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    /**
     * Gets release date.
     *
     * @return the release date
     */
    public String getReleaseDate(Locale locale) {
        return FormatUtils.formatDate(this.releaseDate, locale);
    }

    /**
     * Gets the year from the release date.
     *
     * @return the year as an int.
     */
    public int getYear(){

        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date convertedDate = new Date();

        try {
            convertedDate = dateFormat.parse(releaseDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(convertedDate);
        return cal.get(Calendar.YEAR);
    }

    /**
     * Sets release date.
     *
     * @param releaseDate the release date
     */
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    /**
     * Gets genres.
     *
     * @return the genres
     */
    public List<Genre> getGenres() {
        return genres;
    }

    /**
     * A method which loops through the list of Genres to get their names
     *
     * @return a String of concatenated genre names.
     */
    public String getGenresToString(){
        List<String> genresString = new ArrayList<>();

        for(Genre genre: this.genres){
            genresString.add(genre.getName());
        }

        return TextUtils.join(" | ", genresString);
    }

    /**
     * Sets genres.
     *
     * @param genres the genres
     */
    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    /**
     * Gets runtime.
     *
     * @return the runtime
     */
    public int getRuntime() {
        return runtime;
    }

    /**
     * Get runtime to string string.
     *
     * @return the string
     */
    public String getRuntimeToString(){
        if(this.runtime == 0)return "";
        int hours = this.runtime/60;
        int mins = this.runtime - (hours*60);
        String hourSign;
        if(hours > 1){
            hourSign = "hs ";
        }else{
            hourSign = "h ";
        }
        return Integer.toString(hours) + hourSign + Integer.toString(mins) + "m";

    }

    /**
     * Sets runtime.
     *
     * @param runtime the runtime
     */
    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    /**
     * Gets tagline.
     *
     * @return the tagline
     */
    public String getTagline() {
        return tagline;
    }

    /**
     * Sets tagline.
     *
     * @param tagline the tagline
     */
    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    /**
     * Gets tmdb id.
     *
     * @return the tmdb id
     */
    public int getTmdbID() {
        return tmdbID;
    }

    /**
     * Sets tmdb id.
     *
     * @param tmdbID the tmdb id
     */
    public void setTmdbID(int tmdbID) {
        this.tmdbID = tmdbID;
    }

    /**
     * Gets imdb id.
     *
     * @return the imdb id
     */
    public String getImdbID() {
        return imdbID;
    }

    /**
     * Sets imdb id.
     *
     * @param imdbID the imdb id
     */
    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    /**
     * Gets budget.
     *
     * @return the budget
     */
    public int getBudget() {
        return budget;
    }

    /**
     * Get budget in string format.
     * Using the formatNumbers method in {@link FormatUtils}
     *
     * @return the string format of the budget
     */
    public String getBudgetToString(){
        if(this.budget == 0)return "Unknown";
        return FormatUtils.formatNumbers(this.budget);
    }

    /**
     * Sets budget.
     *
     * @param budget the budget
     */
    public void setBudget(int budget) {
        this.budget = budget;
    }

    /**
     * Gets revenue.
     *
     * @return the revenue
     */
    public int getRevenue() {
        return revenue;
    }

    /**
     * Get revenue in string format.
     * Using the formatNumbers method in {@link FormatUtils}
     *
     * @return the string format of the revenue
     */
    public String getRevenueToString(){
        if(this.revenue == 0)return "Unknown";
        return FormatUtils.formatNumbers(this.revenue);
    }

    /**
     * Sets revenue.
     *
     * @param revenue the revenue
     */
    public void setRevenue(int revenue) {
        this.revenue = revenue;
    }

    /**
     * Gets main language.
     *
     * @return the main language
     */
    public String getMainLanguage() {
        Locale locale = new Locale(this.mainLanguage);
        return locale.getDisplayLanguage(locale);
    }

    /**
     * Sets main language.
     *
     * @param mainLanguage the main language
     */
    public void setMainLanguage(String mainLanguage) {
        this.mainLanguage = mainLanguage;
    }

    /**
     * Gets homepage link.
     *
     * @return the homepage link
     */
    public String getHomepageLink() {
        return homepageLink;
    }

    /**
     * Sets homepage link.
     *
     * @param homepageLink the homepage link
     */
    public void setHomepageLink(String homepageLink) {
        this.homepageLink = homepageLink;
    }

    /**
     * Get full imdb link for the movie in string form.
     *
     * @return the string
     */
    public String getImdbLink(){
        return ApiUrlBuilder.IMDB_BASE_LINK_URL + this.imdbID;
    }

    /**
     * Get full tmdb link for the movie in string form.
     *
     * @return the string
     */
    public String getTmdbLink(){
        return ApiUrlBuilder.TMDB_BASE_LINK_URL + this.tmdbID;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", posterImageLink='" + posterImageLink + '\'' +
                ", backdropImageLink='" + backdropImageLink + '\'' +
                ", overview='" + overview + '\'' +
                ", userRating=" + userRating +
                ", voteCount=" + voteCount +
                ", releaseDate='" + releaseDate + '\'' +
                ", genres=" + genres +
                ", runtime=" + runtime +
                ", tagline='" + tagline + '\'' +
                ", tmdbID=" + tmdbID +
                ", imdbID=" + imdbID +
                ", budget=" + budget +
                ", revenue=" + revenue +
                ", mainLanguage='" + mainLanguage + '\'' +
                ", homepageLink='" + homepageLink + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.originalTitle);
        dest.writeString(this.title);
        dest.writeString(this.posterImageLink);
        dest.writeString(this.backdropImageLink);
        dest.writeString(this.overview);
        dest.writeDouble(this.userRating);
        dest.writeInt(this.voteCount);
        dest.writeString(this.releaseDate);
        dest.writeTypedList(this.genres);
        dest.writeInt(this.runtime);
        dest.writeString(this.tagline);
        dest.writeInt(this.tmdbID);
        dest.writeString(this.imdbID);
        dest.writeInt(this.budget);
        dest.writeInt(this.revenue);
        dest.writeString(this.mainLanguage);
        dest.writeString(this.homepageLink);
        dest.writeString(this.status);
        dest.writeByte(this.adult ? (byte) 1 : (byte) 0);
        dest.writeDouble(this.popularity);
        dest.writeTypedList(this.productionCompanies);
        dest.writeTypedList(this.productionCountries);
        dest.writeList(this.spokenLanguages);
        dest.writeByte(this.video ? (byte) 1 : (byte) 0);
        dest.writeParcelable(this.belongsToCollection, flags);
    }

    protected Movie(Parcel in) {
        this.originalTitle = in.readString();
        this.title = in.readString();
        this.posterImageLink = in.readString();
        this.backdropImageLink = in.readString();
        this.overview = in.readString();
        this.userRating = in.readDouble();
        this.voteCount = in.readInt();
        this.releaseDate = in.readString();
        this.genres = in.createTypedArrayList(Genre.CREATOR);
        this.runtime = in.readInt();
        this.tagline = in.readString();
        this.tmdbID = in.readInt();
        this.imdbID = in.readString();
        this.budget = in.readInt();
        this.revenue = in.readInt();
        this.mainLanguage = in.readString();
        this.homepageLink = in.readString();
        this.status = in.readString();
        this.adult = in.readByte() != 0;
        this.popularity = in.readDouble();
        this.productionCompanies = in.createTypedArrayList(ProductionCompany.CREATOR);
        this.productionCountries = in.createTypedArrayList(ProductionCountry.CREATOR);
        this.spokenLanguages = new ArrayList<>();
        in.readList(this.spokenLanguages, SpokenLanguage.class.getClassLoader());
        this.video = in.readByte() != 0;
        this.belongsToCollection = in.readParcelable(BelongsToCollection.class.getClassLoader());
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
