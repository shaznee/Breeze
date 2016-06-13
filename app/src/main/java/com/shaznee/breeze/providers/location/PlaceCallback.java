package com.shaznee.breeze.providers.location;

import com.google.android.gms.location.places.Place;

/**
 * Created by SHAZNEE on 13-Jun-16.
 */
public interface PlaceCallback {

    void onPlaceFound(Place place);

    void onPlaceNotFound(Throwable throwable);
}
