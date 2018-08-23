package com.kwabenaberko.cointrack;

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
    private double cap24hrChange;

    @SerializedName("long")
    @ColumnInfo()
    private String longName;

    private double mktcap;
    private double perc;
    private double price;
    private boolean shapeshift;

    @ColumnInfo()
    @SerializedName("short")
    private String shortName;
    private double supply;
    private double volume;
    private double usdVolume;
    private double vwapData;
    private double vwapDataBTC;

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

    public double getMktcap() {
        return mktcap;
    }

    public void setMktcap(double mktcap) {
        this.mktcap = mktcap;
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

    public boolean isShapeshift() {
        return shapeshift;
    }

    public void setShapeshift(boolean shapeshift) {
        this.shapeshift = shapeshift;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public double getSupply() {
        return supply;
    }

    public void setSupply(double supply) {
        this.supply = supply;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getUsdVolume() {
        return usdVolume;
    }

    public void setUsdVolume(double usdVolume) {
        this.usdVolume = usdVolume;
    }

    public double getVwapData() {
        return vwapData;
    }

    public void setVwapData(double vwapData) {
        this.vwapData = vwapData;
    }

    public double getVwapDataBTC() {
        return vwapDataBTC;
    }

    public void setVwapDataBTC(double vwapDataBTC) {
        this.vwapDataBTC = vwapDataBTC;
    }
}
