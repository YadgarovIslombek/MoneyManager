/*
 * *
 *  * Created by Yadgarov Islombek on 2021
 *  * Copyright (c).  All rights reserved.
 *  * Last modified 19.01.21 16:33
 *  بِسْمِ ٱللّٰهِ ٱلرَّحْمَٰنِ ٱلرَّحِيم  *
 *
 */

package com.aid.moneymanager.ui.add_kiritish;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.aid.moneymanager.R;
import com.aid.moneymanager.activityContainer.OvalActivity;
import com.aid.moneymanager.firebase.models.User;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

public class AddWalletActivity extends OvalActivity {
    private Spinner selectCategorySpinner;
    private TextInputEditText selectNameEditText;
    private Calendar chosenDate;
    private TextInputEditText selectAmountEditText;
    private TextView chooseDayTextView;
    private TextView chooseTimeTextView;
    private Spinner selectTypeSpinner;
    private User user;
    private TextInputLayout selectAmountInputLayout;
    private TextInputLayout selectNameInputLayout;

    public AddWalletEntryActivity() {
        super(R.layout.activity_add_wallet, R.id.fab_contact_activity, R.id.root_layout, R.id.root_layout2);
    }
    @Override
    public void onInitialized(Bundle savedInstanceState) {
        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Kirim-Chiqim qo'shish");
        selectCategorySpinner = findViewById(R.id.select_category_spinner);
        selectNameEditText = findViewById(R.id.select_name_edittext);
        selectNameInputLayout = findViewById(R.id.select_name_inputlayout);
        selectTypeSpinner = findViewById(R.id.select_spinner);
        Button addEntryButton = findViewById(R.id.add_button);
        chooseTimeTextView = findViewById(R.id.vaqt_tanlash_textview);
        chooseDayTextView = findViewById(R.id.kun_tanlash_textview);
        selectAmountEditText = findViewById(R.id.amount_edittext);
        selectAmountInputLayout = findViewById(R.id.amount_inputlayout);
        chosenDate = Calendar.getInstance();
    }
}