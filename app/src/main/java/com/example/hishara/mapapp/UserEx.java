package com.example.hishara.mapapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.amigold.fundapter.BindDictionary;
import com.amigold.fundapter.FunDapter;
import com.amigold.fundapter.extractors.StringExtractor;
import com.amigold.fundapter.interfaces.DynamicImageLoader;
import com.example.hishara.mapapp.Models.NewStatus;
import com.example.hishara.mapapp.Models.TouristPlace;
import com.kosalgeek.android.json.JsonConverter;
import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class UserEx extends AppCompatActivity {

    private ArrayList<NewStatus> statusList;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_ex);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("com.example.hishara.mapapp.MapsActivity3");
                startActivity(intent);
            }
        });
        statusUpdate();
    }


    public void statusUpdate() {

        PostResponseAsyncTask taskRead = new PostResponseAsyncTask(UserEx.this, new AsyncResponse() {
            @Override
            public void processFinish(String s) {
                //Log.d(LOG,s);
                statusList = new JsonConverter<NewStatus>().toArrayList(s, NewStatus.class);
                //removeUnWantedResults(tourList, 10);
                BindDictionary<NewStatus> dict = new BindDictionary<NewStatus>();


                dict.addStringField(R.id.tvStatus, new StringExtractor<NewStatus>() {
                    @Override
                    public String getStringValue(NewStatus item, int position) {
                        return item.getStatus();
                    }
                });

                dict.addStringField(R.id.tvUserName, new StringExtractor<NewStatus>() {
                    @Override
                    public String getStringValue(NewStatus item, int position) {
                        return "Hishara Shared his Experience";
                    }
                });

                final ImageView iv = (ImageView) findViewById(R.id.ivImage);
                dict.addDynamicImageField(R.id.ivImage, new StringExtractor<NewStatus>() {
                    @Override
                    public String getStringValue(NewStatus item, int position) {
                        return item.getPicURL1();
                    }
                }, new DynamicImageLoader() {
                    @Override
                    public void loadImage(String url, ImageView view) {
                        Picasso.with(UserEx.this)
                                .load(url)
                                .placeholder(android.R.drawable.star_big_on)
                                .error(android.R.drawable.stat_sys_download).into(view);
//                        iv.setPadding(0,0,0,0);
//                        iv.setAdjustViewBounds(true);
                    }
                });




                FunDapter<NewStatus> adapter = new FunDapter<>(UserEx.this, statusList, R.layout.layout_exlist, dict);
                listView = (ListView) findViewById(R.id.lvStatus);
                listView.setAdapter(adapter);
            }
        });
        taskRead.execute("http://guideme.orgfree.com/customer/status.php");
    }

}
