package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class BreedActivity extends AppCompatActivity {
    private TextView breedname;
    private ImageView dog_image;
    private ImageButton add_fav;
    private ImageButton breed_info;
    private Button recommend_breed,location;

    //fav
    private String result;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private String favEmail;
    private static int count = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breed);
        breedname = findViewById(R.id.Breed_name);
        dog_image = findViewById(R.id.dog_img);
        add_fav  = findViewById(R.id.add_to_fav_btn);
        breed_info= findViewById(R.id.breed_info_btn);
        Bundle extras = getIntent().getExtras();
        byte[] byteArray = extras.getByteArray("picture");
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        dog_image.setImageBitmap(bmp);
        breedname.setText(getIntent().getStringExtra("mytext"));
        recommend_breed=findViewById(R.id.recommend_button);
        location=findViewById(R.id.loc);
        // fav
        result = getIntent().getStringExtra("mytext");
        firebaseAuth= FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        }
        databaseReference = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user=firebaseAuth.getCurrentUser();
        favEmail = user.getEmail();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        add_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(CameraActivity.this, FavoriteActivity.class));
                // result and email id
                userFavInfo();
                finish();
                startActivity(new Intent(BreedActivity.this, FavoriteActivity.class));
            }
        });

        recommend_breed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(BreedActivity.this, Recommendation_Activity.class);
                intent.putExtra("breedname",breedname.getText());
                startActivity(intent);
            }
        });

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(BreedActivity.this, AdoptActivity.class);
                //intent.putExtra("breedname",breedname.getText());
                startActivity(intent);
            }
        });

        breed_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(BreedActivity.this, BreedInfoActivity.class);
                intent.putExtra("picture", byteArray);
                intent.putExtra("mytext",breedname.getText());
                startActivity(intent);
                //startActivity(new Intent(BreedActivity.this, BreedInfoActivity.class));
            }
        });
    }

    private void userFavInfo ( ) {
        UserFav userFav = new UserFav(favEmail, result);
       // FirebaseUser user = firebaseAuth.getCurrentUser();
        databaseReference.child("UserFav").push().setValue(userFav);
       // databaseReference.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(userFav);
        //databaseReference.child(user.getUid()).setValue(userFav);
        Toast.makeText(getApplicationContext(),"Saved to Fav",Toast.LENGTH_LONG).show();
    }
}