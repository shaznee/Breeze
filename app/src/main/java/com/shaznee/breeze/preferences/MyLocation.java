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

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String toJSON() throws JSONException {

        JSONObject jsonObject = new JSONObject()
                .put(LATITUDE_KEY, latitude)
                .put(LONGITUDE_KEY, longitude);

        return jsonObject.toString();
    }

    public static MyLocation fromJSON(String cityName, String jsonString) throws JSONException {
        MyLocation myLocation = new MyLocation();
        JSONObject jsonObject = new JSONObject(jsonString);
        myLocation.setCityName(cityName);
        myLocation.setLatitude(jsonObject.getDouble(LATITUDE_KEY));
        myLocation.setLongitude(jsonObject.getLong(LONGITUDE_KEY));
        return myLocation;
    }
}
