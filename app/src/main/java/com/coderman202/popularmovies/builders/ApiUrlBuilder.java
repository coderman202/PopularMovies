package com.coderman202.popularmovies.builders;

import android.content.Context;

import com.coderman202.popularmovies.utilities.NetworkConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Reggie on 28/02/2018.
 * Builds URL with base URL constant and gets network connectivity to TMDB API.
 */

public class ApiUrlBuilder {

    public static Retrofit retrofitClient = null;

    public static final String API_KEY = "";

    public static final int RESPONSE_CODE = 200;
    public static final int TIMEOUT = 10000;
    public static final int CONNECT_TIMEOUT = 15000;
    public static final String REQUEST_METHOD = "GET";
    public static final String CHARSET_NAME = "UTF-8";
    public static final int SLEEP_MILLIS = 2000;


    // Base URLs
    public static final String TMDB_BASE_URL = "http://api.themoviedb.org/3/";
    public final static String MOVIE_POSTER_PATH_BASE_URL = "http://image.tmdb.org/t/p/w200";
    public final static String BACKDROP_POSTER_PATH_BASE_URL = "http://image.tmdb.org/t/p/w500";

    public static Retrofit getRetrofitClient(Context context) throws IOException{

        if(!NetworkConnectionUtils.isConnected(context)){
            throw new IOException();
        }

        if(retrofitClient == null){
            OkHttpClient.Builder builder = new OkHttpClient.Builder();

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

            builder.addInterceptor(interceptor);
            builder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS);

            retrofitClient = new Retrofit.Builder().baseUrl(TMDB_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(builder.build()).build();
        }

        return retrofitClient;

    }


}
