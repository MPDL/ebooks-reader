package de.mpg.mpdl.ebooksreader.activity.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import de.mpg.mpdl.ebooksreader.activity.R;
import de.mpg.mpdl.ebooksreader.common.adapter.BookShelfAdapter;
import de.mpg.mpdl.ebooksreader.model.DownloadedBookModel;

public class CollectionFragment extends Fragment {

    ImageView finishEditBookshelfImageView;
    ImageView deleteImageView;
    ImageView editImageView;
    TextView booksCountTextView;
    RecyclerView bookshelfRecyclerView;
    BookShelfAdapter bookShelfAdapter;
    List<DownloadedBookModel> bookModelList = new ArrayList<>();
    public CollectionFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_collection, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        finishEditBookshelfImageView = getActivity().findViewById(R.id.finishEditBookshelfImageView);
        deleteImageView = getActivity().findViewById(R.id.deleteImageView);
        editImageView = getActivity().findViewById(R.id.editImageView);
        bookshelfRecyclerView = getActivity().findViewById(R.id.bookshelfRecyclerView);
        booksCountTextView = getActivity().findViewById(R.id.booksCountTextView);

        for (int i = 0; i < 10; i++) {
            bookModelList.add(new DownloadedBookModel("Book No."+(i+1), "Robot", false));
        }
        booksCountTextView.setText(bookModelList.size() + " Books");

        RecyclerView.LayoutManager bookshelfRecyclerViewLayoutManager = new GridLayoutManager(getActivity(),3);
        bookshelfRecyclerView.setLayoutManager(bookshelfRecyclerViewLayoutManager);
        bookShelfAdapter = new BookShelfAdapter(bookModelList, false);
        bookshelfRecyclerView.setAdapter(bookShelfAdapter);

        editImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishEditBookshelfImageView.setVisibility(View.VISIBLE);
                deleteImageView.setVisibility(View.VISIBLE);
                editImageView.setVisibility(View.GONE);
                bookShelfAdapter.setInEditMode(true);
                for (int i = 0; i < bookModelList.size(); i++) {
                    bookModelList.get(i).setChecked(false);
                }
                bookShelfAdapter.notifyDataSetChanged();
            }
        });

        finishEditBookshelfImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishEditBookshelfImageView.setVisibility(View.GONE);
                deleteImageView.setVisibility(View.GONE);
                editImageView.setVisibility(View.VISIBLE);
                bookShelfAdapter.setInEditMode(false);
                bookShelfAdapter.notifyDataSetChanged();
            }
        });

        deleteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookModelList.removeIf(new Predicate<DownloadedBookModel>() {
                    @Override
                    public boolean test(DownloadedBookModel b) {
                        return b.isChecked();
                    }
                });

                bookShelfAdapter.notifyDataSetChanged();
                booksCountTextView.setText(bookModelList.size() + " Books");
            }
        });

        super.onViewCreated(view, savedInstanceState);
    }
}
