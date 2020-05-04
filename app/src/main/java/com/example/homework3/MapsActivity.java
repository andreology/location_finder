//Andre barajas, Hunter
//Google maps location finder project
//Spring 2020
package com.example.homework3;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;
import java.io.IOException;
import java.util.ArrayList;

//Class to set up Maps activity and list locations requested by user
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, SendListEventsCallBack {
    private EditText tEditSearch;
    private Geocoder tGeoCode;
    private List<MarkerLocation> tSaveSearchedLocations;
    private RecyclerView mRecyclerList;
    private LocationHolder mRecyclerListAdapter;
    private GoogleMap mMap;
    //setting up a default location for map to place marker at
    static final LatLng QMARY = new LatLng(33.7526d, -118.1903d);
    //allows zooming from 2 to twenty one
    static final float ALLOW_ZOOM = 10f;
    //setting up async to call map while app loads
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        //wait till map is ready before starting fragment
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_view);
        mapFragment.getMapAsync(this);
    }
    //method to display map and show search box using edittext.
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.addMarker(new MarkerOptions()
                .position(QMARY)
                .title(getResources().getString(R.string.default_marker)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(QMARY));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(ALLOW_ZOOM));
        //using java geocode library to decode addresses
        tGeoCode = new Geocoder(this);
        tSaveSearchedLocations = new ArrayList<>();
        tSaveSearchedLocations.add(new MarkerLocation(QMARY,getResources().getString(R.string.default_marker)));
        //get edittextg widget to check user input
        tEditSearch = findViewById(R.id.edittext_view);
        tEditSearch.setInputType(InputType.TYPE_NULL);
        //https://stackoverflow.com/questions/8063439/android-edittext-finished-typing-event
        //wait until user is done typing and hits enter to begin searching
        tEditSearch.setOnEditorActionListener(
                new EditText.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                                actionId == EditorInfo.IME_ACTION_DONE ||
                                event != null &&
                                        event.getAction() == KeyEvent.ACTION_DOWN &&
                                        event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                            if (event == null || !event.isShiftPressed()) {
                                // the user is done typing.
                                findLocationThenMark();
                                return true; // consume.
                            }
                        }
                        return false; // pass on to other listeners.
                    }
                }
        );
        //Create a list of locations being entered into "database"
        mRecyclerList = findViewById(R.id.recycler_list);
        mRecyclerListAdapter = new LocationHolder(this, tSaveSearchedLocations);
        //pass adapter to class
        mRecyclerList.setAdapter(mRecyclerListAdapter);
        mRecyclerList.setLayoutManager(new LinearLayoutManager(this));
    }
    //method to move camera to new location, if location exists marker will be set
    //otherwise error message should pop up on UI
    void findLocationThenMark() {
        //Change map view to show new location
        String locus = tEditSearch.getText().toString();
        if (locus.isEmpty()) { return; }
        tEditSearch.setText("");
        // try catch block to use java geo code and find lat and long of address provided
        try {
            List<Address> locations = tGeoCode.getFromLocationName(locus, 1);
            //throw error message
            if(locations == null) { throw new IOException("Error: The location you entered doesn't exist, try again. "); }
            Address foundAddress = locations.get(0);
            //store lat and long of new locus
            LatLng markPlace = new LatLng(foundAddress.getLatitude(), foundAddress.getLongitude());
            mMap.addMarker(new MarkerOptions().position(markPlace).title(locus).snippet(" "));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(markPlace));
            //save to database
            tSaveSearchedLocations.add(new MarkerLocation(markPlace, locus));
            mRecyclerListAdapter.notifyItemInserted(tSaveSearchedLocations.size() - 1);
        } catch (Exception e) {
            Toast.makeText(this, "\"" + locus + "\" NOT A VALID input", Toast.LENGTH_LONG).show();
        }
    }
    //callback method from professor fahim lecture needed for sending events between activities and fragments.
    public void recyclerCallbackFn(MarkerLocation location) {
        //passing call back data
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location.tLatandLong));
    }
}
