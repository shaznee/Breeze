package com.shaznee.breeze.services.weather;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class Currently implements Parcelable {

    @JsonProperty("time")
    private long time;
    @JsonProperty("summary")
    private String summary;
    @JsonProperty("icon")
    private String icon;
    @JsonProperty("precipProbability")
    private long precipProbability;
    @JsonProperty("temperature")
    private double temperature;
    @JsonProperty("humidity")
     private double humidity;

    public Currently() {}

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @JsonIgnore
    public int getIconId () {
        return Forecast.getIconId(icon);
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

    public int getPrecipProbability() {
        return (int) Math.round(precipProbability * 100);
    }

    public void setPrecipProbability(long precipProbability) {
        this.precipProbability = precipProbability;
    }

    public int getTemperature() {
        return (int) Math.round(temperature);
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    //*********Parcelable Implemetnation **********//

    private Currently(Parcel in) {
        time = in.readLong();
        summary = in.readString();
        icon = in.readString();
        precipProbability = in.readLong();
        temperature = in.readDouble();
        humidity = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(time);
        dest.writeString(summary);
        dest.writeString(icon);
        dest.writeLong(precipProbability);
        dest.writeDouble(temperature);
        dest.writeDouble(humidity);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Currently> CREATOR = new Creator<Currently>() {
        @Override
        public Currently createFromParcel(Parcel in) {
            return new Currently(in);
        }

        @Override
        public Currently[] newArray(int size) {
            return new Currently[size];
        }
    };

}
