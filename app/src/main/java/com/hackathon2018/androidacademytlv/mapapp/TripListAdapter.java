package com.hackathon2018.androidacademytlv.mapapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hackathon2018.androidacademytlv.mapapp.Data.IItemAddedCallback;
import com.hackathon2018.androidacademytlv.mapapp.Data.TripsDataLayer;
import com.hackathon2018.androidacademytlv.mapapp.Models.Trip;

import java.util.ArrayList;

public class TripListAdapter extends RecyclerView.Adapter<TripListAdapter.ViewHolder> implements View.OnClickListener {
    private ArrayList<Trip> mDataset = new ArrayList<>();
    private Context mContext;

    void setData(ArrayList<Trip> mDataset,Context mContext){
        this.mDataset = mDataset;
        this.mContext = mContext;
    }

    @Override
    public void onClick(View view) {
        int position = (Integer) view.getTag();
        TextView textView = (TextView) view.findViewById(R.id.trip_view_holder);
        String id = textView.getText().toString();
        TripsDataLayer.getInstance().getTripDetails(id, new IItemAddedCallback<Trip>() {
            @Override
            public void onItemAdded(Trip item) {
                Intent intent = new Intent(mContext, MapActivity.class);
                intent.putExtra(Trip.EXTRA_KEY,item);
                mContext.startActivity(intent);
            }
        });

    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        public ViewHolder(View v) {
            super(v);
            mTextView = v.findViewById(R.id.trip_view_holder);
            mTextView.setOnClickListener(TripListAdapter.this);

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
//   public TripListAdapter(ArrayList<String> strings) {}

//    public TripListAdapter(ListItemClickListener mOnClickListener) {
//        this.mOnClickListener = mOnClickListener;
//    }

    // Create new views (invoked by the layout manager)
    @Override
    public TripListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_holder, parent, false);

        ViewHolder vh = new ViewHolder(v);


        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mTextView.setText(String.valueOf(mDataset.get(position).id));
        holder.mTextView.setTag(position);


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}