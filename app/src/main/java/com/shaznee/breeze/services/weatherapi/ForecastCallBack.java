package com.shaznee.breeze.services.weatherapi;

import com.shaznee.breeze.services.weather.Forecast;

/**
 * Created by Shaznee on 22-May-16.
 */
public interface ForecastCallBack {

    void successful(Forecast forecast);

    void failure(Throwable throwable);
}
