package com.shaznee.breeze.adapters.arrayadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shaznee.breeze.R;
import com.shaznee.breeze.preferences.MyLocation;

import java.util.List;

/**
 * Created by Shaznee on 23-May-16.
 */
public class PreferenceAdapter extends BaseAdapter {

    private Context context;
    private List<MyLocation> locations;

    public PreferenceAdapter(Context context, List<MyLocation> locations) {
        this.context = context;
        this.locations= locations;
    }

    @Override
    public int getCount() {
        return locations.size();
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
            convertView = LayoutInflater.from(context).inflate(R.layout.preference_list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.cityLabel = (TextView) convertView.findViewById(R.id.cityLabel);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.cityLabel.setText(locations.get(position).getCityName());
        return convertView;
    }

    private static class ViewHolder {
        TextView cityLabel;
    }
}
