package com.coderman202.popularmovies.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.coderman202.popularmovies.R;
import com.coderman202.popularmovies.model.MovieReview;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Reggie on 25/03/2018.
 * Custom Adapter to handle displaying lists of movie trailers
 */

public class ReviewListAdapter extends RecyclerView.Adapter<ReviewListAdapter.ViewHolder> {

    private static final String LOG_TAG = ReviewListAdapter.class.getSimpleName();

    private List<MovieReview> reviewList;

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.username) TextView usernameView;
        @BindView(R.id.review) TextView reviewView;


        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public ReviewListAdapter() {
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reviews_list_item,
                    parent, false);
        return new ReviewListAdapter.ViewHolder(view);
    }

    /**
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ReviewListAdapter.ViewHolder holder, int position) {
        final MovieReview review = reviewList.get(position);

        holder.usernameView.setText(review.getAuthor());
        holder.reviewView.setText(review.getContent());

    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        if (reviewList == null) {
            return 0;
        }
        return reviewList.size();
    }

    /**
     * Sets/refreshes movie list.
     *
     * @param reviewList the movie list which is generated by the
     *                    {@link com.coderman202.popularmovies.responses.MovieReviewJsonResponse} object.
     */
    public void setReviewList(List<MovieReview> reviewList) {
        this.reviewList = reviewList;
        notifyDataSetChanged();
    }
}
