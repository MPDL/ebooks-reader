package de.mpg.mpdl.ebooksreader.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.mpg.mpdl.ebooksreader.activity.BookDescriptionActivity;
import de.mpg.mpdl.ebooksreader.activity.R;
import de.mpg.mpdl.ebooksreader.common.adapter.SearchResultAdapter;
import de.mpg.mpdl.ebooksreader.model.BookModel;

public class SearchFragment extends Fragment implements SearchResultAdapter.BookClickListener{

    ImageView backImageView;
    ImageView ebooksSearchImageView;
    TextView ebooksDescriptionTextView;
    TextView ebooksLabel;
    SearchView ebooksSearchView;
    TextView searchHintTextView;
    RecyclerView searchResultRecyclerView;
    SearchResultAdapter searchResultAdapter;


    public SearchFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        backImageView = getActivity().findViewById(R.id.backImageView);
        ebooksSearchImageView = getActivity().findViewById(R.id.ebooksSearchImageView);
        searchResultRecyclerView = getActivity().findViewById(R.id.searchResultRecyclerView);
        ebooksLabel = getActivity().findViewById(R.id.ebooksLabel);
        ebooksDescriptionTextView = getActivity().findViewById(R.id.ebooksDescriptionTextView);
        searchHintTextView = getActivity().findViewById(R.id.searchHintTextView);
        ebooksSearchView = getActivity().findViewById(R.id.ebooksSearchView);

        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ebooksSearchImageView.setVisibility(View.VISIBLE);
                TabLayout tabLayout = getActivity().findViewById(R.id.tabLayout);
                tabLayout.setVisibility(View.VISIBLE);
                ebooksLabel.setVisibility(View.VISIBLE);
                ebooksDescriptionTextView.setVisibility(View.VISIBLE);
                searchHintTextView.setVisibility(View.VISIBLE);

                backImageView.setVisibility(View.GONE);
                searchResultRecyclerView.setVisibility(View.GONE);
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)ebooksSearchView.getLayoutParams();
                params.setMargins(params.leftMargin, 435, params.rightMargin, params.bottomMargin);
                ebooksSearchView.setLayoutParams(params);
                ebooksSearchView.setQuery("", false);
                ebooksSearchView.clearFocus();
            }
        });

        searchHintTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ebooksSearchImageView.setVisibility(View.GONE);
                TabLayout tabLayout = getActivity().findViewById(R.id.tabLayout);
                tabLayout.setVisibility(View.GONE);
                ebooksLabel.setVisibility(View.GONE);
                ebooksDescriptionTextView.setVisibility(View.GONE);
                searchHintTextView.setVisibility(View.GONE);

                backImageView.setVisibility(View.VISIBLE);
                searchResultRecyclerView.setVisibility(View.VISIBLE);
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)ebooksSearchView.getLayoutParams();
                params.setMargins(params.leftMargin, 210, params.rightMargin, params.bottomMargin);
                ebooksSearchView.setLayoutParams(params);
                ebooksSearchView.setIconified(false);

            }
        });


        searchResultRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        List<BookModel> bookModelList = new ArrayList<>();
        BookModel dummyBook = new BookModel("robots", "Robot");
        bookModelList.add(dummyBook);
        bookModelList.add(dummyBook);
        searchResultAdapter = new SearchResultAdapter(bookModelList);
        searchResultAdapter.setClickListener(this);
        searchResultRecyclerView.setAdapter(searchResultAdapter);

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent bookDescriptionIntent = new Intent(getActivity(), BookDescriptionActivity.class);
        startActivity(bookDescriptionIntent);
    }
}
