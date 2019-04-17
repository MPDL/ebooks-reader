package de.mpg.mpdl.ebooksreader.common.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import de.mpg.mpdl.ebooksreader.activity.R;
import de.mpg.mpdl.ebooksreader.model.DownloadedBookModel;

public class BookShelfAdapter extends RecyclerView.Adapter<BookShelfAdapter.BookShelfViewHolder> {

    private List<DownloadedBookModel> downloadedBooks;
    private boolean inEditMode;


    public BookShelfAdapter(List<DownloadedBookModel> downloadedBooks, boolean inEditMode) {
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
        return new BookShelfViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.bookshelf_recycler_view, parent, false));
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
}
