/*
 * *
 *  * Created by Yadgarov Islombek on 2021
 *  * Copyright (c).  All rights reserved.
 *  * Last modified 23.01.21 2:28
 *  بِسْمِ ٱللّٰهِ ٱلرَّحْمَٰنِ ٱلرَّحِيم  *
 *
 */

package com.aid.moneymanager.ui.main.options;


import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import pl.cyfrogen.budget.R;

public class OptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Options");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        onBackPressed();
        return true;
    }
}
