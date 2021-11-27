package com.example.chucknorrisapp;

import android.annotation.SuppressLint;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navigationView = findViewById(R.id.bottom_navigation);
        loadFragment(JokesFragment.getInstance());
        navigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.page_joke:
                    loadFragment(JokesFragment.getInstance());
                    return true;
                case R.id.page_web:
                    loadFragment(WebFragment.getInstance());
                    return true;
            }
            return false;
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fl_content, fragment);
        ft.commit();
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

