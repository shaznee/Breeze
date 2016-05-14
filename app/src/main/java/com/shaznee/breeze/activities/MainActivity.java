package com.shaznee.breeze.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.shaznee.breeze.R;
import com.shaznee.breeze.api.WeatherAPI;
import com.shaznee.breeze.models.Forecast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String BASE_URL = "https://api.forecast.io";
    private final String apiKey = "e738670a91beb00176965e46a062ce23";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        double latitude = 37.8267;
        double longitude = -122.423;
        
        if (isOnline()) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build();

            WeatherAPI weatherAPI = retrofit.create(WeatherAPI.class);
            weatherAPI.getForecast(apiKey,latitude,longitude).enqueue(new Callback<Forecast>() {
                @Override
                public void onResponse(Call<Forecast> call, Response<Forecast> response) {
                    if (response.isSuccessful()) {
                        Forecast forecast = response.body();
                        Log.v(TAG, "Timezone:" + forecast.getTimezone());
                    } else {
                        showAlertDialog(getString(R.string.error_title), getString(R.string.error_message));
                    }
                }

                @Override
                public void onFailure(Call<Forecast> call, Throwable t) {

                }
            });
        } else {
            Toast.makeText(this, getString(R.string.no_network_string), Toast.LENGTH_LONG).show();
        }
        Log.v(TAG, "Main UI Code is running");
    }

    private boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    private void showAlertDialog(String title, String message) {

        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.dialog_ok), null)
                .create();

        alertDialog.show();
    }
}
