package com.shaznee.breeze.services.weatherapi;

import android.util.Log;

import com.shaznee.breeze.services.weather.Forecast;
import com.shaznee.breeze.services.weatherapi.api.WeatherAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by Shaznee on 22-May-16.
 */
public class ForecastClient {

    public interface CallBack {
        void successful(Forecast forecast);
        void failure(Throwable throwable);
    }

    private static final String TAG = ForecastClient.class.getSimpleName();

    private static final String BASE_URL = "https://api.forecast.io";
    private final String apiKey = "ef388c6f41ff45add4bae9c3562b2304";

    private WeatherAPI weatherAPI;

    private static ForecastClient instance;

    public static ForecastClient getInstance() {
        if (instance == null) {
            instance = new ForecastClient();
        }
        return instance;
    }

    private ForecastClient() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        weatherAPI = retrofit.create(WeatherAPI.class);
    }

    public void getForecast(double latitude, double longitude, final CallBack callBack) {
        weatherAPI.getForecast(apiKey, latitude, longitude).enqueue(new Callback<Forecast>() {
            @Override
            public void onResponse(Call<Forecast> call, Response<Forecast> response) {
                if (response.isSuccessful()) {
                    callBack.successful(response.body());
                } else {
                    Log.e(TAG, "On response failure");
                }
            }

            @Override
            public void onFailure(Call<Forecast> call, Throwable t) {
                callBack.failure(t);
            }
        });
    }
}
