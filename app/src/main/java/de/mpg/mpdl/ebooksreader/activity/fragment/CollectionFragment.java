package de.mpg.mpdl.ebooksreader.activity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.mpg.mpdl.ebooksreader.activity.R;

public class CollectionFragment extends Fragment {


    public CollectionFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_collection, container, false);
    }
}
