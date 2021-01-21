/*
 * *
 *  * Created by Yadgarov Islombek on 2021
 *  * Copyright (c).  All rights reserved.
 *  * Last modified 22.01.21 0:15
 *  بِسْمِ ٱللّٰهِ ٱلرَّحْمَٰنِ ٱلرَّحِيم  *
 *
 */

package com.aid.moneymanager.ui.main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.aid.moneymanager.ui.main.history.HistoryFragment;
import com.aid.moneymanager.ui.main.home.HomeFragment;
import com.aid.moneymanager.ui.main.statistics.StatistikaFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private static int TAB_SON =3;

    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
           case 0:
           return HomeFragment.newInstance();
           case 1:
                return HistoryFragment.newInstance();
            case 2:
                return StatistikaFragment.newInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return TAB_SON;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return HomeFragment.TITLE;
            case 1:
                return HistoryFragment.TITLE;
            case 2:
                return StatistikaFragment.TITLE;
        }
        return super.getPageTitle(position);
    }
}
