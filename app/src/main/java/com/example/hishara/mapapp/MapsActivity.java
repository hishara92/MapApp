package com.example.hishara.mapapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.hishara.mapapp.Models.Restuarent;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kosalgeek.android.json.JsonConverter;
import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    final String LOG="MapsActivity";
    private ArrayList<Restuarent> restList;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        //setView();

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    public void onTypeChange(View view){
        Button typeButton=(Button)findViewById(R.id.BtnType);

        if(mMap.getMapType()==GoogleMap.MAP_TYPE_NORMAL){
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            typeButton.setText("Basic");
        }
        else{
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            typeButton.setText("Sat");
        }
    }

    public void onSearch(View view) {
        EditText locationText = (EditText) findViewById(R.id.TextPlace);
        String Laddress = locationText.getText().toString();
        List<Address> AddressList = null;


        if (locationText != null || !locationText.equals("")) {
            Geocoder geocoder = new Geocoder(this);
            try {
                AddressList=geocoder.getFromLocationName(Laddress, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Address address=AddressList.get(0);
            LatLng latLng=new LatLng(address.getLatitude(),address.getLongitude());
            System.out.println(latLng.toString());
            mMap.addMarker(new MarkerOptions().position(latLng).title("Marker1"));
            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));


        }
    }

    public void showPlaces(){


        try{

            for(int x=0;x<PlaceResult.lat.size();x++){

                double lt=Double.parseDouble(PlaceResult.lat.get(x));
                Log.d(LOG, lt + "");
                Double lng=Double.parseDouble(PlaceResult.lang.get(x));
                Log.d(LOG, lng + "");

                LatLng latLng=new LatLng(lt,lng);
                System.out.println(latLng.toString());
                mMap.addMarker(new MarkerOptions().position(latLng).title(PlaceResult.name.get(x)));
                //mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                //mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

            }

        }catch (Exception e){
            e.printStackTrace();
        }


    }

//    public void setView(){
//        PostResponseAsyncTask taskRead=new PostResponseAsyncTask(MapsActivity.this, new AsyncResponse() {
//            @Override
//            public void processFinish(String s) {
//                //Log.d(LOG,s);
//                restList=new JsonConverter<Restuarent>().toArrayList(s,Restuarent.class);
//            }
//        });
//        taskRead.execute("http://10.0.2.2/customer/product.php");
//    }











    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        showPlaces();
        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Maps Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.hishara.mapapp/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Maps Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.hishara.mapapp/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
