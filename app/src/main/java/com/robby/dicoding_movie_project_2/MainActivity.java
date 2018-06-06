package com.robby.dicoding_movie_project_2;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.HorizontalScrollView;
import android.widget.TabHost;
import android.widget.TabWidget;

import com.robby.dicoding_movie_project_2.adapter.ViewPagerAdapter;
import com.robby.dicoding_movie_project_2.fragment.NowPlayingFragment;
import com.robby.dicoding_movie_project_2.fragment.PopularMovieFragment;
import com.robby.dicoding_movie_project_2.fragment.SearchFragment;
import com.robby.dicoding_movie_project_2.fragment.UpcomingFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tab_host)
    FragmentTabHost tabHost;
    @BindView(R.id.vp_tab)
    ViewPager viewPager;
    @BindView(R.id.hsv_tab)
    HorizontalScrollView scrollView;
    @BindView(android.R.id.tabs)
    TabWidget tabWidget;

    private ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        tabHost.setup(this, getSupportFragmentManager(), R.id.real_tab_content);
        tabHost.addTab(tabHost.newTabSpec(getResources().getString(R.string.popular))
                        .setIndicator(getResources().getString(R.string.popular)),
                PopularMovieFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec(getResources().getString(R.string.now_playing))
                        .setIndicator(getResources().getString(R.string.now_playing)),
                NowPlayingFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec(getResources().getString(R.string.upcoming))
                        .setIndicator(getResources().getString(R.string.upcoming)),
                UpcomingFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec(getResources().getString(R.string.action_search))
                        .setIndicator(getResources().getString(R.string.action_search)),
                SearchFragment.class, null);
        viewPager.setAdapter(getViewPagerAdapter());
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                invalidateOptionsMenu();
                tabHost.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                viewPager.setCurrentItem(tabHost.getCurrentTab());
                int screenWidth = getWindowManager().getDefaultDisplay().getWidth();
                int leftX = tabWidget.getChildAt(tabHost.getCurrentTab()).getLeft();
                int newX = 0;

                newX = leftX + (tabWidget.getChildAt(tabHost.getCurrentTab()).getWidth() / 2) - (screenWidth / 2);
                if (newX < 0) {
                    newX = 0;
                }
                scrollView.scrollTo(newX, 0);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menus, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mn_lang:
                Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public ViewPagerAdapter getViewPagerAdapter() {
        if (viewPagerAdapter == null) {
            viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        }
        return viewPagerAdapter;
    }

}
