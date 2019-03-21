package de.mpg.mpdl.ebooksreader;

import android.content.Context;

import de.mpg.mpdl.ebooksreader.base.BaseApplication;

public class EbooksReaderApp extends BaseApplication {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public static Context getmContext() {
        return mContext;
    }
}
