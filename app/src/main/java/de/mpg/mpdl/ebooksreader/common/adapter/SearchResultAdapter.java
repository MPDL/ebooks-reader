package de.mpg.mpdl.ebooksreader.common.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import de.mpg.mpdl.ebooksreader.activity.R;
import de.mpg.mpdl.ebooksreader.common.adapter.interf.BookClickListener;
import de.mpg.mpdl.ebooksreader.common.adapter.interf.OnLoadMoreListener;
import de.mpg.mpdl.ebooksreader.injection.module.glide.ImageLoader;
import de.mpg.mpdl.ebooksreader.model.dto.DocDTO;

public class SearchResultAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private boolean isLoading;
    private int visibleThreshold = 10;
    private int lastVisibleItem, totalItemCount;

    private List<DocDTO> searchResults;

    private BookClickListener mBookClickListener;
    private OnLoadMoreListener onLoadMoreListener;


    public SearchResultAdapter(RecyclerView recyclerView, List<DocDTO> searchResults) {
        this.searchResults = searchResults;
        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (onLoadMoreListener != null) {
                        onLoadMoreListener.onLoadMore();
                    }
                    isLoading = true;
                }
            }
        });
    }

    public class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View view) {
            super(view);
            progressBar = (ProgressBar) view.findViewById(R.id.searchResultLoadMoreProgressBar);
        }
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
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == VIEW_TYPE_ITEM) {
            return new SearchResultViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view_search_result, parent, false));
        } else if (viewType == VIEW_TYPE_LOADING) {
            return new LoadingViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view_loading, parent, false));
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case 0:
                SearchResultViewHolder searchResultViewHolder = (SearchResultViewHolder) holder;
                searchResultViewHolder.autoIncrementTextView.setText("" + (position + 1) );
                searchResultViewHolder.resultBookTitleTextView.setText(searchResults.get(position).getTitle());
                if (searchResults.get(position).getAuthorList() != null) {
                    searchResultViewHolder.resultBookAuthorTextView.setText(searchResults.get(position).getAuthorList().get(0));
                }
                if (searchResults.get(position).getCoverUrl() != null) {
                    ImageLoader.loadStringRes(searchResultViewHolder.resultBookCoverImageView, searchResults.get(position).getCoverUrl(), ImageLoader.defConfig, null);
                }
                break;
            case 1:
                LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
                loadingViewHolder.progressBar.setIndeterminate(true);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return searchResults.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return searchResults == null ? 0 : searchResults.size();
    }

    public void setLoaded() {
        isLoading = false;
    }

    public DocDTO getItem(int position) {
        return searchResults.get(position);
    }

    public void setClickListener(BookClickListener bookClickListener) {
        this.mBookClickListener = bookClickListener;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.onLoadMoreListener = mOnLoadMoreListener;
    }
}
