package com.shaznee.breeze.models.location;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Shaznee on 23-May-16.
 */
public class MyLocation implements Parcelable {

    private static final String LATITUDE_KEY = "latitude";
    private static final String LONGITUDE_KEY = "longitude";
    private static final String PRIMARY_TEXT_KEY = "primaryText";
    private static final String SECONDARY_TEXT_KEY = "secondaryText";

    private String placeId;
    private String primaryText;
    private String secondaryText;
    private double latitude;
    private double longitude;

    public MyLocation(String placeId, double latitude, double longitude,
                      String primaryText, String secondaryText) {

        this.placeId = placeId;
        this.primaryText = primaryText;
        this.secondaryText = secondaryText;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getPlaceId() {
        return placeId;
    }

    public String getPrimaryText() {
        return primaryText;
    }

    public String getSecondaryText() {
        return secondaryText;
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
                .append(this.primaryText)
                .append("Latitude : ")
                .append(this.latitude)
                .append("Longitude ")
                .append(this.longitude)
                .toString();
    }

    public String toJSON() throws JSONException {

        JSONObject jsonObject = new JSONObject()
                .put(PRIMARY_TEXT_KEY, primaryText)
                .put(SECONDARY_TEXT_KEY, secondaryText)
                .put(LATITUDE_KEY, latitude)
                .put(LONGITUDE_KEY, longitude);

        return jsonObject.toString();
    }

    public static MyLocation fromJSON(String placeId, String jsonString) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonString);
        return new MyLocation(placeId,
                jsonObject.getDouble(LATITUDE_KEY),
                jsonObject.getDouble(LONGITUDE_KEY),
                jsonObject.getString(PRIMARY_TEXT_KEY),
                jsonObject.getString(SECONDARY_TEXT_KEY));
    }

    //*********Parcelable Implemetnation **********//

    protected MyLocation(Parcel in) {
        placeId = in.readString();
        primaryText = in.readString();
        secondaryText = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(placeId);
        dest.writeString(primaryText);
        dest.writeString(secondaryText);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
    }

    @Override
    public int describeContents() {
        return 0;
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
