package com.example.chucknorrisapp;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

//Adapter for tabs
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {


        switch (position) {
            case 0:
                return "Jokes";
            case 1:
                return "Web";

        }
        return null;
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new JokesFragment();
            case 1:
                return new WebFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
