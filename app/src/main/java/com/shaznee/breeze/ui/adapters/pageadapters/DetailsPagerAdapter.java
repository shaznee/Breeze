package com.shaznee.breeze.ui.adapters.pageadapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.shaznee.breeze.ui.fragments.DailyFragment;
import com.shaznee.breeze.ui.fragments.HourlyFragment;

/**
 * Created by SHAZNEE on 29-Jun-16.
 */
public class DetailsPagerAdapter extends FragmentStatePagerAdapter {

    public DetailsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a HourlyFragment (defined as a static inner class below).
        switch (position) {
            case 0:
                return DailyFragment.newInstance();
            case 1:
                return HourlyFragment.newInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "DAILY";
            case 1:
                return "HOURLY";
        }
        return null;
    }
}
