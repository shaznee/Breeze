package com.shaznee.breeze.preferences;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Shaznee on 23-May-16.
 */
public class MyLocation {

    private static final String LATITUDE_KEY = "lat";
    private static final String LONGITUDE_KEY = "lon";

    private String cityName;
    private double latitude, longitude;

    public MyLocation(String cityName, double latitude, double longitude) {
        this.cityName = cityName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public String toJSON() throws JSONException {

        JSONObject jsonObject = new JSONObject()
                .put(LATITUDE_KEY, latitude)
                .put(LONGITUDE_KEY, longitude);

        return jsonObject.toString();
    }

    public static MyLocation fromJSON(String cityName, String jsonString) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonString);
        return new MyLocation(cityName, jsonObject.getDouble(LATITUDE_KEY),jsonObject.getLong(LONGITUDE_KEY) );
    }
}
