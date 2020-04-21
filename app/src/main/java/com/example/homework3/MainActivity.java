package com.example.homework3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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
        search = findViewById(R.id.search_view);

        search.setOnQueryTextListener(
                new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        Log.w(TAG, "search input: " + query);
                        get = new HttpHandler();
                        get.makeServicesCall(query);
                        StringTokenizer address = new StringTokenizer(query);
                        Log.w(TAG, "number of tokens " + address.countTokens());
                        int tokenCounter = 1;
                        StringBuilder req = new StringBuilder("https://maps.googleapis.com/maps/api/geocode/json?address=");
                        while(address.hasMoreTokens()) {
                                if(tokenCounter == 1) { req.append(address.nextToken());
                                }else { req.append("+" + address.nextToken()); }
                                tokenCounter +=1;
                        }
                        req.append("&key=AIzaSyAx5q_BkPjuQVIcneGBm74Qc_2F_bqLkVc");
                        String geoCodeUrl = new String(req);
                        String response = get.makeServicesCall(geoCodeUrl);
                        Log.w(TAG, "req at " + req);
                        Log.w(TAG, "fetch response " + response);
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



