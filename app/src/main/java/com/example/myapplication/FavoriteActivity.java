package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class FavoriteActivity extends AppCompatActivity {

    private ArrayList<String> favArrayList;
    private DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    private ListView listView;
    UserFav userFav;
    private ArrayAdapter<String> adapter;
    private FirebaseAuth firebaseAuth;
    TextView t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        listView = findViewById(R.id.favList);
        t1 = findViewById(R.id.t1);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseUser user=firebaseAuth.getCurrentUser();
        databaseReference = firebaseDatabase.getReference("UserFav/"+user.getUid());

        favArrayList = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, favArrayList);

        if (firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        }

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange (@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren()){
                    //favArrayList.clear();
                    userFav = ds.getValue(UserFav.class);
                    // Log.i("UserFav", String.valueOf(userFav));
                    //Log.i("Res", userFav.getResult());
                    favArrayList.add(userFav.getResult().toString());
                    //favArrayList.clear();

                }
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled (@NonNull DatabaseError error) {

            }
        });





        /*databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded (@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                for(DataSnapshot ds: snapshot.getChildren()) {
                        //favArrayList.clear();
                        userFav = ds.getValue(UserFav.class);
                        // Log.i("UserFav", String.valueOf(userFav));
                        //Log.i("Res", userFav.getResult());
                        favArrayList.add(userFav.getResult());
                        //favArrayList.clear();

                    }
                    listView.setAdapter(adapter);
                }

                //t1.setText(userProfile.getResult());
                //textViewemailname=(TextView)findViewById(R.id.textViewEmailAdress);
                //textViewemailname.setText(user.getEmail());

            @Override
            public void onChildChanged (@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved (@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved (@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled (@NonNull DatabaseError error) {

            }
        });

         */





    }
}