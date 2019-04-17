package de.mpg.mpdl.ebooksreader.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tonyodev.fetch2.AbstractFetchListener;
import com.tonyodev.fetch2.Download;
import com.tonyodev.fetch2.Error;
import com.tonyodev.fetch2.Fetch;
import com.tonyodev.fetch2.FetchListener;
import com.tonyodev.fetch2.Request;
import com.tonyodev.fetch2.Status;
import com.tonyodev.fetch2core.Extras;
import com.tonyodev.fetch2core.FetchObserver;
import com.tonyodev.fetch2core.Func;
import com.tonyodev.fetch2core.MutableExtras;
import com.tonyodev.fetch2core.Reason;

import org.jetbrains.annotations.NotNull;

import java.util.Set;

import de.mpg.mpdl.ebooksreader.utils.Data;
import timber.log.Timber;

public class BookDescriptionActivity extends AppCompatActivity implements FetchObserver<Download> {
    private static final int STORAGE_PERMISSION_CODE = 100;
    private static final String TAG = "BookDescriptionActivity";
    private static final int groupId = 12;

    private TextView progressTextView;
    private Request request;
    private Fetch fetch;
    private Button downloadButton;
    private ProgressBar Progressbar;
    private final ArrayMap<Integer, Integer> fileProgressMap = new ArrayMap<>();
    static int lastProgress;
    static int lastProgressId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_description);
        progressTextView = findViewById(R.id.progress_TextView);
        downloadButton = findViewById(R.id.downloadButton);
        Progressbar = findViewById(R.id.Progressbar);
        fetch = Fetch.Impl.getDefaultInstance();
        downloadButton.setOnClickListener(v -> {checkStoragePermission();});
    }

    @Override
    protected void onResume() {
        Log.i(TAG, "onResume: ");
        super.onResume();
        if (lastProgress!=0){
            Progressbar.setProgress(lastProgress);
            final String progressString = getResources().getString(R.string.percent_progress, lastProgress);
            progressTextView.setText(progressString);
        }
    }

    @Override
    protected void onPause() {
        Log.i(TAG, "onPause: ");
        super.onPause();
        if (fetch!=null && request!=null) {
            fetch.pause(request.getId());
        }
    }

    @Override
    protected void onStop() {
        Log.i(TAG, "onStop: ");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (fetch!=null && request != null) {
            fetch.removeFetchObserversForDownload(request.getId(), this);
        }
        fetch.close();
    }

    @Override
    public void onChanged(Download data, @NotNull Reason reason) {
        updateViews(data, reason);
    }

    private void checkStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        } else {
            enqueueDownload();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            enqueueDownload();
        }
    }

    private void enqueueDownload() {
        Log.i(TAG, "enqueueDownload: ");
        final String url = Data.sampleUrls[0];
        final String filePath = Data.getSaveDir() + "/movies/" + Data.getNameFromUrl(url);
        request = new Request(url, filePath);
        request.setExtras(getExtrasForRequest(request));

        fetch.getDownloadsInGroup(groupId, downloads -> {
            for (Download download : downloads) {
                if (fileProgressMap.containsKey(download.getId())) {
                    fileProgressMap.put(download.getId(), download.getProgress());
                    lastProgress = download.getProgress();
                    lastProgressId = download.getId();
                    updateUIWithProgress();
                }
            }
        }).addListener(fetchListener);
        fetch.attachFetchObserversForDownload(request.getId(), this)
                .enqueue(request, new Func<Request>() {
                    @Override
                    public void call(@NotNull Request result) {
                        request = result;
                    }
                }, new Func<Error>() {
                    @Override
                    public void call(@NotNull Error result) {
                        Timber.d("SingleDownloadActivity Error: %1$s", result.toString());
                    }
                });
    }

    private Extras getExtrasForRequest(Request request) {
        final MutableExtras extras = new MutableExtras();
        extras.putBoolean("testBoolean", true);
        extras.putString("testString", "test");
        extras.putFloat("testFloat", Float.MIN_VALUE);
        extras.putDouble("testDouble", Double.MIN_VALUE);
        extras.putInt("testInt", Integer.MAX_VALUE);
        extras.putLong("testLong", Long.MAX_VALUE);
        return extras;
    }

    private void updateViews(@NotNull Download download, Reason reason) {
        if (request.getId() == download.getId()) {
            setProgressView(download.getStatus(), download.getProgress());

        }
    }

    private void setProgressView(@NonNull final Status status, final int progress) {
        switch (status) {
            case QUEUED: {
                break;
            }
            case ADDED: {
                break;
            }
            case DOWNLOADING:
            case COMPLETED: {
                if (progress == -1) {
                    progressTextView.setText(R.string.downloading);
                } else {
                    final String progressString = getResources().getString(R.string.percent_progress, progress);
                    progressTextView.setText(progressString);
                }
                break;
            }
            default: {
                break;
            }
        }
    }

    private void updateUIWithProgress() {
        final int progress = getDownloadProgress();
        Progressbar.setProgress(progress);
    }

    private int getDownloadProgress() {
        int currentProgress = 0;
        final int totalProgress = fileProgressMap.size() * 100;
        final Set<Integer> ids = fileProgressMap.keySet();

        for (int id : ids) {
            currentProgress += fileProgressMap.get(id);
        }
        currentProgress = (int) (((double) currentProgress / (double) totalProgress) * 100);
        return currentProgress;
    }

    private final FetchListener fetchListener = new AbstractFetchListener() {
        @Override
        public void onCompleted(@NotNull Download download) {
            fileProgressMap.put(download.getId(), download.getProgress());
            lastProgress = download.getProgress();
            lastProgressId = download.getId();
            updateUIWithProgress();
        }

        @Override
        public void onError(@NotNull Download download, @NotNull Error error, @org.jetbrains.annotations.Nullable Throwable throwable) {
            super.onError(download, error, throwable);
        }

        @Override
        public void onProgress(@NotNull Download download, long etaInMilliseconds, long downloadedBytesPerSecond) {
            super.onProgress(download, etaInMilliseconds, downloadedBytesPerSecond);
            fileProgressMap.put(download.getId(), download.getProgress());
            lastProgress = download.getProgress();
            lastProgressId = download.getId();
            updateUIWithProgress();
        }
    };
}