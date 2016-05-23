package com.shaznee.breeze.locationservice;

/**
 * Created by Shaznee on 22-May-16.
 */
public interface LocationHandler {

    void handleNewLocation(String cityName, double latitude, double longitude);

}
