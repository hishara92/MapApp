package com.example.hishara.mapapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Debug;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.amigold.fundapter.BindDictionary;
import com.amigold.fundapter.FunDapter;
import com.amigold.fundapter.extractors.StringExtractor;
import com.amigold.fundapter.interfaces.DynamicImageLoader;
import com.example.hishara.mapapp.Models.TouristPlace;
import com.kosalgeek.android.json.JsonConverter;
import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class PlaceResult extends AppCompatActivity {

    final String LOG = "PlaceResult";
    private ArrayList<TouristPlace> tourList;
    private ListView listView;
    Button btnShow,btnFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_result);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setView();

        try {
            onShowMapClick();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static ArrayList<String> lang;
    public static ArrayList<String> lat;
    public static ArrayList<String> name;
    public static float num;
    public float num1;

    public void setView() {
        lang = new ArrayList<String>();
        lat = new ArrayList<String>();
        name = new ArrayList<String>();
        PostResponseAsyncTask taskRead = new PostResponseAsyncTask(PlaceResult.this, new AsyncResponse() {
            @Override
            public void processFinish(String s) {
                //Log.d(LOG,s);
                tourList = new JsonConverter<TouristPlace>().toArrayList(s, TouristPlace.class);
                //removeUnWantedResults(tourList, 10.00F);
                BindDictionary<TouristPlace> dict = new BindDictionary<TouristPlace>();


                dict.addStringField(R.id.tvDistance, new StringExtractor<TouristPlace>() {
                    @Override
                    public String getStringValue(TouristPlace item, int position) {
                        num = findDistance(Double.parseDouble("6.796652"), Double.parseDouble("79.9003355"), Double.parseDouble(item.getLat()), Double.parseDouble(item.getLang())) / 1000;
                        DecimalFormat df = new DecimalFormat("#.#");
                        num1 = Float.valueOf(df.format(num));
                        String s = String.valueOf(num1) + "km";
                        return s;
                    }
                });

//                Toast.makeText(getApplicationContext(), num+"",
//                        Toast.LENGTH_LONG).show();

                dict.addStringField(R.id.tvName, new StringExtractor<TouristPlace>() {
                    @Override
                    public String getStringValue(TouristPlace item, int position) {
                        lat.add(item.getLat());
                        lang.add(item.getLang());
                        name.add(item.getName());
                        return item.getName();
                    }
                });

                dict.addStringField(R.id.tvCat, new StringExtractor<TouristPlace>() {
                    @Override
                    public String getStringValue(TouristPlace item, int position) {

                        return item.getCatagory();
                    }
                });

                dict.addStringField(R.id.tvAddress, new StringExtractor<TouristPlace>() {
                    @Override
                    public String getStringValue(TouristPlace item, int position) {
                        return item.getAddress();
                    }
                });

                final ImageView iv = (ImageView) findViewById(R.id.ivImage);
                dict.addDynamicImageField(R.id.ivImage, new StringExtractor<TouristPlace>() {
                    @Override
                    public String getStringValue(TouristPlace item, int position) {
                        return item.getImg_url();
                    }
                }, new DynamicImageLoader() {
                    @Override
                    public void loadImage(String url, ImageView view) {
                        Picasso.with(PlaceResult.this)
                                .load(url)
                                .placeholder(android.R.drawable.star_big_on)
                                .error(android.R.drawable.stat_sys_download).into(view);
//                        iv.setPadding(0,0,0,0);
//                        iv.setAdjustViewBounds(true);
                    }
                });


                FunDapter<TouristPlace> adapter = new FunDapter<>(PlaceResult.this, tourList, R.layout.layout_list, dict);
                listView = (ListView) findViewById(R.id.lvRestuarent);
                listView.setAdapter(adapter);
            }
        });
        taskRead.execute("http://guideme.orgfree.com/customer/product.php");

    }

    public void onShowMapClick() {
        btnShow = (Button) findViewById(R.id.button_show);
        btnShow.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent("com.example.hishara.mapapp.MapsActivity");
                        startActivity(intent);
                    }
                }
        );

        //onFillClick();
    }

    public void onFillClick(){
        btnFilter = (Button) findViewById(R.id.button_fill);
        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(PlaceResult.this, btnFilter);
                //Inflating the Popup using xml file
                popup.getMenuInflater()
                        .inflate(R.menu.popup_menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(
                                PlaceResult.this,
                                "You Clicked : " + item.getTitle(),
                                Toast.LENGTH_SHORT
                        ).show();
                        return true;
                    }
                });

                popup.show(); //showing popup menu
            }
        }); //closing the setOnClickListener method

    }


    public float findDistance(Double lat1, Double lon1, Double lat2, Double lon2) {
        Location loc1 = new Location("");
        loc1.setLatitude(lat1);
        loc1.setLongitude(lon1);

        Location loc2 = new Location("");
        loc2.setLatitude(lat2);
        loc2.setLongitude(lon2);

        float distanceInMeters = loc1.distanceTo(loc2);
        return distanceInMeters;
    }

    public void removeUnWantedResults(ArrayList<TouristPlace> tp, float radius) {

        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
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
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Double longitude = Double.parseDouble(location.getLongitude()+"");
        Double latitude = Double.parseDouble(location.getLatitude()+"");

        for(int x=0;x<tp.size();x++){
            num = findDistance(Double.parseDouble("6.796652"), Double.parseDouble("79.9003355"), Double.parseDouble(tp.get(x).getLat()), Double.parseDouble(tp.get(x).getLang())) / 1000;
            if(num>radius){
                Toast.makeText(PlaceResult.this, "true", Toast.LENGTH_LONG).show();
                Log.d(LOG, num + "################################");
                tp.remove(x);
            }
//            if(15.0000f>10f){
//                Toast.makeText(PlaceResult.this, "true", Toast.LENGTH_LONG).show();
//            }
            else{
                Toast.makeText(PlaceResult.this, "false", Toast.LENGTH_LONG).show();
            }

        }
    }

}
