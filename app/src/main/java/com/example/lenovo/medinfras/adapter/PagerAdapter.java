package com.example.lenovo.medinfras.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.lenovo.medinfras.activity.PatientDetailActivity;
import com.example.lenovo.medinfras.fragment.CatatanPerawatFragment;
import com.example.lenovo.medinfras.fragment.TabView2Fragment;
import com.example.lenovo.medinfras.fragment.TabViewFragment;

/**
 * Created by Lenovo on 1/15/2018.
 */

public class PagerAdapter extends FragmentPagerAdapter {
    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        switch (position) {
            case 0:
                fragment = new CatatanPerawatFragment();
                break;
            case 1:
                fragment = new TabView2Fragment();
                break;
            case 2:
                fragment = new TabViewFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
