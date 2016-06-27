package com.shaznee.breeze.adapters.arrayadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shaznee.breeze.R;
import com.shaznee.breeze.models.weather.Data;
import com.shaznee.breeze.models.weather.Forecast;

/**
 * Created by Shaznee on 16-May-16.
 */
public class DayAdapter extends BaseAdapter {

    private Context context;
    private Forecast forecast;
    private CharSequence unitPref;

    public DayAdapter(Context context, Forecast forecast, CharSequence unitPref) {
        this.context = context;
        this.forecast = forecast;
        this.unitPref = unitPref;
    }

    @Override
    public int getCount() {
        return forecast.getDaily().getData().size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.daily_list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.iconImageView = (ImageView) convertView.findViewById(R.id.iconImageView);
            viewHolder.temperatureLabel = (TextView) convertView.findViewById(R.id.temperatureLabel);
            viewHolder.dayLabel = (TextView) convertView.findViewById(R.id.dayNameLabel);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Data data = forecast.getDaily().getData().get(position);

        viewHolder.iconImageView.setImageResource(data.getIconId());
        viewHolder.temperatureLabel.setText(data.getTemperatureMax() + "° " + unitPref);
        if (position == 0) {
            viewHolder.dayLabel.setText("Today");
        } else {
            viewHolder.dayLabel.setText(forecast.getDayOfTheWeek(data.getTime()));
        }

        return convertView;
    }

    private static class ViewHolder {
        ImageView iconImageView;
        TextView temperatureLabel;
        TextView dayLabel;
    }
}
