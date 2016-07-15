package com.shaznee.breeze.ui.adapters.recycleradapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shaznee.breeze.R;
import com.shaznee.breeze.services.weather.Data;
import com.shaznee.breeze.services.weather.Forecast;

/**
 * Created by Shaznee on 18-May-16.
 */
public class HourAdapter extends RecyclerView.Adapter<HourAdapter.HourViewHolder> {

    private Forecast forecast;
    private CharSequence unitPref;

    public HourAdapter(Forecast forecast, CharSequence unitPref) {
        this.forecast = forecast;
        this.unitPref = unitPref;
    }

    @Override
    public HourViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hourly_list_item, parent, false);
        return new HourViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HourViewHolder holder, int position) {
        holder.bindForecast(forecast, position);
    }

    @Override
    public int getItemCount() {
        return forecast.getHourly().getData().size();
    }

    public class HourViewHolder extends RecyclerView.ViewHolder {

        TextView timeLabel;
        TextView summaryLabel;
        TextView temperatureLabel;
        ImageView iconImageView;

        public HourViewHolder(View itemView) {
            super(itemView);

            timeLabel = (TextView) itemView.findViewById(R.id.timeLabel);
            summaryLabel = (TextView) itemView.findViewById(R.id.summaryLabel);
            temperatureLabel = (TextView) itemView.findViewById(R.id.temperatureLabel);
            iconImageView = (ImageView) itemView.findViewById(R.id.iconImageView);
        }

        public void bindForecast(Forecast forecast, int position) {
            Data data = forecast.getHourly().getData().get(position);
            timeLabel.setText(forecast.getHour(data.getTime()));
            summaryLabel.setText(data.getSummary());
            temperatureLabel.setText(data.getTemperature() + "°" + unitPref);
            iconImageView.setImageResource(data.getIconId());
        }
    }
}
