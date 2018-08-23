package com.ericzhang.androidartpractice.chapter2.ipccategories.aidl;

import com.ericzhang.androidartpractice.chapter2.ipccategories.aidl.Magazine;

interface IOnNewMagazineArrivedListener {

    void onNewMagazineArrived(in Magazine magazine);

}
