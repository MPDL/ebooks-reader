package de.mpg.mpdl.ebooksreader.common.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.List;

import de.mpg.mpdl.ebooksreader.DocumentActivity;
import de.mpg.mpdl.ebooksreader.activity.MainActivity;
import de.mpg.mpdl.ebooksreader.activity.R;
import de.mpg.mpdl.ebooksreader.injection.module.glide.ImageLoader;
import de.mpg.mpdl.ebooksreader.model.DownloadedBookModel;
import de.mpg.mpdl.ebooksreader.model.dto.DocDTO;
import de.mpg.mpdl.ebooksreader.utils.Data;
import de.mpg.mpdl.ebooksreader.utils.JacksonUtil;
import de.mpg.mpdl.ebooksreader.utils.PreferenceUtil;

public class BookShelfAdapter extends RecyclerView.Adapter<BookShelfAdapter.BookShelfViewHolder> {

    private List<DownloadedBookModel> downloadedBooks;
    private boolean inEditMode;
    private Context context;

    public BookShelfAdapter(Context context, List<DownloadedBookModel> downloadedBooks, boolean inEditMode) {
        this.context = context;
        this.downloadedBooks = downloadedBooks;
        this.inEditMode = inEditMode;
    }

    public class BookShelfViewHolder extends RecyclerView.ViewHolder {
        public ImageView downloadedBookCoverImageView;
        public TextView downloadedBookTitle;
        public CheckBox editBookCheckBox;

        public BookShelfViewHolder(View itemView) {
            super(itemView);
            downloadedBookCoverImageView = itemView.findViewById(R.id.downloadedBookCoverImageView);
            downloadedBookTitle = itemView.findViewById(R.id.downloadedBookTitle);
            editBookCheckBox = itemView.findViewById(R.id.editBookCheckBox);
        }
    }

    @Override
    public BookShelfViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BookShelfViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view_bookshelf, parent, false));
    }

    @Override
    public void onBindViewHolder(BookShelfViewHolder holder, final int position) {
        holder.downloadedBookTitle.setText(downloadedBooks.get(position).getTitle());
        if (inEditMode) {
            if (downloadedBooks.get(position).isChecked()) {
                holder.editBookCheckBox.setChecked(true);
            } else {
                holder.editBookCheckBox.setChecked(false);
            }
            holder.editBookCheckBox.setVisibility(View.VISIBLE);
        } else {
            holder.editBookCheckBox.setVisibility(View.GONE);
        }

        holder.editBookCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(position < downloadedBooks.size()) {
                    downloadedBooks.get(position).setChecked(isChecked);
                }
            }
        });

        holder.downloadedBookCoverImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!inEditMode) {
                    openBook(position);
                }
            }
        });

        holder.downloadedBookTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!inEditMode) {
                    openBook(position);
                }
            }
        });

        ImageLoader.loadStringRes(holder.downloadedBookCoverImageView, "https://ebooks4-qa.mpdl.mpg.de/ebooks/Cover/Show?size=small&isbn=" + downloadedBooks.get(position).getIsbn(), ImageLoader.defConfig, null);
    }

    @Override
    public int getItemCount() {
        return downloadedBooks.size();
    }

    public DownloadedBookModel getItem(int position) {
        return downloadedBooks.get(position);
    }

    public void setInEditMode(boolean inEditMode) {
        this.inEditMode = inEditMode;
    }

    private void openBook(int position) {
        File file = new File(Data.getSaveDir() + "/books/" + downloadedBooks.get(position).getIsbn());
        if (!file.isFile())
            return;

        List<DocDTO> docDTOList = JacksonUtil.parseDocDTOList(PreferenceUtil.getString(context, PreferenceUtil.SHARED_PREFERENCES, PreferenceUtil.DOWNLOADED_BOOKS, ""));
        int index = -1;
        for (int i = 0; i < docDTOList.size(); i++) {
            if ( null != docDTOList && docDTOList.size() > 0 && docDTOList.get(i).getIsbn().get(0).equalsIgnoreCase(downloadedBooks.get(position).getIsbn())) {
                index = i;
                break;
            }
        }

        if (index == -1) return;

        DocDTO docDTO = docDTOList.get(index);
        docDTOList.remove(index);
        docDTOList.add(0, docDTO);

        PreferenceUtil.setString(context, PreferenceUtil.SHARED_PREFERENCES, PreferenceUtil.DOWNLOADED_BOOKS, JacksonUtil.stringifyDocDTOList(docDTOList));

        Intent intent = new Intent(context, DocumentActivity.class);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.fromFile(file));
        context.startActivity(intent);
    }
}
