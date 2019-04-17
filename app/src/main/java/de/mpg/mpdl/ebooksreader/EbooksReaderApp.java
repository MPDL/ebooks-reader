package de.mpg.mpdl.ebooksreader;

import android.content.Context;

import com.tonyodev.fetch2.Fetch;
import com.tonyodev.fetch2.FetchConfiguration;
import com.tonyodev.fetch2.HttpUrlConnectionDownloader;
import com.tonyodev.fetch2core.Downloader;

import de.mpg.mpdl.ebooksreader.base.BaseApplication;

public class EbooksReaderApp extends BaseApplication {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        final FetchConfiguration fetchConfiguration = new FetchConfiguration.Builder(this)
                .enableRetryOnNetworkGain(true)
                .setDownloadConcurrentLimit(3)
                .setHttpDownloader(new HttpUrlConnectionDownloader(Downloader.FileDownloaderType.PARALLEL))
                // OR
                //.setHttpDownloader(getOkHttpDownloader())
                .build();
        Fetch.Impl.setDefaultInstanceConfiguration(fetchConfiguration);
    }

    public static Context getmContext() {
        return mContext;
    }
}
