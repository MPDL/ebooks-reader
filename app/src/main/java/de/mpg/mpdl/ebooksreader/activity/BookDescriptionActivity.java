package de.mpg.mpdl.ebooksreader.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ImageView;

import com.tonyodev.fetch2.AbstractFetchListener;
import com.tonyodev.fetch2.Download;
import com.tonyodev.fetch2.Error;
import com.tonyodev.fetch2.Fetch;
import com.tonyodev.fetch2.FetchListener;
import com.tonyodev.fetch2.Request;
import com.tonyodev.fetch2.Status;

import com.tonyodev.fetch2core.FetchObserver;
import com.tonyodev.fetch2core.Reason;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;

import de.mpg.mpdl.ebooksreader.utils.Data;
import de.mpg.mpdl.ebooksreader.utils.PreferenceUtil;
import timber.log.Timber;
import de.mpg.mpdl.ebooksreader.base.BaseCompatActivity;
import de.mpg.mpdl.ebooksreader.injection.module.glide.ImageLoader;
import de.mpg.mpdl.ebooksreader.model.dto.DocDTO;
import de.mpg.mpdl.ebooksreader.utils.JacksonUtil;

public class BookDescriptionActivity extends BaseCompatActivity implements FetchObserver<Download>{

    private static final int STORAGE_PERMISSION_CODE = 100;
    private static final String TAG = "BookDescriptionActivity";
    private static final int groupId = 12;

    private final ArrayMap<Integer, Integer> fileProgressMap = new ArrayMap<>();
    private Request request;
    private Fetch fetch;

    static int lastProgress;
    static int lastProgressId;

    TextView progressTextView;
    ImageView detailCoverImageView;
    ImageView detailBackImageView;
    TextView detailAbstractTextView;
    TextView detailTitleTextView;
    TextView detailSubtitleTextView;
    TextView detailAuthorTextView;
    TextView detailPublishDateTextView;
    TextView detailPublisherTextView;
    TextView detailIsbnTextView;
    Button downloadButton;
    ProgressBar Progressbar;

    DocDTO docDTO;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_book_description;
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {

        progressTextView = findViewById(R.id.progress_TextView);
        downloadButton = findViewById(R.id.downloadButton);
        Progressbar = findViewById(R.id.Progressbar);
        fetch = Fetch.Impl.getDefaultInstance();
        downloadButton.setOnClickListener(v -> {checkStoragePermission();});

        String docDTOStr = getIntent().getStringExtra("docDTOStr");
        docDTO = JacksonUtil.parseDocDTO(docDTOStr);

        detailCoverImageView = findViewById(R.id.detailCoverImageView);
        detailBackImageView = findViewById(R.id.detailBackImageView);
        detailAbstractTextView = findViewById(R.id.detailAbstractTextView);
        detailTitleTextView = findViewById(R.id.detailTitleTextView);
        detailSubtitleTextView = findViewById(R.id.detailSubtitleTextView);
        detailAuthorTextView = findViewById(R.id.detailAuthorTextView);
        detailPublishDateTextView = findViewById(R.id.detailPublishDateTextView);
        detailPublisherTextView = findViewById(R.id.detailPublisherTextView);
        detailIsbnTextView = findViewById(R.id.detailIsbnTextView);

        if (null != docDTO.getCoverUrl()) {
            ImageLoader.loadStringRes(detailCoverImageView, docDTO.getCoverUrl(), ImageLoader.defConfig, null);
        }

        detailBackImageView.setOnClickListener(view -> finish());

        if (null != docDTO.getDescription()) {
            detailAbstractTextView.setText(docDTO.getDescription());
        }

        if (null != docDTO.getTitle()) {
            detailTitleTextView.setText(docDTO.getTitle());
        }

        if (null != docDTO.getSubTitle()) {
            detailSubtitleTextView.setText(docDTO.getSubTitle());
        }

        if (null != docDTO.getAuthorList() && docDTO.getAuthorList().size() > 0) {
            detailAuthorTextView.setText(docDTO.getAuthorList().get(0));
        }

        if (null != docDTO.getPublishDate() && docDTO.getPublishDate().size() > 0) {
            detailPublishDateTextView.setText(docDTO.getPublishDate().get(0));
        }

        if (null != docDTO.getPublisher() && docDTO.getPublisher().size() > 0) {
            detailPublisherTextView.setText(docDTO.getPublisher().get(0));
        }

        if (null == docDTO.getIsbn() || docDTO.getIsbn().size() == 0)
            return;
        else
            detailIsbnTextView.setText("ISBN:  " + docDTO.getIsbn());
        lastProgress = 0;

        Log.e("BookDescriptionActivity", JacksonUtil.stringifyDocDTO(docDTO));

        List<DocDTO> docDTOList = JacksonUtil.parseDocDTOList(PreferenceUtil.getString(getApplicationContext(), PreferenceUtil.SHARED_PREFERENCES, PreferenceUtil.DOWNLOADED_BOOKS, ""));
        for (DocDTO downloadedDocDTO : docDTOList) {
            if (downloadedDocDTO.getUrlPdfStr().equalsIgnoreCase(docDTO.getUrlPdfStr())) {
                hideDownloadProgressBar();
                break;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        lastProgress = 0;
        Progressbar.setProgress(lastProgress);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (fetch!=null && request!=null) {
            fetch.pause(request.getId());
        }
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
        if (null == docDTO.getUrlPdfStr() || docDTO.getUrlPdfStr().equalsIgnoreCase("")) {
            showMessage("Download url not found.");
            return;
        }
        
        final String url = docDTO.getUrlPdfStr();
        if (null != docDTO.getIsbn() && docDTO.getIsbn().size()>0) {
            final String filePath = Data.getSaveDir() + "/books/" + docDTO.getIsbn().get(0);
            request = new Request(url, filePath);
        } else {
            showMessage("ISBN of the book not found.");
            return;
        }

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
                .enqueue(request, result -> request = result, result -> Timber.d("Download Error: %1$s", result.toString()));
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

    private void hideDownloadProgressBar() {
        downloadButton.setText("Downloaded");
        downloadButton.setTextColor(Color.BLACK);
        downloadButton.setEnabled(false);
        downloadButton.setBackgroundColor(Color.TRANSPARENT);
        progressTextView.setVisibility(View.INVISIBLE);
        Progressbar.setVisibility(View.INVISIBLE);
    }

    private final FetchListener fetchListener = new AbstractFetchListener() {
        @Override
        public void onCompleted(@NotNull Download download) {
            fileProgressMap.put(download.getId(), download.getProgress());
            lastProgress = download.getProgress();
            lastProgressId = download.getId();
            updateUIWithProgress();

            hideDownloadProgressBar();
            List<DocDTO> docDTOList = JacksonUtil.parseDocDTOList(PreferenceUtil.getString(getApplicationContext(), PreferenceUtil.SHARED_PREFERENCES, PreferenceUtil.DOWNLOADED_BOOKS, ""));
            for (DocDTO downloadedDocDTO : docDTOList) {
                if (downloadedDocDTO.getUrlPdfStr().equalsIgnoreCase(docDTO.getUrlPdfStr())) return;
            }

            docDTO.setDownloadId(download.getId());
            docDTOList.add(0, docDTO);
            PreferenceUtil.setString(getApplicationContext(), PreferenceUtil.SHARED_PREFERENCES, PreferenceUtil.DOWNLOADED_BOOKS, JacksonUtil.stringifyDocDTOList(docDTOList));
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