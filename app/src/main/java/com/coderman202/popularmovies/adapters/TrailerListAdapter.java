package com.coderman202.popularmovies.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.coderman202.popularmovies.R;
import com.coderman202.popularmovies.builders.ApiUrlBuilder;
import com.coderman202.popularmovies.model.MovieTrailer;
import com.coderman202.popularmovies.responses.MovieTrailerJsonResponse;
import com.coderman202.popularmovies.utilities.PopularMoviesUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Reggie on 25/03/2018.
 * Custom Adapter to handle displaying lists of movie trailers
 */

public class TrailerListAdapter extends RecyclerView.Adapter<TrailerListAdapter.ViewHolder> {

    private static final String LOG_TAG = TrailerListAdapter.class.getSimpleName();

    private Context context;
    private List<MovieTrailer> trailerList;

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.trailer_type)
        TextView trailerTypeView;
        @BindView(R.id.trailer_name) TextView trailerNameView;
        @BindView(R.id.trailer_img)
        ImageView trailerItemView;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public TrailerListAdapter(Context context) {
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_list_item,
                    parent, false);
        return new TrailerListAdapter.ViewHolder(view);
    }

    /**
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull TrailerListAdapter.ViewHolder holder, int position) {
        final MovieTrailer trailer = trailerList.get(position);

        holder.trailerTypeView.setText(trailer.getType());
        holder.trailerNameView.setText(trailer.getName());

        String thumbnailUrlKey = trailer.getKey();

        if(!TextUtils.isEmpty(thumbnailUrlKey)){
            String thumbnailUrlPath = ApiUrlBuilder.YOUTUBE_IMG_BASE_URL + thumbnailUrlKey + "/" + ApiUrlBuilder.YOUTUBE_IMG_HQ;
            Picasso.with(context)
                    .load(thumbnailUrlPath)
                    .error(R.drawable.loading_error)
                    .placeholder(R.drawable.loading_progress)
                    .into(holder.trailerItemView);
        }

        holder.trailerItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopularMoviesUtils.openYoutubeLink(context, trailer.getKey());

            }
        });
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        if (trailerList == null) {
            return 0;
        }
        return trailerList.size();
    }

    /**
     * Sets/refreshes movie list.
     *
     * @param trailerList the movie list which is generated by the
     *                    {@link MovieTrailerJsonResponse} object.
     */
    public void setTrailerList(List<MovieTrailer> trailerList) {
        this.trailerList = trailerList;
        notifyDataSetChanged();
    }
}
