package com.shaznee.breeze.services;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by SHAZNEE on 14-Jul-16.
 */
public class WeatherUpdaterService extends IntentService {

    public WeatherUpdaterService() {
        super("WeatherUpdater");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }
}
