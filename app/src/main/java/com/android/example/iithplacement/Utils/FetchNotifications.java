package com.android.example.iithplacement.Utils;

import android.util.Log;

import com.android.example.iithplacement.DateEvent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by om on 7/12/17.
 */

public class FetchNotifications {




    public  static ArrayList<DateEvent> fetchBooksData(String request_url) {


        URL url=createUrl(request_url);
        String jsonResponse="";
        jsonResponse=makeHttpRequest(url);
        Log.v("This is the response", jsonResponse);
        ArrayList<DateEvent> dateList=fetchBooks(jsonResponse);
        return  dateList;

    }

    public  static ArrayList<DateEvent> fetchAnnouncementData(String request_url) {


        URL url=createUrl(request_url);
        String jsonResponse="";
        jsonResponse=makeHttpRequest(url);
        Log.v("This is the response", jsonResponse);
        ArrayList<DateEvent> dateList=fetchAnnouncement(jsonResponse);
        return  dateList;

    }

    public  static DateEvent fetchFirstEvent(String request_url) {


        URL url=createUrl(request_url);
        String jsonResponse="";
        jsonResponse=makeHttpRequest(url);
        Log.v("This is the response", jsonResponse);
        DateEvent dateEvent=fetchEvent(jsonResponse);
        return  dateEvent;

    }

    public  static DateEvent fetchFirstAnnouncement(String request_url) {


        URL url=createUrl(request_url);
        String jsonResponse="";
        jsonResponse=makeHttpRequest(url);
        Log.v("This is the response", jsonResponse);
        DateEvent dateEvent=fetchAnnouncementSingle(jsonResponse);
        return  dateEvent;

    }

    

    private static URL createUrl(String stringUrl){
        URL url=null;
        try {
            url=new URL(stringUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
        return url;
    }

    private static String makeHttpRequest(URL url){
        String jsonResponse="";
        if(url==null){
            return jsonResponse;
        }
        HttpURLConnection httpURLConnection=null;
        InputStream inputStream=null;
        try {
            httpURLConnection=(HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.connect();
            if(httpURLConnection.getResponseCode()==200){
                inputStream = httpURLConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }else{
                //Log.e(LOG_TAG,"Error response code: " + httpURLConnection.getResponseCode());
            }



        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(url!=null){
                httpURLConnection.disconnect();
            }
            if(inputStream!=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream)throws IOException {
        StringBuilder output=new StringBuilder();
        if(inputStream!=null){
            InputStreamReader inputStreamReader=new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
            String line=bufferedReader.readLine();
            while(line!=null){
                output.append(line);
                line=bufferedReader.readLine();
            }
        }
        return  output.toString();
    }

    public static ArrayList<DateEvent> fetchBooks(String jsonResponse){
        ArrayList<DateEvent> datelist=new ArrayList<>();
        try {
            JSONObject jsonObject=new JSONObject(jsonResponse);
            JSONArray eventsArray=new JSONArray();
            try{
                eventsArray=jsonObject.getJSONArray("Sheet1");
            }catch (JSONException ignored){

            }

            for (int i = eventsArray.length() - 1; i >= 0 ; i--){
                JSONObject currentEvent= eventsArray.getJSONObject(i);
                String date = currentEvent.getString("date");
                String event = currentEvent.getString("event");

                DateEvent dateEvent=new DateEvent(date,event);
                datelist.add(dateEvent);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return datelist;
    }

    public static ArrayList<DateEvent> fetchAnnouncement(String jsonResponse){
        ArrayList<DateEvent> datelist=new ArrayList<>();
        try {
            JSONObject jsonObject=new JSONObject(jsonResponse);
            JSONArray eventsArray=new JSONArray();
            try{
                eventsArray=jsonObject.getJSONArray("Sheet1");
            }catch (JSONException ignored){

            }

            for (int i = eventsArray.length() - 1; i >= 0 ; i--){
                JSONObject currentEvent= eventsArray.getJSONObject(i);
                String date = currentEvent.getString("Date");
                String event = currentEvent.getString("Announcement");
                String post = currentEvent.getString("Timestamp");
                String authority = currentEvent.getString("Authority_Name");

                DateEvent dateEvent=new DateEvent(date,event,authority,post);
                datelist.add(dateEvent);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return datelist;
    }

    public static DateEvent fetchEvent(String jsonResponse){
        DateEvent dateEvent = null;
        try {
            JSONObject jsonObject=new JSONObject(jsonResponse);
            JSONArray eventsArray=new JSONArray();
            try{
                eventsArray=jsonObject.getJSONArray("Sheet1");
            }catch (JSONException ignored){

            }

            JSONObject firstEvent = eventsArray.getJSONObject(eventsArray.length() - 1);
            String date = firstEvent.getString("date");
            String event = firstEvent.getString("event");

            dateEvent = new DateEvent(date,event);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return dateEvent;
    }

    public static DateEvent fetchAnnouncementSingle(String jsonResponse){
        DateEvent dateEvent = null;
        try {
            JSONObject jsonObject=new JSONObject(jsonResponse);
            JSONArray eventsArray=new JSONArray();
            try{
                eventsArray=jsonObject.getJSONArray("Sheet1");
            }catch (JSONException ignored){

            }

            JSONObject firstEvent = eventsArray.getJSONObject(eventsArray.length() - 1);
            String date = firstEvent.getString("Date");
            String event = firstEvent.getString("Announcement");

            dateEvent = new DateEvent(date,event);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return dateEvent;
    }


}
