package com.coderman202.popularmovies.adapters;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.coderman202.popularmovies.R;
import com.coderman202.popularmovies.fragments.DetailsFragment;
import com.coderman202.popularmovies.fragments.OverviewFragment;
import com.coderman202.popularmovies.fragments.ReviewsFragment;
import com.coderman202.popularmovies.fragments.TrailersFragment;

/**
 * Created by Reggie on 25/03/2018.
 * Custom pager adapter to handle on screen rotation better.
 */

public class MovieDetailPagerAdapter extends FragmentPagerAdapter {

    private Context context;
    private String[] titlesArray;
    private int tmdbID;

    public MovieDetailPagerAdapter(FragmentManager fm, Context context, int tmdbID) {
        super(fm);
        this.context = context;
        titlesArray = context.getResources().getStringArray(R.array.tablayout_titles_array);
        this.tmdbID = tmdbID;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titlesArray[position];
    }

    @Override
    public int getCount() {
        return titlesArray.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object fragment) {
        return ((Fragment) fragment).getView() == view;
    }

    public Fragment getItem(int position) {
        switch (position){
            case 0:
                OverviewFragment overviewFragment = new OverviewFragment();
                overviewFragment.setMovieIdKey(tmdbID);
                return overviewFragment;
            case 1:
                DetailsFragment detailsFragment = new DetailsFragment();
                detailsFragment.setMovieIdKey(tmdbID);
                return detailsFragment;
            case 2:
                TrailersFragment trailersFragment = new TrailersFragment();
                trailersFragment.setMovieIdKey(tmdbID);
                return trailersFragment;
            case 3:
                ReviewsFragment reviewsFragment = new ReviewsFragment();
                reviewsFragment.setMovieIdKey(tmdbID);
                return reviewsFragment;
        }
        return null;
    }
}
