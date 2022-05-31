package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebStorage;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;

public class BreedInfoActivity extends AppCompatActivity {
    private TextView breedname;
    private ImageView dog_image;
    private TextView origin;
    private TextView weight;
    private TextView height;
    private TextView nature;
    private TextView price;
    private TextView age;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    //CollectionReference cities = db.collection("cities");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breed_info);
        breedname = findViewById(R.id.Breed_name);
        dog_image = findViewById(R.id.dog_img);
        origin = findViewById(R.id.orgin_info);
        weight = findViewById(R.id.average_weight_info);
        height = findViewById(R.id.average_height_info);
        age = findViewById(R.id.average_age_info);
        price= findViewById(R.id.price_info);
        nature = findViewById(R.id.nature_info);
        Bundle extras = getIntent().getExtras();
        byte[] byteArray = extras.getByteArray("picture");
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        dog_image.setImageBitmap(bmp);
        breedname.setText(getIntent().getStringExtra("mytext"));
        String breed=(String)breedname.getText();
        DocumentReference docRef = db.collection("DogBreedInfo").document(breed);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        //Toast.makeText(BreedInfoActivity.this, "\"DocumentSnapshot data: \""+document.getData(), Toast.LENGTH_SHORT).show();
                        String org= document.getString("Origin");
                        //Toast.makeText(BreedInfoActivity.this, "origin : "+org, Toast.LENGTH_SHORT).show();
                        origin.setText("Origin : "+org);
                        weight.setText("Average Weight : "+document.getString("Average Weight"));
                        height.setText("Average Height : "+document.getString("Average Height"));
                        age.setText("Average Age : "+document.getString("Average Age"));
                        nature.setText("Nature : "+document.getString("Nature"));
                        price.setText("Price : "+document.getString("Price"));


                    } else {
                        Log.d("BreedInfoActivity", "No such document");
                    }
                } else {
                    Toast.makeText(BreedInfoActivity.this, "\"get failed with \""+ task.getException(), Toast.LENGTH_SHORT).show();
                    //Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

    }
}