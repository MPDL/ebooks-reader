package de.mpg.mpdl.ebooksreader.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.tonyodev.fetch2.Download;
import com.tonyodev.fetch2.Error;
import com.tonyodev.fetch2.Fetch;
import com.tonyodev.fetch2.Request;
import com.tonyodev.fetch2.Status;
import com.tonyodev.fetch2core.Extras;
import com.tonyodev.fetch2core.FetchObserver;
import com.tonyodev.fetch2core.Func;
import com.tonyodev.fetch2core.MutableExtras;
import com.tonyodev.fetch2core.Reason;

import org.jetbrains.annotations.NotNull;

import de.mpg.mpdl.ebooksreader.utils.Data;
import de.mpg.mpdl.ebooksreader.utils.Utils;
import timber.log.Timber;

public class DownloadBookActivity extends AppCompatActivity implements FetchObserver<Download> {
    private static final int STORAGE_PERMISSION_CODE = 100;
    private static final String TAG = "DownloadBookActivity";
    private TextView progressTextView;
    private Request request;
    private Fetch fetch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_description);
        progressTextView = findViewById(R.id.progress_TextView);
        fetch = Fetch.Impl.getDefaultInstance();
        checkStoragePermission();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (request != null) {
            fetch.attachFetchObserversForDownload(request.getId(), this);
        }
    }

    @Override
    protected void onPause() {
        Log.i(TAG, "onPause: ");
        super.onPause();
        if (request != null) {
            fetch.removeFetchObserversForDownload(request.getId(), this);
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
        final String url = Data.sampleUrls[0];
        final String filePath = Data.getSaveDir() + "/movies/" + Data.getNameFromUrl(url);
        request = new Request(url, filePath);
        request.setExtras(getExtrasForRequest(request));

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
                progressTextView.setText(R.string.queued);
                break;
            }
            case ADDED: {
                progressTextView.setText(R.string.added);
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
                progressTextView.setText(R.string.status_unknown);
                break;
            }
        }
    }
}