package com.example.homework3;
//Andre barajas, Hunter
//Google maps location finder project
//Spring 2020
import com.google.android.gms.maps.model.LatLng;

public class MarkerLocation {
    public String mTitle;
    public LatLng tLatandLong;
    //constructor to set up private instances
    public MarkerLocation(LatLng latLng, String title) {
        tLatandLong = latLng;
        mTitle = title;
    }
}
