package com.example.hishara.mapapp.FindPlace;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.amigold.fundapter.BindDictionary;
import com.amigold.fundapter.FunDapter;
import com.amigold.fundapter.extractors.StringExtractor;
import com.example.hishara.mapapp.R;
import com.google.android.gms.location.places.Place;
import com.kosalgeek.android.json.JsonConverter;
import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import java.util.ArrayList;

public class FindPlace extends AppCompatActivity {

    private Button btnSearch;
    private ImageView ivRest;
    private ImageView ivHotel;
    private ImageView ivTourism;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_place);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        onRestClick();
        //getActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("com.example.hishara.mapapp.MapsActivity2");
                startActivity(intent);
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });
    }


    public static Double longitude;
    public static Double latitude;

    public void onRestClick(){

        try{

            LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                            if (ActivityCompat.checkSelfPermission(FindPlace.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                // TODO: Consider calling
                                //    ActivityCompat#requestPermissions
                                // here to request the missing permissions, and then overriding
                                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                //                                          int[] grantResults)
                                // to handle the case where the user grants the permission. See the documentation
                                // for ActivityCompat#requestPermissions for more details.
                                return;
                            }
                            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            longitude = Double.parseDouble(location.getLongitude()+"");
                            latitude = Double.parseDouble(location.getLatitude()+"");

            ivRest=(ImageView)findViewById(R.id.imageRest);
            ivRest.setOnClickListener(
                    new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
//
//
//                            Toast.makeText(getApplicationContext(), "Loading Result..\n" +
//                                    "Please Wait..!!",Toast.LENGTH_LONG).show();
//
                            String msg="geo:"+latitude+","+longitude+"&radius=5000?q=restaurants";
                            //Uri gmmIntentUri = Uri.parse(Uri.encode(msg));
                            Uri gmmIntentUri = Uri.parse("geo:6.796652,79.9003355&radius=5000?q=restaurants");
                            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                            mapIntent.setPackage("com.google.android.apps.maps");
                            startActivity(mapIntent);
                        }
                    }
            );

            ivHotel=(ImageView)findViewById(R.id.imageHotel);
            ivHotel.setOnClickListener(
                    new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
//

                            Toast.makeText(getApplicationContext(), "Loading Result..\n" +
                                    "Please Wait..!!",Toast.LENGTH_LONG).show();
                            Uri gmmIntentUri = Uri.parse("geo:6.796652,79.9003355&radius=5000?q=hotels");
                            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                            mapIntent.setPackage("com.google.android.apps.maps");
                            startActivity(mapIntent);
                        }
                    }
            );

            ivTourism=(ImageView)findViewById(R.id.imageTourist);
            ivTourism.setOnClickListener(
                    new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            Toast.makeText(getApplicationContext(), "Loading Result..\n" +
                                    "Please Wait..!!",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent("com.example.hishara.mapapp.PlaceResult");
                            startActivity(intent);
                        }
                    }
            );

        }
        catch(Exception e){
            e.printStackTrace();
        }

    }


    public void resultMap(){
        Uri gmmIntentUri = Uri.parse("geo:6.796652,79.9003355&radius=5000?q=restaurants");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }


    private ArrayList<Place> tourList;
    private ListView listView;
    final String LOG="FindPlace";

    public void setView2() {

        PostResponseAsyncTask taskRead = new PostResponseAsyncTask(FindPlace.this, new AsyncResponse() {
            @Override
            public void processFinish(String s) {
                Log.d(LOG, s);
                tourList = new JsonConverter<Place>().toArrayList(s, com.google.android.gms.location.places.Place.class);

                BindDictionary<Place> dict = new BindDictionary<Place>();

                dict.addStringField(R.id.tvName, new StringExtractor<Place>() {
                    @Override
                    public String getStringValue(Place item, int position) {
                        return String.valueOf(item.getName());
                    }
                });

                FunDapter<Place> adapter = new FunDapter<>(FindPlace.this, tourList, R.layout.layout_list, dict);
                listView = (ListView) findViewById(R.id.lvRestuarent);
                listView.setAdapter(adapter);
            }


        });
        taskRead.execute("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=6.796652,79.9003355&radius=500&types=cafe&key=AIzaSyBqMmZ6gXLA9D_K7yoOzS9ZXBaQOCuBFIE&sensor=true");
    }
    //AIzaSyAJYnBU3IrJYnLqHl_VBgJnCGVEYhtzh34

    //AIzaSyCOsP4WDKWZlXjSVeG7I_Y_1TrJB0bxxFo

}
