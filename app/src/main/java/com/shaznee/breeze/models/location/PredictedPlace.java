package com.shaznee.breeze.models.location;

/**
 * Created by SHAZNEE on 13-Jun-16.
 */
public class PredictedPlace {

    private String placeId, primaryText, secondaryText;

    public PredictedPlace(String placeId, String primaryText, String secondaryText) {
        this.placeId = placeId;
        this.primaryText = primaryText;
        this.secondaryText = secondaryText;
    }

    public String getPlaceId() {
        return placeId;
    }

    public String getPrimaryText() {
        return primaryText;
    }

    public String getSecondaryText() {
        return secondaryText;
    }
}
