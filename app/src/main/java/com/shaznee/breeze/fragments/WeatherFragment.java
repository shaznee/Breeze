package com.shaznee.breeze.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.shaznee.breeze.client.Handler;
import com.shaznee.breeze.R;
import com.shaznee.breeze.activities.DailyForecast;
import com.shaznee.breeze.activities.HourlyForecast;
import com.shaznee.breeze.client.ForecastClient;
import com.shaznee.breeze.models.Forecast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WeatherFragment extends Fragment implements Handler {

    private static final String TAG = WeatherFragment.class.getSimpleName();

    @BindView(R.id.timeLabel) TextView timeLabel;
    @BindView(R.id.temperatureLabel) TextView temperatureLabel;
    @BindView(R.id.humidityValue) TextView humidityValue;
    @BindView(R.id.precipValue) TextView precipValue;
    @BindView(R.id.summaryLabel) TextView summaryLabel;
    @BindView(R.id.iconImageView) ImageView iconImageView;
    @BindView(R.id.refreshImageView) ImageView refreshImageView;
    @BindView(R.id.progressBar) ProgressBar progressBar;

    public static final String FORECAST = "FORECAST";

    ForecastClient client = ForecastClient.getInstance();

    private Forecast forecast;

    double latitude = 37.8267;
    double longitude = -122.423;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;


    private OnFragmentInteractionListener mListener;

    public WeatherFragment() {}

//    public static WeatherFragment newInstance(String param1, String param2) {
//        WeatherFragment fragment = new WeatherFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        ButterKnife.bind(this, view);

        progressBar.setVisibility(View.INVISIBLE);
        getForecast();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void successful(Forecast forecast) {
        toggleRefresh();
        this.forecast = forecast;
        updateDisplay();
    }

    @Override
    public void failure() {
        toggleRefresh();
        showAlertDialog(getString(R.string.error_title), getString(R.string.error_message));
    }

    private void getForecast() {
        if (isOnline()) {
            toggleRefresh();
            client.getForecast(latitude, longitude, this);
        } else {
            Toast.makeText(getActivity(), getString(R.string.no_network_string), Toast.LENGTH_LONG).show();
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
        iconImageView.setImageDrawable(ContextCompat.getDrawable(getActivity(),forecast.getCurrently().getIconId()));
    }

    private boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    @OnClick(R.id.refreshImageView)
    protected void refresh(View view) {
        getForecast();
    }

    @OnClick(R.id.dailyButton)
    protected void startDailyActivity(View view) {
        Intent intent = new Intent(getActivity(), DailyForecast.class);
        intent.putExtra(FORECAST, forecast);
        startActivity(intent);
    }

    @OnClick(R.id.hourlyButton)
    protected void startHourlyActivity(View view) {
        Intent intent = new Intent(getActivity(), HourlyForecast.class);
        intent.putExtra(FORECAST, forecast);
        startActivity(intent);
    }

    private void showAlertDialog(String title, String message) {

        AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.dialog_ok), null)
                .create();

        alertDialog.show();
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction();
    }
}
