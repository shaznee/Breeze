package com.shaznee.breeze.weatherservice;

import com.shaznee.breeze.models.Forecast;

/**
 * Created by Shaznee on 22-May-16.
 */
public interface ForecastHandler {

    void successful(Forecast forecast);

    void failure();
}
