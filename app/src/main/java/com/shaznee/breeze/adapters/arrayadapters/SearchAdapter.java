package com.shaznee.breeze.adapters.arrayadapters;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.style.CharacterStyle;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataBufferUtils;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.AutocompletePredictionBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.shaznee.breeze.R;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Created by SHAZNEE on 10-Jun-16.
 */
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> implements
        Filterable {

    private static final String TAG = SearchAdapter.class.getSimpleName();

    private static final CharacterStyle STYLE_BOLD = new StyleSpan(Typeface.BOLD);

    private ArrayList<AutocompletePrediction> selectedCities;
    private GoogleApiClient googleApiClient;

    public SearchAdapter(GoogleApiClient googleApiClient) {
        this.googleApiClient = googleApiClient;
    }

    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_list_item, parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchViewHolder holder, int position) {
        holder.bindPlace(selectedCities.get(position));
    }

    @Override
    public int getItemCount() {
        return selectedCities.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();

                if (constraint != null) {
                    selectedCities = getAutocomplete(constraint);
                    if (selectedCities != null) {
                        // The API successfully returned results.
                        results.values = selectedCities;
                        results.count = selectedCities.size();
                    }
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged();
                }
            }

            @Override
            public CharSequence convertResultToString(Object resultValue) {
                if (resultValue instanceof AutocompletePrediction) {
                    return ((AutocompletePrediction) resultValue).getFullText(null);
                } else {
                    return super.convertResultToString(resultValue);
                }
            }
        };
    }

    private ArrayList<AutocompletePrediction> getAutocomplete(CharSequence constraint) {
        if( googleApiClient != null || googleApiClient.isConnected() ) {
            LatLngBounds bounds = new LatLngBounds(new LatLng(-0, 0), new LatLng(0, 0));

            AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                    .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
                    .build();

            PendingResult<AutocompletePredictionBuffer> results = Places.GeoDataApi.getAutocompletePredictions
                    (googleApiClient, constraint.toString(), bounds, typeFilter);

            AutocompletePredictionBuffer autocompletePredictions = results
                    .await(60, TimeUnit.SECONDS);
            final Status status = autocompletePredictions.getStatus();
            if (!status.isSuccess()) {
//                Toast.makeText(getContext(), "Error contacting API: " + status.toString(),
//                        Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Error getting autocomplete prediction API call: " + status.toString());
                autocompletePredictions.release();
                return null;
            }

            Log.i(TAG, "Query completed. Received " + autocompletePredictions.getCount()
                    + " predictions.");

            // Freeze the results immutable representation that can be stored safely.
            return DataBufferUtils.freezeAndClose(autocompletePredictions);
        }
        Log.e(TAG, "Google API client is not connected for autocomplete query.");
        return null;
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder {

        TextView cityLabel;

        public SearchViewHolder(View itemView) {
            super(itemView);
            cityLabel = (TextView) itemView.findViewById(R.id.cityLabel);
        }

        public void bindPlace(AutocompletePrediction selectedCity) {
            cityLabel.setText(selectedCity.getFullText(STYLE_BOLD));
        }
    }

}
