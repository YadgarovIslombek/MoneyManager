/*
 * *
 *  * Created by Yadgarov Islombek on 2021
 *  * Copyright (c).  All rights reserved.
 *  * Last modified 19.01.21 14:55
 *  بِسْمِ ٱللّٰهِ ٱلرَّحْمَٰنِ ٱلرَّحِيم  *
 *
 */

package com.aid.moneymanager.ui.main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private static int TAB_SON =3;

    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
//        switch (position){
//            case 0:
//            return HomeFragment.newInstance();
//            case 1:
//                return HistoryFragment.newInstance();
//            case 2:
//                return StatistikaFragment.newInstance();
//        }
        return null;
    }

    @Override
    public int getCount() {
        return TAB_SON;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
//        switch (position){
//            case 0:
//                return HomeFragment.TITLE;
//            case 1:
//                return HistoryFragment.TITLE;
//            case 2:
//                return StatistikaFragment.TITLE;
//        }
        return super.getPageTitle(position);
    }
}
