package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StoresRVAdapter extends RecyclerView.Adapter<StoresRVAdapter.ViewHolder> {
    // creating variables for our ArrayList and context
    private ArrayList<Stores> coursesArrayList;
    private Context context;

    // creating constructor for our adapter class
    public StoresRVAdapter(ArrayList<Stores> coursesArrayList, Context context) {
        this.coursesArrayList = coursesArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public StoresRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // passing our layout file for displaying our card item
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.stores_list, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull StoresRVAdapter.ViewHolder holder, int position) {
        // setting data to our text views from our modal class.
        Stores courses = coursesArrayList.get(position);
        holder.courseNameTV.setText(courses.getStoreId());
        holder.courseDurationTV.setText(Html.fromHtml("<font><b>" + "Store Name : " + "</b></font>" +courses.getStoreName()));
        holder.courseDescTV.setText(Html.fromHtml("<font><b>" + "Store URL : " + "</b></font>" +courses.getStoreUrl()));
    }

    @Override
    public int getItemCount() {
        // returning the size of our array list.
        return coursesArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        // creating variables for our text views.
        private final TextView courseNameTV;
        private final TextView courseDurationTV;
        private final TextView courseDescTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views.
            courseNameTV = itemView.findViewById(R.id.storeId);
            courseDurationTV = itemView.findViewById(R.id.storeName);
            courseDescTV = itemView.findViewById(R.id.storeUrl);
        }
    }
}

