package com.ericzhang.androidartpractice.chapter2.ipccategories.aidl;

import com.ericzhang.androidartpractice.chapter2.ipccategories.aidl.Magazine;
import com.ericzhang.androidartpractice.chapter2.ipccategories.aidl.IOnNewMagazineArrivedListener;

interface IMagazineManager {

    List<Magazine> getMagazineList();
    void addMagazine(in Magazine magazine);
    void registerListener(IOnNewMagazineArrivedListener listener);
    void unregisterListener(IOnNewMagazineArrivedListener listener);

}
