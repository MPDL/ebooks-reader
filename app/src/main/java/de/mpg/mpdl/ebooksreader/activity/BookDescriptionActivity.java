package de.mpg.mpdl.ebooksreader.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import de.mpg.mpdl.ebooksreader.base.BaseCompatActivity;
import de.mpg.mpdl.ebooksreader.injection.module.glide.ImageLoader;
import de.mpg.mpdl.ebooksreader.model.dto.DocDTO;
import de.mpg.mpdl.ebooksreader.utils.JacksonUtil;

public class BookDescriptionActivity extends BaseCompatActivity {
    ImageView detailCoverImageView;
    ImageView detailBackImageView;
    TextView detailAbstractTextView;
    TextView detailTitleTextView;
    TextView detailSubtitleTextView;
    TextView detailAuthorTextView;
    TextView detailPublishDateTextView;
    TextView detailPublisherTextView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_book_description;
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        String docDTOStr = getIntent().getStringExtra("docDTOStr");
        DocDTO docDTO = JacksonUtil.parseDocDTO(docDTOStr);

        detailCoverImageView = findViewById(R.id.detailCoverImageView);
        detailBackImageView = findViewById(R.id.detailBackImageView);
        detailAbstractTextView = findViewById(R.id.detailAbstractTextView);
        detailTitleTextView = findViewById(R.id.detailTitleTextView);
        detailSubtitleTextView = findViewById(R.id.detailSubtitleTextView);
        detailAuthorTextView = findViewById(R.id.detailAuthorTextView);
        detailPublishDateTextView = findViewById(R.id.detailPublishDateTextView);
        detailPublisherTextView = findViewById(R.id.detailPublisherTextView);

        if (null != docDTO.getCoverUrl()) {
            ImageLoader.loadStringRes(detailCoverImageView, docDTO.getCoverUrl(), ImageLoader.defConfig, null);
        }

        detailBackImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

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
    }

}
