/*
 * *
 *  * Created by Yadgarov Islombek on 2021
 *  * Copyright (c).  All rights reserved.
 *  * Last modified 19.01.21 13:06
 *  بِسْمِ ٱللّٰهِ ٱلرَّحْمَٰنِ ٱلرَّحِيم  *
 *
 */

package com.aid.moneymanager.ui.main;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.aid.moneymanager.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FirebaseApp.initializeApp(this);
        FloatingActionButton addButton = findViewById(R.id.add_fab);
        addButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                ActivityOptions activityOptions
                        = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, addButton, addButton.getTransitionName());
                        startActivity(new Intent(MainActivity.this, AddWalletActivity.class));
            }

        });
        ViewPager viewPager = findViewById(R.id.pager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

    }
}