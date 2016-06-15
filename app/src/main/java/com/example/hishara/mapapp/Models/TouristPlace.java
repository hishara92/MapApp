package com.example.hishara.mapapp.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hishara on 4/27/2016.
 */
public class TouristPlace {
    @SerializedName("id")
    private int id;
    @SerializedName("Name")
    private String name;
    @SerializedName("Address")
    private String address;
    @SerializedName("catagory")
    private String catagory;
    @SerializedName("lat")
    private String lat;
    @SerializedName("lang")
    private String lang;
    @SerializedName("image_url")
    private String img_url;

    public TouristPlace(int id, String name, String address, String catagory, String lat, String img_url, String lang) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.catagory = catagory;
        this.lat = lat;
        this.img_url = img_url;
        this.lang = lang;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getCatagory() {
        return catagory;
    }

    public void setCatagory(String catagory) {
        this.catagory = catagory;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}
