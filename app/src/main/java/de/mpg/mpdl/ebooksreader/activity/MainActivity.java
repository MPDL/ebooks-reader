package de.mpg.mpdl.ebooksreader.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;


import de.mpg.mpdl.ebooksreader.activity.fragment.CollectionFragment;
import de.mpg.mpdl.ebooksreader.activity.fragment.SearchFragment;
import de.mpg.mpdl.ebooksreader.activity.fragment.UserFragment;
import de.mpg.mpdl.ebooksreader.base.BaseCompatActivity;
import de.mpg.mpdl.ebooksreader.common.adapter.TabAdapter;

public class MainActivity extends BaseCompatActivity {

    private TabAdapter tabAdapter;
    private TabLayout tabLayout;
    private ViewPager fragmentsViewPager;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        Intent intent = new Intent(this, LibraryActivity.class);
        startActivity(intent);

        fragmentsViewPager = findViewById(R.id.fragmentsViewPager);
        tabLayout = findViewById(R.id.tabLayout);

        tabAdapter = new TabAdapter(getSupportFragmentManager());
        tabAdapter.addFragment(new SearchFragment(), "search");
        tabAdapter.addFragment(new CollectionFragment(), "collection");
        tabAdapter.addFragment(new UserFragment(), "user");

        fragmentsViewPager.setAdapter(tabAdapter);
        tabLayout.setupWithViewPager(fragmentsViewPager);

    }
}
