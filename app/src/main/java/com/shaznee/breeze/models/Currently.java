package com.shaznee.breeze.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.shaznee.breeze.R;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class Currently {

    @JsonProperty("time")
    private long time;
    @JsonProperty("summary")
    private String summary;
    @JsonProperty("icon")
    private String icon;
//    @JsonProperty("nearestStormDistance")
//    private long nearestStormDistance;
//    @JsonProperty("nearestStormBearing")
//    private long nearestStormBearing;
//    @JsonProperty("precipIntensity")
//    private long precipIntensity;
    @JsonProperty("precipProbability")
    private long precipProbability;
    @JsonProperty("temperature")
    private double temperature;
//    @JsonProperty("apparentTemperature")
//    private double apparentTemperature;
//    @JsonProperty("dewPoint")
//    private double dewPoint;
    @JsonProperty("humidity")
     private double humidity;
//    @JsonProperty("windSpeed")
//    private double windSpeed;
//    @JsonProperty("windBearing")
//    private long windBearing;
//    @JsonProperty("visibility")
//    private double visibility;
//    @JsonProperty("cloudCover")
//    private double cloudCover;
//    @JsonProperty("pressure")
//    private double pressure;
//    @JsonProperty("ozone")
//    private double ozone;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @JsonIgnore
    public int getIconId () {
        //clear-day, clear-night, rain, snow, sleet, wind, fog, cloudy, partly-cloudy-day, or partly-cloudy-night
        switch (icon) {
            case "clear-day" :
                return R.drawable.clear_day;
            case "clear-night" :
                return R.drawable.clear_night;
            case "rain" :
                return R.drawable.rain;
            case "snow" :
                return R.drawable.snow;
            case "sleet" :
                return R.drawable.sleet;
            case "wind" :
                return R.drawable.wind;
            case "fog" :
                return R.drawable.fog;
            case "cloudy" :
                return R.drawable.cloudy;
            case "partly-cloudy-day" :
                return R.drawable.clear_day;
            case "partly-cloudy-night" :
                return R.drawable.cloudy_night;
            default:
                return R.drawable.clear_day;
        }
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

//    public long getNearestStormDistance() {
//        return nearestStormDistance;
//    }
//
//    public void setNearestStormDistance(long nearestStormDistance) {
//        this.nearestStormDistance = nearestStormDistance;
//    }
//
//    public long getNearestStormBearing() {
//        return nearestStormBearing;
//    }
//
//    public void setNearestStormBearing(long nearestStormBearing) {
//        this.nearestStormBearing = nearestStormBearing;
//    }
//
//    public long getPrecipIntensity() {
//        return precipIntensity;
//    }
//
//    public void setPrecipIntensity(long precipIntensity) {
//        this.precipIntensity = precipIntensity;
//    }

    public long getPrecipProbability() {
        return precipProbability;
    }

    public void setPrecipProbability(long precipProbability) {
        this.precipProbability = precipProbability;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

//    public double getApparentTemperature() {
//        return apparentTemperature;
//    }
//
//    public void setApparentTemperature(double apparentTemperature) {
//        this.apparentTemperature = apparentTemperature;
//    }
//
//    public double getDewPoint() {
//        return dewPoint;
//    }
//
//    public void setDewPoint(double dewPoint) {
//        this.dewPoint = dewPoint;
//    }
//
    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }
//
//    public double getWindSpeed() {
//        return windSpeed;
//    }
//
//    public void setWindSpeed(double windSpeed) {
//        this.windSpeed = windSpeed;
//    }
//
//    public long getWindBearing() {
//        return windBearing;
//    }
//
//    public void setWindBearing(long windBearing) {
//        this.windBearing = windBearing;
//    }
//
//    public double getVisibility() {
//        return visibility;
//    }
//
//    public void setVisibility(double visibility) {
//        this.visibility = visibility;
//    }
//
//    public double getCloudCover() {
//        return cloudCover;
//    }
//
//    public void setCloudCover(double cloudCover) {
//        this.cloudCover = cloudCover;
//    }
//
//    public double getPressure() {
//        return pressure;
//    }
//
//    public void setPressure(double pressure) {
//        this.pressure = pressure;
//    }
//
//    public double getOzone() {
//        return ozone;
//    }
//
//    public void setOzone(double ozone) {
//        this.ozone = ozone;
//    }

}
