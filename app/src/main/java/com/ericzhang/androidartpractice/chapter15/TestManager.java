package com.ericzhang.androidartpractice.chapter15;

import java.util.ArrayList;
import java.util.List;

public class TestManager {

    private List<OnDataArrivedListener> mOnDataArrivedListeners = new ArrayList<>();

    private static class SingletonHolder {
        public static final TestManager INSTANCE = new TestManager();
    }

    private TestManager() {
    }

    public static TestManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public synchronized void registerListener(OnDataArrivedListener listener) {
        if (!mOnDataArrivedListeners.contains(listener)) {
            mOnDataArrivedListeners.add(listener);
        }
    }

    public synchronized void unregisterListener(OnDataArrivedListener listener) {
        mOnDataArrivedListeners.remove(listener);
    }

    public interface OnDataArrivedListener {
        void onDataArrived(Object data);
    }

}
