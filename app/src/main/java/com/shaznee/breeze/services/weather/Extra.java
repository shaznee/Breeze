package com.shaznee.breeze.services.weather;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class Extra implements Parcelable {

    @JsonProperty("data")
    private List<Data> data;

    public Extra() {}

    public List<Data> getData() {
        return data;
    }

    public void setData(ArrayList<Data> data) {
        this.data = data;
    }

    //*********Parcelable Implemetnation **********//

    protected Extra(Parcel in) {
        if (in.readByte() == 0x01) {
            data = new ArrayList<>();
            in.readList(data, Data.class.getClassLoader());
        } else {
            data = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (data == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(data);
        }
    }

    public static final Parcelable.Creator<Extra> CREATOR = new Parcelable.Creator<Extra>() {
        @Override
        public Extra createFromParcel(Parcel in) {
            return new Extra(in);
        }

        @Override
        public Extra[] newArray(int size) {
            return new Extra[size];
        }
    };

}
