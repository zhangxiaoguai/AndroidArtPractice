package com.ericzhang.androidartpractice.chapter2.binder.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Teacher implements Parcelable {

    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.age);
    }

    public Teacher() {
    }

    protected Teacher(Parcel in) {
        this.name = in.readString();
        this.age = in.readInt();
    }

    public static final Parcelable.Creator<Teacher> CREATOR = new Parcelable.Creator<Teacher>() {
        @Override
        public Teacher createFromParcel(Parcel source) {
            return new Teacher(source);
        }

        @Override
        public Teacher[] newArray(int size) {
            return new Teacher[size];
        }
    };
}

