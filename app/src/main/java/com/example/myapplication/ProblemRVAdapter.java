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

import java.text.BreakIterator;
import java.util.ArrayList;

public class ProblemRVAdapter extends RecyclerView.Adapter<ProblemRVAdapter.ViewHolder> {
    // creating variables for our ArrayList and context
    private ArrayList<problems> coursesArrayList;
    private Context context;

    // creating constructor for our adapter class
    public ProblemRVAdapter(ArrayList<problems> coursesArrayList, Context context) {
        this.coursesArrayList = coursesArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ProblemRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // passing our layout file for displaying our card item
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.symptoms_list, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ProblemRVAdapter.ViewHolder holder, int position) {
        // setting data to our text views from our modal class.
        problems courses = coursesArrayList.get(position);
        holder.courseNameTV.setText(courses.getProblems());
        holder.courseDurationTV.setText(Html.fromHtml("<font><b>" + "Symptoms : " + "</b></font>" + courses.getSymptoms()));
        holder.courseDescTV.setText(Html.fromHtml("<font><b>" + "Treatment : " + "</b></font>" + courses.getTreatment()));
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
            courseNameTV = itemView.findViewById(R.id.Problem);
            courseDurationTV = itemView.findViewById(R.id.symptoms);
            courseDescTV = itemView.findViewById(R.id.treatment);
        }
    }
}

