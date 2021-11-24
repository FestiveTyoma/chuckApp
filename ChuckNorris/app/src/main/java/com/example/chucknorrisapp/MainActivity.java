package com.example.chucknorrisapp;

import android.annotation.SuppressLint;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter pagerAdapter;
        pagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        ViewPager pager = findViewById(R.id.pager);
        pager.setAdapter(pagerAdapter);
        //binding ViewPager with TabLayout
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(pager);
        tabLayout.getTabAt(0).setIcon(R.drawable.joke_icon);
        tabLayout.getTabAt(1).setIcon(R.drawable.web_icon);
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getVisibleFragment();
        IOnBackPressed backPressedListener;
        if (fragment instanceof IOnBackPressed) {
            backPressedListener = (IOnBackPressed) fragment;
            backPressedListener.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }

    //get Current fragment
    @SuppressLint("RestrictedApi")
    public Fragment getVisibleFragment() {
        FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();
        for (Fragment fragment : fragmentManager.getFragments()) {
            if (fragment.isMenuVisible())
                return fragment;
        }
        return null;
    }
}

