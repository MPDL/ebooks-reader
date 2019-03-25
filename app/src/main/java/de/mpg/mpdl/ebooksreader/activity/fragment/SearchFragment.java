package de.mpg.mpdl.ebooksreader.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import de.mpg.mpdl.ebooksreader.activity.BookDescriptionActivity;
import de.mpg.mpdl.ebooksreader.activity.R;

public class SearchFragment extends Fragment {

    TextView ebooksDescriptionTextView;

    public SearchFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ebooksDescriptionTextView = getActivity().findViewById(R.id.ebooksDescriptionTextView);
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
