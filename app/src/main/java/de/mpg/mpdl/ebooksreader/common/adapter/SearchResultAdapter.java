package de.mpg.mpdl.ebooksreader.common.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import de.mpg.mpdl.ebooksreader.activity.R;
import de.mpg.mpdl.ebooksreader.model.BookModel;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.SearchResultViewHolder> {

    private List<BookModel> searchResults;
    private BookClickListener mBookClickListener;


    public SearchResultAdapter(List<BookModel> searchResults) {
        this.searchResults = searchResults;
    }

    public class SearchResultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView resultBookCoverImageView;
        public TextView resultBookTitleTextView;
        public TextView resultBookAuthorTextView;
        public TextView autoIncrementTextView;

        public SearchResultViewHolder(View itemView) {
            super(itemView);
            resultBookCoverImageView = itemView.findViewById(R.id.resultBookCoverImageView);
            resultBookTitleTextView = itemView.findViewById(R.id.resultBookTitleTextView);
            resultBookAuthorTextView = itemView.findViewById(R.id.resultBookAuthorTextView);
            autoIncrementTextView = itemView.findViewById(R.id.autoIncrementTextView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mBookClickListener != null) mBookClickListener.onItemClick(view, getAdapterPosition());
        }
    }


    @Override
    public SearchResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SearchResultViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.search_result_recycler_view, parent, false));
    }

    @Override
    public void onBindViewHolder(SearchResultViewHolder holder, int position) {
        holder.autoIncrementTextView.setText("" + (position + 1) );
        holder.resultBookTitleTextView.setText(searchResults.get(position).getTitle());
        holder.resultBookAuthorTextView.setText(searchResults.get(position).getAuthor());

    }

    @Override
    public int getItemCount() {
        return searchResults.size();
    }

    public BookModel getItem(int position) {
        return searchResults.get(position);
    }

    public void setClickListener(BookClickListener bookClickListener) {
        this.mBookClickListener = bookClickListener;
    }

    public interface BookClickListener {
        void onItemClick(View view, int position);
    }
}
