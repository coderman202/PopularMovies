package com.coderman202.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

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

/**
 * Created by Reggie on 25/02/2018.
 * A class to represent movies.
 */
public class Movie implements Parcelable {

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
    private int imdbID;

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

    /**
     * Instantiates a new Movie.
     *
     * @param title             the title
     * @param posterImageLink   the poster image link
     * @param backdropImageLink the backdrop image link
     * @param overview          the overview
     * @param userRating        the user rating
     * @param voteCount         the vote count
     * @param releaseDate       the release date
     * @param genres            the genres
     * @param runtime           the runtime
     * @param tagline           the tagline
     * @param tmdbID            the tmdb id
     * @param imdbID            the imdb id
     * @param budget            the budget
     * @param revenue           the revenue
     * @param mainLanguage      the main language
     * @param homepageLink      the homepage link

     * @param status            the status - is the film released or is it in production or what?
     */
    public Movie(String title, String posterImageLink, String backdropImageLink, String overview,
                 double userRating, int voteCount, String releaseDate, List<Genre> genres,
                 int runtime, String tagline, int tmdbID, int imdbID, int budget, int revenue,
                 String mainLanguage, String homepageLink, String status) {
        this.title = title;
        this.posterImageLink = posterImageLink;
        this.backdropImageLink = backdropImageLink;
        this.overview = overview;
        this.userRating = userRating;
        this.voteCount = voteCount;
        this.releaseDate = releaseDate;
        this.genres = genres;
        this.runtime = runtime;
        this.tagline = tagline;
        this.tmdbID = tmdbID;
        this.imdbID = imdbID;
        this.budget = budget;
        this.revenue = revenue;
        this.mainLanguage = mainLanguage;
        this.homepageLink = homepageLink;
        this.status = status;
    }

    private Movie(){
        this.genres = new ArrayList<>();
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

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

    public String getBackdropImageLink() {
        return backdropImageLink;
    }

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
    public String getReleaseDate() {
        return releaseDate;
    }

    /**
     * Gets the year from the release date.
     *
     * @return the year as an int.
     */
    public int getYear(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

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

    public String getRuntimeToString(){
        int hours = this.runtime/60;
        int mins = this.runtime - (hours*60);
        String hourSign = "";
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
    public int getImdbID() {
        return imdbID;
    }

    /**
     * Sets imdb id.
     *
     * @param imdbID the imdb id
     */
    public void setImdbID(int imdbID) {
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
        return mainLanguage;
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
        dest.writeInt(this.imdbID);
        dest.writeInt(this.budget);
        dest.writeInt(this.revenue);
        dest.writeString(this.mainLanguage);
        dest.writeString(this.homepageLink);
        dest.writeString(this.status);
    }

    protected Movie(Parcel in) {
        this();

        this.title = in.readString();
        this.posterImageLink = in.readString();
        this.backdropImageLink = in.readString();
        this.overview = in.readString();
        this.userRating = in.readDouble();
        this.voteCount = in.readInt();
        this.releaseDate = in.readString();
        in.readTypedList(genres, Genre.CREATOR);
        this.runtime = in.readInt();
        this.tagline = in.readString();
        this.tmdbID = in.readInt();
        this.imdbID = in.readInt();
        this.budget = in.readInt();
        this.revenue = in.readInt();
        this.mainLanguage = in.readString();
        this.homepageLink = in.readString();
        this.status = in.readString();
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
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
