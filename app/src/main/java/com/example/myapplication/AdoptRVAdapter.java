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

public class AdoptRVAdapter extends RecyclerView.Adapter<AdoptRVAdapter.ViewHolder>{
    private ArrayList<Location> coursesArrayList;
    private Context context;

    // creating constructor for our adapter class
    public AdoptRVAdapter(ArrayList<Location> coursesArrayList, Context context) {
        this.coursesArrayList = coursesArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public AdoptRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // passing our layout file for displaying our card item
        return new AdoptRVAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.location_list, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AdoptRVAdapter.ViewHolder holder, int position) {
        // setting data to our text views from our modal class.
        Location courses = coursesArrayList.get(position);
        holder.courseNameTV.setText(courses.getAddress());
        holder.courseDurationTV.setText(courses.getEmail());
        holder.courseDescTV.setText(courses.getContact());
    }

    @Override
    public int getItemCount() {

        return coursesArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView courseNameTV;
        private final TextView courseDurationTV;
        private final TextView courseDescTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views.
            courseNameTV = itemView.findViewById(R.id.Address);
            courseDurationTV = itemView.findViewById(R.id.Email);
            courseDescTV = itemView.findViewById(R.id.Phone_no);
        }
    }
}
