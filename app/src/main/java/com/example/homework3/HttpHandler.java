package com.example.homework3;


import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Class that handles the retrieval of data from the API (website) in JSON form
 */
public class HttpHandler extends AsyncTask<String, Void, String> {

    private static final String TAG = HttpHandler.class.getSimpleName();

    public HttpHandler(){

    }

    @Override
    protected String doInBackground(String... strings) {
        String reqUrl = strings[0];
        String response = null;
        Log.w(TAG, "IN MAKE SERVICE CALL " + reqUrl);
        try{

            URL url = new URL(reqUrl);
            Log.w(TAG, "IN MAKE TRY CATCH BLOCK  " + url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            // conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            Log.w(TAG, "CONN URL  " + conn.toString());
            // read the response
            InputStream in = new BufferedInputStream(conn.getInputStream());

            response = convertStreamToString(in);
            System.out.println("response0 " + response);
        } catch (ProtocolException | MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }

//    private String makeServicesCall(String reqUrl){
//        String response = null;
//        Log.w(TAG, "IN MAKE SERVICE CALL " + reqUrl);
//        try{
//
//            URL url = new URL(reqUrl);
//            Log.w(TAG, "IN MAKE TRY CATCH BLOCK  " + url);
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setRequestMethod("GET");
//           // conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//            Log.w(TAG, "CONN URL  " + conn.toString());
//            // read the response
//            InputStream in = new BufferedInputStream(conn.getInputStream());
//
//            response = convertStreamToString(in);
//            System.out.println("response0 " + response);
//        } catch (ProtocolException | MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//        return response;
//    }

    private String convertStreamToString(InputStream is){
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try{
            while ((line = reader.readLine()) != null){
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try{
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
