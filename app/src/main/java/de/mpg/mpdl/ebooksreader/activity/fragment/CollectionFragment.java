package de.mpg.mpdl.ebooksreader.activity.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.support.v7.widget.SearchView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import de.mpg.mpdl.ebooksreader.activity.R;
import de.mpg.mpdl.ebooksreader.common.adapter.BookShelfAdapter;
import de.mpg.mpdl.ebooksreader.model.DownloadedBookModel;
import de.mpg.mpdl.ebooksreader.model.dto.DocDTO;
import de.mpg.mpdl.ebooksreader.utils.Data;
import de.mpg.mpdl.ebooksreader.utils.JacksonUtil;
import de.mpg.mpdl.ebooksreader.utils.PreferenceUtil;

public class CollectionFragment extends Fragment {

    ImageView finishEditBookshelfImageView;
    ImageView deleteImageView;
    ImageView editImageView;
    TextView booksCountTextView;
    SearchView shelfBooksSearchView;
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
        shelfBooksSearchView = getActivity().findViewById(R.id.shelfBooksSearchView);
        bookshelfRecyclerView = getActivity().findViewById(R.id.bookshelfRecyclerView);
        booksCountTextView = getActivity().findViewById(R.id.booksCountTextView);

        updateBookModelList();
        booksCountTextView.setText(bookModelList.size() + " Books");

        shelfBooksSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                bookModelList.removeIf(new Predicate<DownloadedBookModel>() {
                    @Override
                    public boolean test(DownloadedBookModel downloadedBookModel) {
                        String regex = "(?i).*" + query + ".*";
                        return !Pattern.matches(regex, downloadedBookModel.getTitle());
                    }
                });

                bookShelfAdapter.notifyDataSetChanged();
                booksCountTextView.setText(bookModelList.size() + " Books");
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText == null || newText.isEmpty()) {
                    updateBookModelList();
                    booksCountTextView.setText(bookModelList.size() + " Books");
                }
                return false;
            }
        });

        RecyclerView.LayoutManager bookshelfRecyclerViewLayoutManager = new GridLayoutManager(getActivity(),3);
        bookshelfRecyclerView.setLayoutManager(bookshelfRecyclerViewLayoutManager);
        bookShelfAdapter = new BookShelfAdapter(getActivity(), bookModelList, false);
        bookshelfRecyclerView.setAdapter(bookShelfAdapter);

        editImageView.setOnClickListener(view1 -> {
            finishEditBookshelfImageView.setVisibility(View.VISIBLE);
            deleteImageView.setVisibility(View.VISIBLE);
            editImageView.setVisibility(View.GONE);
            bookShelfAdapter.setInEditMode(true);
            for (int i = 0; i < bookModelList.size(); i++) {
                bookModelList.get(i).setChecked(false);
            }
            bookShelfAdapter.notifyDataSetChanged();
        });

        finishEditBookshelfImageView.setOnClickListener(view12 -> {
            finishEditBookshelfImageView.setVisibility(View.GONE);
            deleteImageView.setVisibility(View.GONE);
            editImageView.setVisibility(View.VISIBLE);
            bookShelfAdapter.setInEditMode(false);
            bookShelfAdapter.notifyDataSetChanged();
        });

        deleteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookModelList.removeIf(new Predicate<DownloadedBookModel>() {
                    @Override
                    public boolean test(DownloadedBookModel b) {
                        if(b.isChecked()) {
                            String dir = Data.getSaveDir() + "/books/" + b.getIsbn() + ".pdf";
                            File f = new File(dir);
                            Log.e("log", f.exists() + "");
                            Log.e("log", f.getPath());
                            boolean d0 = f.delete();
                            Log.w("Delete Check", "File deleted: " + dir + "/myFile " + d0);
                            List<DocDTO> docDTOList = JacksonUtil.parseDocDTOList(PreferenceUtil.getString(getActivity(), PreferenceUtil.SHARED_PREFERENCES, PreferenceUtil.DOWNLOADED_BOOKS, ""));
                            docDTOList.removeIf(new Predicate<DocDTO>() {
                                @Override
                                public boolean test(DocDTO docDTO) {
                                    return b.getIsbn().equalsIgnoreCase(docDTO.getIsbn().get(0));
                                }
                            });
                            PreferenceUtil.setString(getActivity(), PreferenceUtil.SHARED_PREFERENCES, PreferenceUtil.DOWNLOADED_BOOKS, JacksonUtil.stringifyDocDTOList(docDTOList));
                        }
                        return b.isChecked();
                    }
                });


                bookShelfAdapter.notifyDataSetChanged();
                booksCountTextView.setText(bookModelList.size() + " Books");
            }
        });

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            updateBookModelList();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateBookModelList();
    }

    private void updateBookModelList() {
        bookModelList.clear();
        List<DocDTO> docDTOList = JacksonUtil.parseDocDTOList(PreferenceUtil.getString(getActivity(), PreferenceUtil.SHARED_PREFERENCES, PreferenceUtil.DOWNLOADED_BOOKS, ""));
        for (DocDTO docDTO : docDTOList) {
            DownloadedBookModel downloadedBookModel = new DownloadedBookModel(docDTO.getTitle(),
                    docDTO.getAuthorList()!=null && docDTO.getAuthorList().size() > 0 ? docDTO.getAuthorList().get(0) : "",
                    docDTO.getIsbn()!=null && docDTO.getIsbn().size() > 0 ? docDTO.getIsbn().get(0) : "", false);
            bookModelList.add(downloadedBookModel);
        }
        if(bookShelfAdapter!=null) {
            bookShelfAdapter.notifyDataSetChanged();
        }
    }
}
