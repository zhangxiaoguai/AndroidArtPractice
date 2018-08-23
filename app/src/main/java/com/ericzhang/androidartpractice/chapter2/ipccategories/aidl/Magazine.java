package com.ericzhang.androidartpractice.chapter2.ipccategories.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public class Magazine implements Parcelable {

    private int price;
    private String name;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Magazine{" +
                "price=" + price +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.price);
        dest.writeString(this.name);
    }

    public Magazine() {
    }

    protected Magazine(Parcel in) {
        this.price = in.readInt();
        this.name = in.readString();
    }

    public static final Parcelable.Creator<Magazine> CREATOR = new Parcelable.Creator<Magazine>() {
        @Override
        public Magazine createFromParcel(Parcel source) {
            return new Magazine(source);
        }

        @Override
        public Magazine[] newArray(int size) {
            return new Magazine[size];
        }
    };
}
