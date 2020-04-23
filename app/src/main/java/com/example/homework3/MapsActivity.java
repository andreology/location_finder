package com.example.homework3;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.Map;

public class MapsActivity extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String response;
    public static String RES_KEY = "response_key";
    private static final String TAG = "main";

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.activity_maps, container, false);
        Bundle args = getArguments();
        if(args != null) {
            String response = args.getString(RES_KEY);
            Log.w(TAG, "LOCATION  " + response);
               try {
                JSONObject object = new JSONObject(response);
                JSONArray results = (JSONArray) object.getJSONArray("results");
              //  JSONObject geo = (JSONObject) results.getJSONArray("geometry");
                Log.w(TAG, "RESULTS00  " + results);

//                for (int i = 0; i < results.length(); i++) {
//                    Log.w(TAG, "INDEX  " + i);
//                    results.getJSONObject(i);
//                }
                Log.w(TAG, "LOCATION0  " + results);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return view;
        //return inflater.inflate(R.layout.activity_maps, container, false);
    }
   // @Override
//    protected void onCreate(Bundle savedInstanceState) {
//       // super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_maps);
//        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
//    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        //Log.w(TAG, "IN MAP READY  ");
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
