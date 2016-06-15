package com.example.hishara.mapapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import java.util.HashMap;

public class AddNewPlace extends AppCompatActivity implements View.OnClickListener {

    final String LOG="AddNewPlace";
    private EditText etName,etAddress, etCatagory;
    private Button btAdd,btnImage;
    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_place);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etName=(EditText)findViewById(R.id.etPlaceName);
        etAddress=(EditText)findViewById(R.id.etPlaceAdd);
        //etCatagory=(EditText)findViewById(R.id.etPlaceCat);
        btAdd=(Button)findViewById(R.id.btnAdd);

        btAdd.setOnClickListener(this);

        spinner=(Spinner)findViewById(R.id.spCat);
        adapter=ArrayAdapter.createFromResource(this,R.array.places_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnImage=(Button)findViewById(R.id.btnImage);
        btnImage.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(getApplicationContext(), "Loading Map......\nPlease Wait..!! ", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent("com.example.hishara.mapapp.ImageUpload");
                        startActivity(intent);
                    }
                }
        );

    }

    @Override
    public void onClick(View v) {
        Double lat=MapsActivity2.newPlaceMarker.getPosition().latitude;
        Double lang=MapsActivity2.newPlaceMarker.getPosition().longitude;

        HashMap postData=new HashMap();
        postData.put("txtName",etName.getText().toString());
        postData.put("txtAdd",etAddress.getText().toString());
        //postData.put("txtCat",etCatagory.getText().toString());
        postData.put("txtCat",spinner.getSelectedItem().toString());
        postData.put("lat",""+lat);
        postData.put("lang",""+lang);
//        postData.put("lat","sffsf");
//        postData.put("lang","sfsf");
        postData.put("txtImage_url","ggghg");
        postData.put("mobile","android");

        PostResponseAsyncTask taskinsert=new PostResponseAsyncTask(AddNewPlace.this, postData, new AsyncResponse() {
            @Override
            public void processFinish(String s) {
                Log.d(LOG, s);
                if(s.contains("success")){
                    Toast.makeText(AddNewPlace.this, "Added successfully..!!!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent("com.example.hishara.mapapp.NewMenu.NewMenu");
                    startActivity(intent);
                }
            }
        });taskinsert.execute("http://guideme.orgfree.com/customer/insert.php");
    }
}
