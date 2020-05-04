package com.example.homework3;
//Andre barajas, Hunter
//Google maps location finder project
//Spring 2020
import com.example.homework3.MarkerLocation;

public interface SendListEventsCallBack {
    //callback method for interface used in classes to pass events
    void recyclerCallbackFn(MarkerLocation location);
}
