package com.example.myapplication;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
public class Recommendation_Activity extends AppCompatActivity {

    private TextView textField_message;
    private Button button_send_post;
    private Button button_send_get;
    private TextView textView_response;
    private String url="https://recommendation-system8398.herokuapp.com/";// *****put your URL here*********
    private String POST="POST";
    private String GET="GET";
    private CheckBox toy_group,terrier_group,sporting_group,working_group;
    private CheckBox imp_group,imp_height,imp_weight;
    private CheckBox low_height,low_weight,low_shedding;
    private CheckBox high_trainability,high_demeanor,high_expectancy;
    private CheckBox ignore_energy_level,ignore_trainability;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);
        textField_message=findViewById(R.id.txtField_message);
        textField_message.setText(getIntent().getStringExtra("breedname"));
        button_send_post=findViewById(R.id.button_send_post);
        button_send_get=findViewById(R.id.button_send_get);
        textView_response=findViewById(R.id.textView_response);
        textView_response.setMovementMethod(new ScrollingMovementMethod());
        toy_group=findViewById(R.id.toy_group);
        terrier_group=findViewById(R.id.terrier_group);
        working_group=findViewById(R.id.working_group);
        sporting_group=findViewById(R.id.sporting_group);
        imp_group=findViewById(R.id.imp_group);
        imp_height=findViewById(R.id.imp_height);
        imp_weight=findViewById(R.id.imp_weight);
        low_height=findViewById(R.id.low_height);
        low_weight=findViewById(R.id.low_weight);
        low_shedding=findViewById(R.id.low_shedding);
        high_expectancy=findViewById(R.id.high_expectancy);
        high_demeanor=findViewById(R.id.high_demeanor);
        high_trainability=findViewById(R.id.high_trainability);
        ignore_energy_level=findViewById(R.id.ignore_energy_level);
        ignore_trainability=findViewById(R.id.ignore_trainability);

        /*making a post request.*/
        button_send_post.setOnClickListener(view -> {

            //get the test in the text field.In this example you should type your name here
            //String text=textField_message.getText().toString();
            String text=getIntent().getStringExtra("breedname");
            text=textField_message.getText().toString();
            if(text.isEmpty()){
                textField_message.setError("This cannot be empty for post request");
            }else {
                /*if name text is not empty,then call the function to make the post request*/
                String group=",hello ";
                String low=",hello ";
                String medium=",hello ";
                String high=",hello ";
                String ignore=",hello ";
                String important=",hello ";
                if(toy_group.isChecked()){
                    group+="Toy ";
                }
                if(terrier_group.isChecked()){
                    group+="Terrier ";
                }
                if(sporting_group.isChecked()){
                    group+="Sporting ";
                }
                if(working_group.isChecked()){
                    group+="Working ";
                }
                text+=group;
                if(low_height.isChecked()){
                    low+="height ";
                }
                if(low_weight.isChecked()){
                    low+="weight ";
                }
                if(low_shedding.isChecked()){
                    low+="shedding ";
                }
                text+=low;
                text+=medium;
                if(high_trainability.isChecked()){
                    high+="trainability ";
                }
                if(high_demeanor.isChecked()){
                    high+="demeanor ";
                }
                if(high_expectancy.isChecked()){
                    high+="expectancy ";
                }
                text+=high;
                if(ignore_trainability.isChecked()){
                    ignore+="trainability ";
                }
                if(ignore_energy_level.isChecked()){
                    ignore+="energy_level ";
                }
                text+=ignore;
                if(imp_height.isChecked()){
                    important+="height ";
                }
                if(imp_weight.isChecked()){
                    important+="weight ";
                }
                if(imp_group.isChecked()){
                    important+="group ";
                }
                text+=important;
                sendRequest(POST, "getname", "name", text);
            }
        });

        /*making the get request*/
        button_send_get.setOnClickListener(view -> {
            String text="hello";
            String group=",hello ";
            String low=",hello ";
            String medium=",hello ";
            String high=",hello ";
            if(toy_group.isChecked()){
                group+="Toy ";
            }
            if(terrier_group.isChecked()){
                group+="Terrier ";
            }
            if(sporting_group.isChecked()){
                group+="Sporting ";
            }
            if(working_group.isChecked()){
                group+="Working ";
            }
            text+=group;
            if(low_height.isChecked()){
                low+="height ";
            }
            if(low_weight.isChecked()){
                low+="weight ";
            }
            if(low_shedding.isChecked()){
                low+="shedding ";
            }
            text+=low;
            text+=medium;
            if(high_trainability.isChecked()){
                high+="trainability ";
            }
            if(high_demeanor.isChecked()){
                high+="demeanor ";
            }
            if(high_expectancy.isChecked()){
                high+="expectancy ";
            }
            text+=high;
            sendRequest(POST,"getfact","info",text);
        });

    }
    void sendRequest(String type,String method,String paramname,String param){

        /* if url is of our get request, it should not have parameters according to our implementation.
         * But our post request should have 'name' parameter. */
        String fullURL=url+"/"+method+(param==null?"":"/"+param);
        Request request;

        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS).build();

        /* If it is a post request, then we have to pass the parameters inside the request body*/
        if(type.equals(POST)){
            RequestBody formBody = new FormBody.Builder()
                    .add(paramname, param)
                    .build();

            request=new Request.Builder()
                    .url(fullURL)
                    .post(formBody)
                    .build();
        }else{
            request = new Request.Builder()
                    .url(fullURL)
                    .build();
        }
        /* this is how the callback get handled */
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                // Read data on the worker thread
                final String responseData = response.body().string();

                // Run view-related code back on the main thread.
                // Here we display the response message in our text view
                Recommendation_Activity.this.runOnUiThread(() -> textView_response.setText(responseData));
            }
        });
    }
}
