package com.shaznee.breeze.weatherservice;

import com.shaznee.breeze.models.weather.Forecast;

/**
 * Created by Shaznee on 22-May-16.
 */
public interface ForecastCallBack {

    void successful(Forecast forecast);

    void failure(Throwable throwable);
}
