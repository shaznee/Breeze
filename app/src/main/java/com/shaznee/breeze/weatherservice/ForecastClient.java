package com.shaznee.breeze.weatherservice;

import android.util.Log;

import com.shaznee.breeze.weatherservice.api.WeatherAPI;
import com.shaznee.breeze.models.weather.Forecast;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by Shaznee on 22-May-16.
 */
public class ForecastClient {

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

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        weatherAPI = retrofit.create(WeatherAPI.class);
    }

    public void getForecast(double latitude, double longitude, final ForecastCallBack forecastCallBack) {
        weatherAPI.getForecast(apiKey, latitude, longitude).enqueue(new Callback<Forecast>() {
            @Override
            public void onResponse(Call<Forecast> call, Response<Forecast> response) {
                if (response.isSuccessful()) {
                    forecastCallBack.successful(response.body());
                } else {
                    Log.e(TAG, "On response failure");
                }
            }

            @Override
            public void onFailure(Call<Forecast> call, Throwable t) {
                forecastCallBack.failure(t);
            }
        });
    }
}
