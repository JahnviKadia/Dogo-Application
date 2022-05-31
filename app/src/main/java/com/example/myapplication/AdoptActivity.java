package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AdoptActivity extends AppCompatActivity {

    private RecyclerView courseRV;
    private ArrayList<Location> coursesArrayList;
    private AdoptRVAdapter problemRVAdapter;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adopt);
        courseRV = findViewById(R.id.idRVSLocation);
        db = FirebaseFirestore.getInstance();
        coursesArrayList = new ArrayList<>();
        courseRV.setHasFixedSize(true);
        courseRV.setLayoutManager(new LinearLayoutManager(this));
        problemRVAdapter = new AdoptRVAdapter(coursesArrayList, this);
        courseRV.setAdapter(problemRVAdapter);
        db.collection("DogLocation").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                Location c = d.toObject(Location.class);
                                coursesArrayList.add(c);
                                assert c != null;
                                Toast.makeText(AdoptActivity.this, "documentData"+c.getAddress(), Toast.LENGTH_SHORT).show();
                            }
                            problemRVAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(AdoptActivity.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AdoptActivity.this, "Fail to get the data.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}