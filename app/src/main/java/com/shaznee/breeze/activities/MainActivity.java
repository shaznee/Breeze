package com.shaznee.breeze.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.shaznee.breeze.R;
import com.shaznee.breeze.adapters.pageadapter.SectionsPagerAdapter;
import com.shaznee.breeze.fragments.WeatherFragment;

public class MainActivity extends AppCompatActivity implements WeatherFragment.OnFragmentInteractionListener {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }

    @Override
    public void onFragmentInteraction() {
        Toast.makeText(this, "Call from fragment", Toast.LENGTH_SHORT).show();
    }

    protected void onCitiesLabelClick(View view) {
        startActivity(new Intent(this, LocationPreferenceActivity.class));
    }

    protected void onAddLabelClick(View view) {
    }

    protected void onMoreLabelClick(View view) {
    }
}
