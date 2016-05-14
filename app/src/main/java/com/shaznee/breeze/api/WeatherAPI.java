package com.shaznee.breeze.api;

import com.shaznee.breeze.models.Forecast;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Shaznee on 12-May-16.
 */
public interface WeatherAPI {

    @GET("/forecast/{apiKey}/{lat},{lon}")
    Call<Forecast> getForecast(@Path("apiKey") String apiKey, @Path("lat") double latitude,
                               @Path("lon") double longitude);
}
