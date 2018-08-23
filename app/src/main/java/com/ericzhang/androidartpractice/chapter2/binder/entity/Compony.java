package com.ericzhang.androidartpractice.chapter2.binder.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Compony implements Parcelable, Serializable {

    private static final long serialVersionUID = 8201143653797799327L;
    private String name;
    private int number;

    @Override
    public String toString() {
        return "Compony{" +
                "name='" + name + '\'' +
                ", number=" + number +
                '}';
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.number);
    }

    public Compony() {
    }

    protected Compony(Parcel in) {
        this.name = in.readString();
        this.number = in.readInt();
    }

    public static final Parcelable.Creator<Compony> CREATOR = new Parcelable.Creator<Compony>() {
        @Override
        public Compony createFromParcel(Parcel source) {
            return new Compony(source);
        }

        @Override
        public Compony[] newArray(int size) {
            return new Compony[size];
        }
    };
}
