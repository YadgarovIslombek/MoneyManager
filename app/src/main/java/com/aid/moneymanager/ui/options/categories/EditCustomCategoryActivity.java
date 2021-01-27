/*
 * *
 *  * Created by Yadgarov Islombek on 2021
 *  * Copyright (c).  All rights reserved.
 *  * Last modified 26.01.21 0:08
 *  بِسْمِ ٱللّٰهِ ٱلرَّحْمَٰنِ ٱلرَّحِيم  *
 *
 */

package com.aid.moneymanager.ui.options.categories;

import android.content.res.ColorStateList;
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
import com.aid.moneymanager.firebase.models.WalletEntryCategory;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.FirebaseDatabase;
import com.pes.androidmaterialcolorpickerdialog.ColorPicker;


public class EditCustomCategoryActivity extends BaseActivity {

    private TextInputEditText selectNameEditText;
    private Button selectColorButton;
    private Button editCustomCategoryButton;
    private ImageView iconImageView;
    private int selectedColor;
    private String categoryID;
    private Button removeCustomCategoryButton;
    private String categoryName;
    private TextInputLayout selectNameInputLayout;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        categoryID = getIntent().getExtras().getString("category-id");
        categoryName = getIntent().getExtras().getString("category-name");
        selectedColor = getIntent().getExtras().getInt("category-color");

        setContentView(R.layout.activity_edit_custom_category);
        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("O'zgartirish");


        iconImageView = findViewById(R.id.icon_imageview);
        iconImageView.setBackgroundTintList(ColorStateList.valueOf(selectedColor));
        selectNameEditText = findViewById(R.id.select_name_edittext);
        selectNameEditText.setText(categoryName);
        selectNameInputLayout = findViewById(R.id.select_name_inputlayout);
        selectColorButton = findViewById(R.id.select_color_button);
        selectColorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ColorPicker colorPicker = new ColorPicker(EditCustomCategoryActivity.this,
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

        editCustomCategoryButton = findViewById(R.id.edit_custom_category_button);
        editCustomCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    editCustomCategory(selectNameEditText.getText().toString(), "#" + Integer.toHexString(selectedColor));
                } catch (StringException e) {
                    selectNameInputLayout.setError(e.getMessage());
                }

            }
        });

        removeCustomCategoryButton = findViewById(R.id.remove_custom_category_button);
        removeCustomCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference()
                        .child("users").child(getUid()).child("customCategories").child(categoryID).removeValue();
                finish();

            }
        });
    }

    private void editCustomCategory(String categoryName, String categoryHtmlCode) throws StringException {
        if (categoryName == null || categoryName.length() == 0)
            throw new StringException("Matn kiritilmagan");

        FirebaseDatabase.getInstance().getReference()
                .child("users").child(getUid()).child("customCategories").child(categoryID).setValue(
                new WalletEntryCategory(categoryName, categoryHtmlCode));
        finish();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        onBackPressed();
        return true;
    }
}
