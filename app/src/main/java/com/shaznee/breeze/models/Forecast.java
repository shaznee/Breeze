package com.shaznee.breeze.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.shaznee.breeze.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class Forecast implements Parcelable {

    @JsonProperty("timezone")
    private String timezone;
    @JsonProperty("currently")
    private Currently currently;
    @JsonProperty("hourly")
    private Extra hourly;
    @JsonProperty("daily")
    private Extra daily;

    public Forecast() {}

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

    @JsonIgnore
    public String getDayOfTheWeek(long time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE");
        dateFormat.setTimeZone(TimeZone.getTimeZone(getTimezone()));
        return dateFormat.format(new Date(time * 1000));
    }

    public Currently getCurrently() {
        return currently;
    }

    public void setCurrently(Currently currently) {
        this.currently = currently;
    }

    public Extra getHourly() {
        return hourly;
    }

    public void setHourly(Extra hourly) {
        this.hourly = hourly;
    }

    public Extra getDaily() {
        return daily;
    }

    public void setDaily(Extra daily) {
        this.daily = daily;
    }

    @JsonIgnore
    public static int getIconId(String icon) {
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

    //*********Parcelable Implemetnation **********//

    private Forecast (Parcel in) {
        timezone = in.readString();
        currently = (Currently) in.readValue(Currently.class.getClassLoader());
        hourly = (Extra) in.readValue(Extra.class.getClassLoader());
        daily = (Extra) in.readValue(Extra.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(timezone);
        dest.writeValue(currently);
        dest.writeValue(hourly);
        dest.writeValue(daily);
    }

    public static final Creator<Forecast> CREATOR = new Creator<Forecast>() {
        @Override
        public Forecast createFromParcel(Parcel in) {
            return new Forecast(in);
        }

        @Override
        public Forecast[] newArray(int size) {
            return new Forecast[size];
        }
    };

}
