/*
 * *
 *  * Created by Yadgarov Islombek on 2021
 *  * Copyright (c).  All rights reserved.
 *  * Last modified 26.01.21 0:06
 *  بِسْمِ ٱللّٰهِ ٱلرَّحْمَٰنِ ٱلرَّحِيم  *
 *
 */

package com.aid.moneymanager.ui.options.categories;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;

import com.aid.moneymanager.R;
import com.aid.moneymanager.base.BaseActivity;
import com.aid.moneymanager.exceptions.StringException;
import com.aid.moneymanager.firebase.FirebaseElement;
import com.aid.moneymanager.firebase.FirebaseObserver;
import com.aid.moneymanager.firebase.models.User;
import com.aid.moneymanager.firebase.models.WalletEntryCategory;
import com.aid.moneymanager.firebase.viewModel_fact.UserProfileViewModelFactory;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.FirebaseDatabase;
import com.pes.androidmaterialcolorpickerdialog.ColorPicker;


public class AddCustomCategoryActivity extends BaseActivity {

    private TextInputEditText selectNameEditText;
    private Button selectColorButton;
    private Button addCustomCategoryButton;
    private User user;
    private ImageView iconImageView;
    private int selectedColor = Color.parseColor("#000000");
    private TextInputLayout selectNameInputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_custom_category);
        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Kategoriya qo'shish");

        UserProfileViewModelFactory.getModel(getUid(), this).observe(this, new FirebaseObserver<FirebaseElement<User>>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onChanged(FirebaseElement<User> firebaseElement) {
                if (firebaseElement.hasNoError()) {
                    AddCustomCategoryActivity.this.user = firebaseElement.getElement();
                    dataUpdated();
                }
            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void dataUpdated() {
        if (user == null) return;
        iconImageView = findViewById(R.id.icon_imageview);
        iconImageView.setBackgroundTintList(ColorStateList.valueOf(selectedColor));
        selectNameEditText = findViewById(R.id.select_name_edittext);
        selectNameInputLayout = findViewById(R.id.select_name_inputlayout);
        selectColorButton = findViewById(R.id.select_color_button);
        selectColorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ColorPicker colorPicker = new ColorPicker(AddCustomCategoryActivity.this,
                        (selectedColor >> 16) & 0xFF,
                        (selectedColor >> 8) & 0xFF,
                        (selectedColor >> 0) & 0xFF);
                colorPicker.show();

                Button okColor = colorPicker.findViewById(R.id.okColorButton);

                okColor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectedColor = colorPicker.getColor();
                        iconImageView.setBackgroundTintList(ColorStateList.valueOf(selectedColor));
                        colorPicker.dismiss();
                    }
                });
            }
        });

        addCustomCategoryButton = findViewById(R.id.add_custom_category_button);
        addCustomCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    addCustomCategory(selectNameEditText.getText().toString(), "#" + Integer.toHexString(selectedColor));
                } catch (StringException e) {
                    selectNameInputLayout.setError(e.getMessage());
                }


            }
        });
    }

    private void addCustomCategory(String categoryName, String categoryHtmlCode) throws StringException {
        if (categoryName == null || categoryName.length() == 0)
            throw new StringException("Matn kiritilmagan");

        FirebaseDatabase.getInstance().getReference()
                .child("users").child(getUid()).child("customCategories").push().setValue(
                new WalletEntryCategory(categoryName, categoryHtmlCode));
        finish();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        onBackPressed();
        return true;
    }
}
