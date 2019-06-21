package com.example.android.movies.ui.detail;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.movies.R;
import com.example.android.movies.data.entities.Trailer;

import java.util.List;

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.ViewHolder>{

    private static final String TAG = TrailersAdapter.class.getSimpleName();
    private final Context mContext;
    private List<Trailer> mTrailerList;
    private final TrailersAdapterOnItemClickHandler mClickHandler;

    public TrailersAdapter(Context context, TrailersAdapterOnItemClickHandler clickHandler) {
        this.mContext = context;
        mClickHandler = clickHandler;
    }

    @NonNull
    @Override
    public TrailersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.trailer_list_item, null);
        itemView.setFocusable(true);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailersAdapter.ViewHolder holder, int position) {
        Trailer trailer = mTrailerList.get(position);
        holder.textViewTrailerName.setText(trailer.getName());
    }

    @Override
    public int getItemCount() {
        if (mTrailerList == null) {
            return 0;
        } else
            return mTrailerList.size();
    }


    void swapTrailers(final List<Trailer> newTrailers) {
        if (mTrailerList == null) {
            mTrailerList = newTrailers;
            notifyDataSetChanged();
        } else {

            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mTrailerList.size();
                }

                @Override
                public int getNewListSize() {
                    return newTrailers.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mTrailerList.get(oldItemPosition).getId() ==
                            newTrailers.get(newItemPosition).getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Trailer newTrailer = newTrailers.get(newItemPosition);
                    Trailer oldTrailer = mTrailerList.get(oldItemPosition);
                    return newTrailer.getId() == oldTrailer.getId();
                }
            });
            mTrailerList = newTrailers;
            result.dispatchUpdatesTo(this);
        }
    }

    public interface TrailersAdapterOnItemClickHandler {
        void OnItemClick(String trailerKey);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        final TextView textViewTrailerName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTrailerName = itemView.findViewById(R.id.textViewTrailerName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            // TODO: 2019-06-19
            int adapterPosition = getAdapterPosition();
            Trailer trailer = mTrailerList.get(adapterPosition);
            mClickHandler.OnItemClick(trailer.getKey());
        }
    }

}
