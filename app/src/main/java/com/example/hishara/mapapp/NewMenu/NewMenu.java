package com.example.hishara.mapapp.NewMenu;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.hishara.mapapp.R;

public class NewMenu extends AppCompatActivity {

    private static Button btnFindPlace;
    private static Button btnAddPlace;
    private static Button btnEx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        onFindPlaceClick();
        onHelpClick();


    }

    public void onFindPlaceClick(){
        btnFindPlace=(Button)findViewById(R.id.button_find);
        btnFindPlace.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent("com.example.hishara.mapapp.FindPlace.FindPlace");
                        startActivity(intent);
                    }
                }
        );

    }

    public void onHelpClick(){
        btnAddPlace=(Button)findViewById(R.id.button_addPlace);
        btnAddPlace.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "Loading Map......\nPlease Wait..!! ", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent("com.example.hishara.mapapp.MapsActivity2");
                        startActivity(intent);
                    }
                }
        );

        btnEx=(Button)findViewById(R.id.button_ex);
        btnEx.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(getApplicationContext(), "Loading Map......\nPlease Wait..!! ", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent("com.example.hishara.mapapp.UserEx");
                        startActivity(intent);
                    }
                }
        );

    }

}
