/*
 * *
 *  * Created by Yadgarov Islombek on 2021
 *  * Copyright (c).  All rights reserved.
 *  * Last modified 23.01.21 3:13
 *  بِسْمِ ٱللّٰهِ ٱلرَّحْمَٰنِ ٱلرَّحِيم  *
 *
 */

package com.aid.moneymanager.ui.add_kiritish;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.aid.moneymanager.R;
import com.aid.moneymanager.activityConteyner.OvalActivity;
import com.aid.moneymanager.exceptions.StringException;
import com.aid.moneymanager.exceptions.NolBalanceDifferenceException;
import com.aid.moneymanager.firebase.FirebaseElement;
import com.aid.moneymanager.firebase.FirebaseObserver;
import com.aid.moneymanager.firebase.models.User;
import com.aid.moneymanager.firebase.models.WalletEnter;
import com.aid.moneymanager.firebase.viewModel_fact.UserProfileViewModelFactory;
import com.aid.moneymanager.models.Category;
import com.aid.moneymanager.utils.CategorysHelper;
import com.aid.moneymanager.utils.CurrencyHelper;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
    private EnterTypeAdapter typeAdapter;

    public AddWalletActivity() {
        super(R.layout.activity_add_wallet, R.id.activity_contact_fab, R.id.root_layout, R.id.root_layout2);
    }

    @Override
    public void onInitialized(Bundle savedInstanceState) {
        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add wallet entry");

        selectCategorySpinner = findViewById(R.id.select_category_spinner);
        selectNameEditText = findViewById(R.id.select_name_edittext);
        selectNameInputLayout = findViewById(R.id.select_name_inputlayout);
        selectTypeSpinner = findViewById(R.id.select_type_spinner);
        Button addEntryButton = findViewById(R.id.add_entry_button);
        chooseTimeTextView = findViewById(R.id.choose_time_textview);
        chooseDayTextView = findViewById(R.id.choose_day_textview);
        selectAmountEditText = findViewById(R.id.select_amount_edittext);
        selectAmountInputLayout = findViewById(R.id.select_amount_inputlayout);
        chosenDate = Calendar.getInstance();

        UserProfileViewModelFactory.getModel(getUid(), this).observe(this, new FirebaseObserver<FirebaseElement<User>>() {
            @Override
            public void onChanged(FirebaseElement<User> firebaseElement) {
                if (firebaseElement.hasNoError()) {
                    user = firebaseElement.getElement();
                    dateUpdated();
                }
            }
        });


        EnterTypeAdapter typeAdapter = new EnterTypeAdapter(this,
                R.layout.new_entry_type_spinner_row, Arrays.asList(
                new EnterTypeListViewModel("Chiqim", Color.parseColor("#ef5350"),
                        R.drawable.money_icon),
                new EnterTypeListViewModel("Kirim", Color.parseColor("#66bb6a"),
                        R.drawable.money_icon)));

        selectTypeSpinner.setAdapter(typeAdapter);

        updateDate();
        chooseDayTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickDate();
            }
        });
        chooseTimeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickTime();
            }
        });


        addEntryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    addToWallet(((selectTypeSpinner.getSelectedItemPosition() * 2) - 1) *
                                    CurrencyHelper.convertAmountStringToLong(selectAmountEditText.getText().toString()),
                            chosenDate.getTime(),
                            ((Category) selectCategorySpinner.getSelectedItem()).getCategoryID(),
                            selectNameEditText.getText().toString());
                } catch (StringException e) {
                    selectNameInputLayout.setError(e.getMessage());
                } catch (NolBalanceDifferenceException e) {
                    selectAmountInputLayout.setError(e.getMessage());
                }
            }
        });
    }

    private void dateUpdated() {
        if (user == null) return;

        final List<Category> categories = CategorysHelper.getCategories(user);
        EntryCategoryAdapter categoryAdapter = new EntryCategoryAdapter(this,
                R.layout.new_entry_type_spinner_row, categories);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectCategorySpinner.setAdapter(categoryAdapter);

        CurrencyHelper.setupAmountEditText(selectAmountEditText, user);

    }


    private void updateDate() {
        SimpleDateFormat dataFormatter = new SimpleDateFormat("yyyy-MM-dd");
        chooseDayTextView.setText(dataFormatter.format(chosenDate.getTime()));

        SimpleDateFormat dataFormatter2 = new SimpleDateFormat("HH:mm");
        chooseTimeTextView.setText(dataFormatter2.format(chosenDate.getTime()));
    }

    public void addToWallet(long balanceDifference, Date entryDate, String entryCategory, String entryName) throws NolBalanceDifferenceException, StringException {
        if (balanceDifference == 0) {
//            throw new ZeroBalanceDifferenceException("Balance difference should not be 0");
            throw new NolBalanceDifferenceException("0 dan yuqori bo'lishi shart");
        }

        if (entryName == null || entryName.length() == 0) {
            throw new StringException("Matn katta bolishi kerak > 0");
//            throw new EmptyStringException("Entry name length should be > 0");
        }

        FirebaseDatabase.getInstance().getReference().child("wallet-entries").child(getUid())
                .child("default").push().setValue(new WalletEnter(entryCategory, entryName, entryDate.getTime(), balanceDifference));
        user.wallet.sum += balanceDifference;
        UserProfileViewModelFactory.saveModel(getUid(), user);
        finishWithAnimation();
    }

    private void pickTime() {
        new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                chosenDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
                chosenDate.set(Calendar.MINUTE, minute);
                updateDate();

            }
        }, chosenDate.get(Calendar.HOUR_OF_DAY), chosenDate.get(Calendar.MINUTE), true).show();
    }

    private void pickDate() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        chosenDate.set(year, monthOfYear, dayOfMonth);
                        updateDate();

                    }
                }, year, month, day).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        onBackPressed();
        return true;
    }


}
