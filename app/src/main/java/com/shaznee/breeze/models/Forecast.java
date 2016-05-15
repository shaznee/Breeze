package com.shaznee.breeze.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class Forecast {

//    @JsonProperty("latitude")
//    private double latitude;
//    @JsonProperty("longitude")
//    private double longitude;
    @JsonProperty("timezone")
    private String timezone;
//    @JsonProperty("offset")
//    private long offset;
    @JsonProperty("currently")
    private Currently currently;
//    @JsonProperty("minutely")
//    private Minutely minutely;
//    @JsonProperty("hourly")
//    private Hourly hourly;
//    @JsonProperty("daily")
//    private Daily daily;
//
//    public double getLatitude() {
//        return latitude;
//    }
//
//    public void setLatitude(double latitude) {
//        this.latitude = latitude;
//    }
//
//    public double getLongitude() {
//        return longitude;
//    }
//
//    public void setLongitude(double longitude) {
//        this.longitude = longitude;
//    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    @JsonIgnore
    public String getFormattedTime () {
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a");
        dateFormat.setTimeZone(TimeZone.getTimeZone(getTimezone()));
        return dateFormat.format(new Date(currently.getTime() * 1000));
    }

//    public long getOffset() {
//        return offset;
//    }
//
//    public void setOffset(long offset) {
//        this.offset = offset;
//    }

    public Currently getCurrently() {
        return currently;
    }

    public void setCurrently(Currently currently) {
        this.currently = currently;
    }
//
//    public Minutely getMinutely() {
//        return minutely;
//    }
//
//    public void setMinutely(Minutely minutely) {
//        this.minutely = minutely;
//    }
//
//    public Hourly getHourly() {
//        return hourly;
//    }
//
//    public void setHourly(Hourly hourly) {
//        this.hourly = hourly;
//    }
//
//    public Daily getDaily() {
//        return daily;
//    }
//
//    public void setDaily(Daily daily) {
//        this.daily = daily;
//    }

}
