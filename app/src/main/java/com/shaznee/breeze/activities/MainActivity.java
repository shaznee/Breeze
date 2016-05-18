package com.shaznee.breeze.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.shaznee.breeze.R;
import com.shaznee.breeze.api.WeatherAPI;
import com.shaznee.breeze.models.Forecast;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.timeLabel) TextView timeLabel;
    @BindView(R.id.temperatureLabel) TextView temperatureLabel;
    @BindView(R.id.humidityValue) TextView humidityValue;
    @BindView(R.id.precipValue) TextView precipValue;
    @BindView(R.id.summaryLabel) TextView summaryLabel;
    @BindView(R.id.iconImageView) ImageView iconImageView;
    @BindView(R.id.refreshImageView) ImageView refreshImageView;
    @BindView(R.id.progressBar) ProgressBar progressBar;

    public static final String FORECAST = "FORECAST";
    private static final String BASE_URL = "https://api.forecast.io";
    private final String apiKey = "e738670a91beb00176965e46a062ce23";

    private Forecast forecast;

    double latitude = 37.8267;
    double longitude = -122.423;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        progressBar.setVisibility(View.INVISIBLE);
        getForecast(latitude, longitude);
    }

    private void getForecast(double latitude, double longitude) {
        if (isOnline()) {
            toggleRefresh();
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build();

            WeatherAPI weatherAPI = retrofit.create(WeatherAPI.class);
            weatherAPI.getForecast(apiKey,latitude,longitude).enqueue(new Callback<Forecast>() {
                @Override
                public void onResponse(Call<Forecast> call, Response<Forecast> response) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                        }
                    });
                    if (response.isSuccessful()) {
                        forecast = response.body();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                updateDisplay();
                            }
                        });
                    } else {
                        Log.e(TAG, "On response failure");
                        showAlertDialog(getString(R.string.error_title), getString(R.string.error_message));
                    }
                }

                @Override
                public void onFailure(Call<Forecast> call, Throwable t) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                        }
                    });
                    Log.e(TAG, "On Failure", t);
                    showAlertDialog(getString(R.string.error_title), getString(R.string.error_message));
                }
            });
        } else {
            Toast.makeText(this, getString(R.string.no_network_string), Toast.LENGTH_LONG).show();
        }
    }

    private void toggleRefresh() {
        if (progressBar.getVisibility() == View.INVISIBLE){
            progressBar.setVisibility(View.VISIBLE);
            refreshImageView.setVisibility(View.INVISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
            refreshImageView.setVisibility(View.VISIBLE);

        }
    }

    private void updateDisplay() {
        temperatureLabel.setText(forecast.getCurrently().getTemperature() + "");
        timeLabel.setText("At " + forecast.getFormattedTime() + " it will be");
        humidityValue.setText(forecast.getCurrently().getHumidity() + "");
        precipValue.setText(forecast.getCurrently().getPrecipProbability() + "%");
        summaryLabel.setText(forecast.getCurrently().getSummary());
        iconImageView.setImageDrawable(ContextCompat.getDrawable(this,forecast.getCurrently().getIconId()));
    }

    private boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    @OnClick(R.id.refreshImageView)
    protected void refresh(View view) {
        getForecast(latitude, longitude);
    }

    @OnClick(R.id.dailyButton)
    protected void startDailyActivity(View view) {
        Intent intent = new Intent(this, DailyActivity.class);
        intent.putExtra(FORECAST, forecast);
        startActivity(intent);
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
