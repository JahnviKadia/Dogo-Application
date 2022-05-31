package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication.ml.Model;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class CameraActivity extends AppCompatActivity {
    private ImageButton favorites;
    private ImageButton camera;
    int imageSize = 224;
    private byte[] byteArray;
    private String result;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private String favEmail;

    private static final String[] paths = {"Welcome To DoGo","Favorites", "Pet Supply", "Common Symptoms Information", "User Profile","Popular Breeds"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        Spinner myspinner = (Spinner) findViewById(R.id.dropdown);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(CameraActivity.this, android.R.layout.simple_list_item_1, paths);
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // fav
        firebaseAuth=FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        }
        databaseReference = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user=firebaseAuth.getCurrentUser();
        favEmail = user.getEmail();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        myspinner.setAdapter(myAdapter);
        myspinner.setSelection(0);
        myspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                switch (position) {
                    case 1:
                        startActivity(new Intent(CameraActivity.this, FavoriteActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(CameraActivity.this, pet_supply_store_Activity.class));
                        break;
                    case 3:
                        startActivity(new Intent(CameraActivity.this, CommonSymptomsActivity.class));
                        break;

                    case 4:
                        startActivity(new Intent(CameraActivity.this, ProfileActivity.class));
                        break;

                    case 5:
                        startActivity(new Intent(CameraActivity.this,Recommendation_Activity.class));
                        break;

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
        favorites = findViewById(R.id.breed_info_btn);
        camera=findViewById(R.id.add_to_fav_btn);
        camera.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                // Launch camera if we have permission
                if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, 1);
                } else {
                    //Request camera permission if we don't have it.
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, 100);
                }
            }
        });

        favorites.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //startActivity(new Intent(CameraActivity.this, FavoriteActivity.class));
                // result and email id
               /* userFavInfo();
                finish();
                startActivity(new Intent(CameraActivity.this, FavoriteActivity.class));

                */
            }
        });
    }

   /* private void userFavInfo ( ) {
        UserFav userFav = new UserFav(favEmail, result);
        FirebaseUser user = firebaseAuth.getCurrentUser();
        databaseReference.child(user.getUid()).setValue(userFav);
        Toast.makeText(getApplicationContext(),"Saved to Fav",Toast.LENGTH_LONG).show();
    }

    */

    public void classifyImage(Bitmap image){
        try {
            Model model = Model.newInstance(getApplicationContext());

            // Creates inputs for reference.
            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3}, DataType.FLOAT32);
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3);
            byteBuffer.order(ByteOrder.nativeOrder());

            // get 1D array of 224 * 224 pixels in image
            int [] intValues = new int[imageSize * imageSize];
            image.getPixels(intValues, 0, image.getWidth(), 0, 0, image.getWidth(), image.getHeight());

            // iterate over pixels and extract R, G, and B values. Add to bytebuffer.
            int pixel = 0;
            for(int i = 0; i < imageSize; i++){
                for(int j = 0; j < imageSize; j++){
                    int val = intValues[pixel++]; // RGB
                    byteBuffer.putFloat(((val >> 16) & 0xFF) * (1.f / 255.f));
                    byteBuffer.putFloat(((val >> 8) & 0xFF) * (1.f / 255.f));
                    byteBuffer.putFloat((val & 0xFF) * (1.f / 255.f));
                }
            }

            inputFeature0.loadBuffer(byteBuffer);

            // Runs model inference and gets result.
            Model.Outputs outputs = model.process(inputFeature0);
            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();
            float[] confidences = outputFeature0.getFloatArray();
            // find the index of the class with the biggest confidence.
            int maxPos = 0;
            float maxConfidence = 0;
            for(int i = 0; i < confidences.length; i++){
                if(confidences[i] > maxConfidence){
                    maxConfidence = confidences[i];
                    maxPos = i;
                }
            }
            String[] classes = {"Afghan Hound", "Australian Terrier", "Basset", "Black And Tan Coonhound",
                    "Blenheim Spaniel","Chow","English Foxhound","German Shepherd","Golden Retriever",
                    "Irish Terrier","Labrador Retriever","Lakeland Terrier","Mexican Hairless","Newfoundland",
                    "Pug","Rottweiler","Samoyed","Siberian Husky","Tibetan Mastiff","Tibetan Terrier"};
            result=(classes[maxPos]);
            Toast.makeText(this, "breed is : "+result, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, BreedActivity.class);
            intent.putExtra("picture", byteArray);
            intent.putExtra("mytext",result);
            startActivity(intent);
            //startActivity(new Intent(CameraActivity.this, BreedActivity.class));
            model.close();
        } catch (IOException e) {
            // TODO Handle the exception
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bitmap image = (Bitmap) data.getExtras().get("data");
            int dimension = Math.min(image.getWidth(), image.getHeight());
            image = ThumbnailUtils.extractThumbnail(image, dimension, dimension);
            //imageView.setImageBitmap(image);
            //Bitmap bmp = BitmapFactory.decodeResource(getResources(), image);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byteArray= stream.toByteArray();
            image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false);
            classifyImage(image);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}