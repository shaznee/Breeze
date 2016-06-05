package com.shaznee.breeze.preferences;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Shaznee on 23-May-16.
 */
public class MyLocation implements Parcelable {

    private static final String LATITUDE_KEY = "lat";
    private static final String LONGITUDE_KEY = "lon";

    private String cityName;
    private double latitude;
    private double longitude;

    public MyLocation(String cityName, double latitude, double longitude) {
        this.cityName = cityName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getCityName() {
        return cityName;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    @Override
    public String toString() {
        return new StringBuilder("City Name : ")
                .append(this.cityName)
                .append("Latitude : ")
                .append(this.latitude)
                .append("Longitude ")
                .append(this.longitude)
                .toString();
    }

    public String toJSON() throws JSONException {

        JSONObject jsonObject = new JSONObject()
                .put(LATITUDE_KEY, latitude)
                .put(LONGITUDE_KEY, longitude);

        return jsonObject.toString();
    }

    public static MyLocation fromJSON(String cityName, String jsonString) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonString);
        return new MyLocation(cityName, jsonObject.getDouble(LATITUDE_KEY),jsonObject.getDouble(LONGITUDE_KEY) );
    }

    //*********Parcelable Implemetnation **********//

    protected MyLocation(Parcel in) {
        this.cityName = in.readString();
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.cityName);
        dest.writeDouble(this.latitude);
        dest.writeDouble(this.longitude);
    }

    public static final Creator<MyLocation> CREATOR = new Creator<MyLocation>() {
        @Override
        public MyLocation createFromParcel(Parcel in) {
            return new MyLocation(in);
        }

        @Override
        public MyLocation[] newArray(int size) {
            return new MyLocation[size];
        }
    };
}
