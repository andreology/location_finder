package com.example.homework3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.EditText;
import android.widget.SearchView;

import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {
    private SearchView search;
    private static final String TAG = "main";
    private HttpHandler get;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        search = findViewById(R.id.search_view);

        search.setOnQueryTextListener(
                new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        Log.w(TAG, "search input: " + query);
                        get = new HttpHandler();

                        get.doInBackground(query);
                        //System.out.println("TEST: " + test);
                        StringTokenizer address = new StringTokenizer(query);
                        Log.w(TAG, "number of tokens " + address.countTokens());
                        int tokenCounter = 1;
                        StringBuilder req = new StringBuilder("https://maps.googleapis.com/maps/api/geocode/json?address=");
                        req.append(query.trim().replace(" ", "") + "+");
//                        while(address.hasMoreTokens()) {
//                                if(tokenCounter == 1) { req.append(address.nextToken());
//                                }else { req.append("+" + address.nextToken()); }
//                                tokenCounter +=1;
//                        }
                        req.append("&key=APIKEY");
                        String geoCodeUrl = new String(req);
                        // Async call here:
                        String response = get.doInBackground(geoCodeUrl);
//                        Log.w(TAG, "req at " + req);
//                        Log.w(TAG, "fetch response " + response);

                        Bundle bundle = new Bundle();
                        bundle.putString(MapsActivity.RES_KEY, response);

                        MapsActivity fragment = new MapsActivity();
                        fragment.setArguments(bundle);
                        // set Fragment class Arguments

                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.map, fragment)
                                .commit();

                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        Log.w(TAG, "search text changed to : " + newText);
                        return false;
                    }
                }
        );
    }

}



