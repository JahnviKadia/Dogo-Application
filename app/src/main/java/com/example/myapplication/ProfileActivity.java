package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProfileActivity  extends AppCompatActivity {

    private DatabaseReference databaseReference;
    // private TextView profileNameTextView, profileSurnameTextView, profilePhonenoTextView;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    //private ImageView profilePicImageView;
    private FirebaseStorage firebaseStorage;
    //private TextView textViewemailname;
    //private EditText editTextName;
    Userinformation2 userinformation2;
    private ArrayList<String> profileArrayList;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private Button backBtn, logoutBtn;
    TextView t1, t2, t3;

    @SuppressLint("ResourceType")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //databaseReference = FirebaseDatabase.getInstance().getReference("UserInfo");
        backBtn = findViewById(R.id.backBtn);
        logoutBtn = findViewById(R.id.logoutBtn);

        userinformation2 = new Userinformation2();
        firebaseAuth = FirebaseAuth.getInstance();
        //firebaseStorage = FirebaseStorage.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseUser user=firebaseAuth.getCurrentUser();
        databaseReference = firebaseDatabase.getReference("Userinformation2/"+user.getUid());

        listView = findViewById(R.id.profileList);

        t1 = findViewById(R.id.t1);
        t2 = findViewById(R.id.t2);
        t3 = findViewById(R.id.t3);

        profileArrayList = new ArrayList<>();
        profileArrayList.clear();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, profileArrayList);


        if (firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        }

        //user.getEmail();

      /*  databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange (@NonNull DataSnapshot snapshot) {
               /* for(DataSnapshot ds: snapshot.getChildren()){
                    profileArrayList.clear();
                    userinformation2 = ds.getValue(Userinformation2.class);
                    profileArrayList.add("\n\t\t Firstname: "+"\t\t "+ userinformation2.getUserName());
                    profileArrayList.add("\n\t\t LastName: "+ "\t\t "+userinformation2.getUserSurname());
                    profileArrayList.add("\n\t\t ContactNo: "+ "\t\t "+userinformation2.getUserPhoneno());
                }
                listView.setAdapter(adapter);


                userinformation2 = snapshot.getValue(Userinformation2.class);
                t1.setText(userinformation2.getUserName());
                t2.setText(userinformation2.getUserSurname());
                t3.setText(userinformation2.getUserPhoneno());


            }

            @Override
            public void onCancelled (@NonNull DatabaseError error) {

            }
        });
*/

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded (@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Userinformation2 userProfile = snapshot.getValue(Userinformation2.class);
                t1.setText(userProfile.getUserName());
                t2.setText(userProfile.getUserSurname());
                t3.setText(userProfile.getUserPhoneno());
                //textViewemailname=(TextView)findViewById(R.id.textViewEmailAdress);
                //textViewemailname.setText(user.getEmail());
            }

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

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                Intent i = new Intent(ProfileActivity.this, CameraActivity.class);
                startActivity(i);
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                Intent i = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(i);
            }
        });


    }

    public void navigateLogOut (View v){
        FirebaseAuth.getInstance().signOut();
        Intent inent = new Intent(this, CameraActivity.class);
        startActivity(inent);
    }

}