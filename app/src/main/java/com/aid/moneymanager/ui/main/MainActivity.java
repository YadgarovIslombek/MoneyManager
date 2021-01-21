/*
 * *
 *  * Created by Yadgarov Islombek on 2021
 *  * Copyright (c).  All rights reserved.
 *  * Last modified 22.01.21 0:34
 *  بِسْمِ ٱللّٰهِ ٱلرَّحْمَٰنِ ٱلرَّحِيم  *
 *
 */

package com.aid.moneymanager.ui.main;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.app.ActivityOptions;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.view.View;


import com.aid.moneymanager.R;
import com.aid.moneymanager.ui.add_kiritish.AddWalletActivity;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.FirebaseApp;

public class MainActivity extends AppCompatActivity {
    private BroadcastReceiver qabulBroadcast;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(findViewById(R.id.toolbar));
        FirebaseApp.initializeApp(this);

        FloatingActionButton addEntryButton = findViewById(R.id.fab_contact_activity);
        addEntryButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                ActivityOptions options =
                        ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, addEntryButton, addEntryButton.getTransitionName());
                startActivity(new Intent(MainActivity.this, AddWalletActivity.class), options.toBundle());

            }
        });

        ViewPager viewPager = findViewById(R.id.pager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                ((AppBarLayout) toolbar.getParent()).setExpanded(true, true);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        TabLayout tabLayout = findViewById(R.id.tab);
        tabLayout.setupWithViewPager(viewPager);


    }
    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.aid.moneymanager.ACTION_LOGOUT");
        qabulBroadcast = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                finish();
            }
        };
        registerReceiver(qabulBroadcast, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(qabulBroadcast);
    }
}
