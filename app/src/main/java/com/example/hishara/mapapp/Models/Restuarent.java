package com.example.hishara.mapapp.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hishara on 4/27/2016.
 */
public class Restuarent {



    public Restuarent(int rest_id, String rest_name, String longitude, String latitude, int rating, String img_url) {
        this.rest_id = rest_id;
        this.rest_name = rest_name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.rating = rating;
        this.img_url = img_url;
    }

    @SerializedName("rest_id")
    private int rest_id;

    @SerializedName("rest_name")
    private String rest_name;

    @SerializedName("longitude")
    private String longitude;

    @SerializedName("latitude")
    private String latitude;

    @SerializedName("rating")
    private int rating;

    @SerializedName("img_url")
    private String img_url;

    public int getRest_id() {
        return rest_id;
    }

    public void setRest_id(int rest_id) {
        this.rest_id = rest_id;
    }

    public String getRest_name() {
        return rest_name;
    }

    public void setRest_name(String rest_name) {
        this.rest_name = rest_name;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}
