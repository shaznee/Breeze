package com.shaznee.breeze.location;

/**
 * Created by Shaznee on 22-May-16.
 */
public interface LocationHandler {

    void handleNewLocation(String cityName, double latitude, double longitude);

}
