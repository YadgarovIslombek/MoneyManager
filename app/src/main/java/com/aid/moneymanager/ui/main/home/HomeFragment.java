/*
 * *
 *  * Created by Yadgarov Islombek on 2021
 *  * Copyright (c).  All rights reserved.
 *  * Last modified 21.01.21 22:20
 *  بِسْمِ ٱللّٰهِ ٱلرَّحْمَٰنِ ٱلرَّحِيم  *
 *
 */

package com.aid.moneymanager.ui.main.home;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aid.moneymanager.R;
import com.aid.moneymanager.base.BaseFragment;
import com.aid.moneymanager.firebase.ListDataSet;
import com.aid.moneymanager.firebase.models.User;
import com.aid.moneymanager.firebase.models.Wallet;
import com.aid.moneymanager.lib.OlchovLibFromGit;

import java.util.ArrayList;

public class HomeFragment  extends BaseFragment {
    private User user;
    private ListDataSet<Wallet> walletEntryListDataSet;

    public static final CharSequence TITLE = "Home";
    private OlchovLibFromGit olchov;


    public static HomeFragment newInstance() {

        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
}