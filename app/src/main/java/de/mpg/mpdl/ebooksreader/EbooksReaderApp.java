package de.mpg.mpdl.ebooksreader;

import android.content.Context;

import com.tonyodev.fetch2.Fetch;
import com.tonyodev.fetch2.FetchConfiguration;
import com.tonyodev.fetch2okhttp.OkHttpDownloader;

import de.mpg.mpdl.ebooksreader.base.BaseApplication;
import okhttp3.OkHttpClient;

public class EbooksReaderApp extends BaseApplication {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        final FetchConfiguration fetchConfiguration = new FetchConfiguration.Builder(this)
                .enableRetryOnNetworkGain(true)
                .setDownloadConcurrentLimit(3)
                .setHttpDownloader(new OkHttpDownloader(okHttpClient))
                .build();
        Fetch.Impl.setDefaultInstanceConfiguration(fetchConfiguration);
    }

    public static Context getmContext() {
        return mContext;
    }
}
