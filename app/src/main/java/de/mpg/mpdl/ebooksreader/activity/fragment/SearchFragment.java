package de.mpg.mpdl.ebooksreader.activity.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import de.mpg.mpdl.ebooksreader.activity.BookDescriptionActivity;
import de.mpg.mpdl.ebooksreader.activity.R;

public class SearchFragment extends Fragment {

    ImageView backImageView;
    ImageView ebooksSearchImageView;
    TextView ebooksDescriptionTextView;
    TextView ebooksLabel;
    SearchView ebooksSearchView;
    TextView searchHintTextView;
    RecyclerView searchResultRecyclerView;


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

        ebooksDescriptionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bookDescriptionIntent = new Intent(getActivity(), BookDescriptionActivity.class);
                startActivity(bookDescriptionIntent);
            }
        });
        super.onViewCreated(view, savedInstanceState);


    }
}
