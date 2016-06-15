package com.shaznee.breeze.adapters.pageadapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.shaznee.breeze.fragments.WeatherFragment;
import com.shaznee.breeze.models.location.MyLocation;

import java.util.List;

/**
 * Created by Shaznee on 22-May-16.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    List<MyLocation> locations;

    public SectionsPagerAdapter(FragmentManager fm, List<MyLocation> locations) {
        super(fm);
        this.locations = locations;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        if (position == 0) {
            return WeatherFragment.newInstance(null);
        } else {
            return WeatherFragment.newInstance(locations.get(position - 1));
        }
    }

    @Override
    public int getCount() {
        if (locations.size() == 0) {
            return 1;
        }
        return locations.size() + 1;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Current Location";
            default:
                return locations.get(position).getPrimaryText();
        }

    }

    public void addLocations(List<MyLocation> locations) {
        if (locations != null && locations.size() > 0) {
            locations.clear();
            locations.addAll(locations);
            notifyDataSetChanged();
        }

    }

    public void addLocation(MyLocation location) {
        locations.add(location);
        notifyDataSetChanged();
    }
}