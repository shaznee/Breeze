package com.shaznee.breeze.adapters.arrayadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shaznee.breeze.R;
import com.shaznee.breeze.models.location.MyLocation;

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
            viewHolder.primaryTextLabel = (TextView) convertView.findViewById(R.id.primaryTextLabel);
            viewHolder.secondaryTextLabel = (TextView) convertView.findViewById(R.id.secondaryTextLabel);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        MyLocation location = locations.get(position);
        viewHolder.primaryTextLabel.setText(location.getPrimaryText());
        viewHolder.secondaryTextLabel.setText(location.getSecondaryText());
        return convertView;
    }

    public void addLocation(MyLocation location) {
        locations.add(location);
        notifyDataSetChanged();
    }

    private static class ViewHolder {
        TextView primaryTextLabel, secondaryTextLabel;
    }
}
