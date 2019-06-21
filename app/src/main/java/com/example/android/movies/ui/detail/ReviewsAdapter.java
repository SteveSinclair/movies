package com.example.android.movies.ui.detail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.movies.R;
import com.example.android.movies.data.entities.Review;

import java.util.List;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> {

    private static final String TAG = ReviewsAdapter.class.getSimpleName();

    private final Context mContext;
    private List<Review> mReviewList;

    public ReviewsAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.review_list_item, null);
        itemView.setFocusable(true);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Review review = mReviewList.get(position);
        holder.textViewAuthorName.setText(review.getAuthor());
        holder.textViewReview.setText(review.getContent());
    }

    @Override
    public int getItemCount() {
        if (mReviewList == null) {
            return 0;
        } else
            return mReviewList.size();
    }

    public void swapReviews(final List<Review> newReviews) {
        if (mReviewList == null) {
            mReviewList = newReviews;
            notifyDataSetChanged();
        } else {

            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mReviewList.size();
                }

                @Override
                public int getNewListSize() {
                    return newReviews.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mReviewList.get(oldItemPosition).getId() ==
                            newReviews.get(newItemPosition).getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Review newReview = newReviews.get(newItemPosition);
                    Review oldReview = mReviewList.get(oldItemPosition);
                    return newReview.getId() == oldReview.getId();
                }
            });
            mReviewList = newReviews;
            result.dispatchUpdatesTo(this);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView textViewAuthorName;
        final TextView textViewReview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewReview = itemView.findViewById(R.id.textViewReview);
            textViewAuthorName = itemView.findViewById(R.id.textViewAuthorName);
        }
    }
}
