package de.mpg.mpdl.ebooksreader.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;

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

        fragmentsViewPager = findViewById(R.id.fragmentsViewPager);
        tabLayout = findViewById(R.id.tabLayout);

        tabAdapter = new TabAdapter(getSupportFragmentManager(), this);
        tabAdapter.addFragment(new SearchFragment(), "search", R.drawable.ic_search_square);
        tabAdapter.addFragment(new CollectionFragment(), "collection", R.drawable.ic_library_square);
        tabAdapter.addFragment(new UserFragment(), "user", R.drawable.ic_user_square);

        fragmentsViewPager.setAdapter(tabAdapter);
        tabLayout.setupWithViewPager(fragmentsViewPager);

        highLightCurrentTab(0);

        fragmentsViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                highLightCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        Log.e("px2dp","190:"+px2dip(getApplicationContext(), 190));
        Log.e("px2dp","270:"+px2dip(getApplicationContext(), 270));
        Log.e("px2dp","30:"+px2dip(getApplicationContext(), 30));
        Log.e("px2dp","10:"+px2dip(getApplicationContext(), 10));
        Log.e("px2dp","50:"+px2dip(getApplicationContext(), 50));
        Log.e("px2dp","40:"+px2dip(getApplicationContext(), 40));
        Log.e("px2dp","20:"+px2dip(getApplicationContext(), 20));
        Log.e("px2dp","440:"+px2dip(getApplicationContext(), 440));
        Log.e("px2dp","320:"+px2dip(getApplicationContext(), 320));
        Log.e("px2dp","80:"+px2dip(getApplicationContext(), 80));

    }

    public float px2dip(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (pxValue / scale + 0.5f);
    }

    private void highLightCurrentTab(int position) {
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(null);
                tab.setCustomView(tabAdapter.getTabView(i));
            }
        }

        TabLayout.Tab tab = tabLayout.getTabAt(position);
        if (tab != null) {
            tab.setCustomView(null);
            tab.setCustomView(tabAdapter.getSelectedTabView(position));
        }
    }

}
