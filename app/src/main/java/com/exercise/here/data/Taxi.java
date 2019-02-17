package com.exercise.here.data;

import com.google.gson.annotations.SerializedName;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Taxi {

    public Taxi(int id, String stationName, String imageUrl, int eta) {
        this.id = id;
        this.stationName = stationName;
        this.imageUrl = imageUrl;
        this.eta = eta;
    }

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    private int id;

    @ColumnInfo (name = "station_name")
    @SerializedName("station_name")
    private String stationName;

    @ColumnInfo (name = "image_url")
    @SerializedName("image_url")
    private String imageUrl;

    @ColumnInfo (name = "eta")
    @SerializedName("eta_sec")
    private int eta;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getEta() {
        return eta;
    }

    public Taxi setEta(int eta) {
        this.eta = eta;
        return this;
    }

}