package com.kwabenaberko.cointrack.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Kwabena Berko on 8/22/2018.
 */

@Entity(tableName = "coins")
public class Coin {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo
    private double cap24hrChange;

    @ColumnInfo
    @SerializedName("long")
    private String longName;

    @ColumnInfo
    private double perc;

    @ColumnInfo
    private double price;

    @ColumnInfo
    @SerializedName("short")
    private String shortName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getCap24hrChange() {
        return cap24hrChange;
    }

    public void setCap24hrChange(double cap24hrChange) {
        this.cap24hrChange = cap24hrChange;
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }


    public double getPerc() {
        return perc;
    }

    public void setPerc(double perc) {
        this.perc = perc;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

}
