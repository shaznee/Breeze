package com.shaznee.breeze.providers.location;

/**
 * Created by Shaznee on 22-May-16.
 */
public interface LocationChangeCallBack {

    void handleNewLocation(String cityName, double latitude, double longitude);

}
