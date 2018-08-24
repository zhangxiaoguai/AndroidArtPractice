package com.ericzhang.androidartpractice.chapter15;

import android.content.Context;
import android.util.Log;

import com.ericzhang.androidartpractice.MyApp;
import com.ericzhang.androidartpractice.chapter3.utils.MyUtils;
import com.github.moduth.blockcanary.BlockCanaryContext;
import com.github.moduth.blockcanary.internal.BlockInfo;

import java.io.File;
import java.util.List;

public class MyBlockCanaryContext extends BlockCanaryContext {

    private static final String TAG = "MyBlockCanaryContext";

    @Override
    public String provideQualifier() {
        Log.e(TAG, "provideQualifier");

        return "AndroidArt_Debug_1.0.1";
    }

    @Override
    public String provideUid() {
        Log.e(TAG, "provideUid");

        return "Eric_AndroidArt";
    }

    @Override
    public String provideNetworkType() {
        Log.e(TAG, "provideNetworkType");

        return MyUtils.isWifi(MyApp.getAppInstance()) ? "Wifi" : "Cellular";
    }

    @Override
    public int provideMonitorDuration() {
        Log.e(TAG, "provideMonitorDuration");

        return super.provideMonitorDuration();
    }

    @Override
    public int provideBlockThreshold() {
        Log.e(TAG, "provideBlockThreshold");

        return super.provideBlockThreshold();
    }

    @Override
    public int provideDumpInterval() {
        Log.e(TAG, "provideDumpInterval");

        return super.provideDumpInterval();
    }

    @Override
    public String providePath() {
        Log.e(TAG, "providePath");

        return super.providePath();
    }

    @Override
    public boolean displayNotification() {
        Log.e(TAG, "displayNotification");

        return super.displayNotification();
    }

    @Override
    public boolean zip(File[] src, File dest) {
        Log.e(TAG, "zip");

        return super.zip(src, dest);
    }

    @Override
    public void upload(File zippedFile) {
        Log.e(TAG, "upload");

        super.upload(zippedFile);
    }

    @Override
    public List<String> concernPackages() {
        Log.e(TAG, "concernPackages");

        return super.concernPackages();
    }

    @Override
    public boolean filterNonConcernStack() {
        Log.e(TAG, "filterNonConcernStack");

        return super.filterNonConcernStack();
    }

    @Override
    public List<String> provideWhiteList() {
        Log.e(TAG, "provideWhiteList");

        return super.provideWhiteList();
    }

    @Override
    public boolean deleteFilesInWhiteList() {
        Log.e(TAG, "deleteFilesInWhiteList");

        return super.deleteFilesInWhiteList();
    }

    @Override
    public void onBlock(Context context, BlockInfo blockInfo) {
        Log.e(TAG, "onBlock");

        super.onBlock(context, blockInfo);
    }
}
