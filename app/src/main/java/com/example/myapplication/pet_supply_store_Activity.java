package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class pet_supply_store_Activity extends AppCompatActivity {

    private RecyclerView courseRV;
    private ArrayList<Stores> coursesArrayList;
    private StoresRVAdapter problemRVAdapter;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_supply_store);
        courseRV = findViewById(R.id.idRVStore);
        db = FirebaseFirestore.getInstance();
        coursesArrayList = new ArrayList<>();
        courseRV.setHasFixedSize(true);
        courseRV.setLayoutManager(new LinearLayoutManager(this));
        problemRVAdapter = new StoresRVAdapter(coursesArrayList, this);
        courseRV.setAdapter(problemRVAdapter);
        db.collection("StoreDetail").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                Stores c = d.toObject(Stores.class);
                                coursesArrayList.add(c);
                                assert c != null;
                                //Toast.makeText(CommonSymptomsActivity.this, "documentData"+c.getProblems(), Toast.LENGTH_SHORT).show();
                            }
                            problemRVAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(pet_supply_store_Activity.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(pet_supply_store_Activity.this, "Fail to get the data.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
