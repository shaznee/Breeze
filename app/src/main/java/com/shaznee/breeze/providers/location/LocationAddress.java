package com.shaznee.breeze.providers.location;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by Shaznee on 02-Jun-16.
 */
public abstract class LocationAddress {

    protected Context context;
    private Geocoder geocoder;

    protected LocationAddress(Context context) {
        this.context = context;
        geocoder = new Geocoder(context, Locale.getDefault());
    }

    protected String getCityName(double latitude, double longitude) throws IOException {
        StringBuilder builder = new StringBuilder();
        List<Address> address = geocoder.getFromLocation(latitude, longitude, 1);
        builder.append(address.get(0).getLocality());
        return builder.toString();
    }

}
