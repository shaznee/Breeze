package com.shaznee.breeze.providers.location;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;

import java.io.IOException;

/**
 * Created by Shaznee on 22-May-16.
 */
public class LocationProvider extends LocationAddress implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private static final String TAG = LocationProvider.class.getSimpleName();

    private LocationChangeCallBack locationChangeCallBack;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;

    public LocationProvider(Context context, LocationChangeCallBack callback) {
        super(context);
        this.googleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .build();

        this.locationChangeCallBack = callback;

        // Create the LocationRequest object
        this.locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000); // 1 second, in milliseconds
    }

    public GoogleApiClient getGoogleApiClient() {
        return googleApiClient;
    }

    public void connect() {
        googleApiClient.connect();
    }

    public void disconnect() {
        if (googleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
            googleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        boolean fineLocationEnabled = ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        boolean coarseLocationEnabled = ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        if (fineLocationEnabled && coarseLocationEnabled) {
            Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            if (location == null) {
                Log.i(TAG, "On connected using fused location API");
                LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
            } else {
                Log.i(TAG, "On connected handling new location");
                try {
                    if (locationChangeCallBack != null) {
                        locationChangeCallBack.handleNewLocation(getCityName(location.getLatitude(),location.getLongitude()),location.getLatitude(), location.getLongitude());
                    }
                } catch (IOException e) {
                    Log.d(TAG, "IOException : ", e);
                }
            }
            Log.i(TAG, "Location services connected.");
        } else {
            Toast.makeText(context, "Please enable GPS permissions", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Location services suspended. Please reconnect.");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(TAG, "Location services connection failed with code " + connectionResult.getErrorCode());
    }

    @Override
    public void onLocationChanged(Location location) {
        try {
            if (locationChangeCallBack != null) {
                locationChangeCallBack.handleNewLocation(getCityName(location.getLatitude(), location.getLongitude()), location.getLatitude(), location.getLongitude());
            }
        } catch (IOException e) {
            Log.d(TAG, "IOException : ", e);
        }
    }

    public void getPlaceById(String placeId, final PlaceCallback placeCallback) {
        Places.GeoDataApi.getPlaceById(googleApiClient, placeId)
                .setResultCallback(new ResultCallback<PlaceBuffer>() {
                    @Override
                    public void onResult(PlaceBuffer places) {
                        if (places.getStatus().isSuccess() && places.getCount() > 0) {
                            placeCallback.onPlaceFound(places.get(0));
                        } else {
                           Log.d(TAG, "Predicted Place not found");
                        }
                        places.release();
                    }
                });
    }

}
