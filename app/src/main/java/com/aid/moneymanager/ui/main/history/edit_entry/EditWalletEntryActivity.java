/*
 * *
 *  * Created by Yadgarov Islombek on 2021
 *  * Copyright (c).  All rights reserved.
 *  * Last modified 25.01.21 23:39
 *  بِسْمِ ٱللّٰهِ ٱلرَّحْمَٰنِ ٱلرَّحِيم  *
 *
 */

package com.aid.moneymanager.ui.main.history.edit_entry;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AlertDialog;

import com.aid.moneymanager.R;
import com.aid.moneymanager.base.BaseActivity;
import com.aid.moneymanager.exceptions.NolBalanceDifferenceException;
import com.aid.moneymanager.exceptions.StringException;
import com.aid.moneymanager.firebase.FirebaseElement;
import com.aid.moneymanager.firebase.FirebaseObserver;
import com.aid.moneymanager.firebase.models.User;
import com.aid.moneymanager.firebase.models.WalletEnter;
import com.aid.moneymanager.firebase.viewModel_fact.UserProfileViewModelFactory;
import com.aid.moneymanager.firebase.viewModel_fact.WalletEntryViewModelFactory;
import com.aid.moneymanager.models.Category;
import com.aid.moneymanager.ui.add_kiritish.EnterTypeAdapter;
import com.aid.moneymanager.ui.add_kiritish.EnterTypeListViewModel;
import com.aid.moneymanager.ui.add_kiritish.EntryCategoryAdapter;
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



public class EditWalletEntryActivity extends BaseActivity {

    private Spinner selectCategorySpinner;
    private TextInputEditText selectNameEditText;
    private Calendar choosedDate;
    private TextInputEditText selectAmountEditText;
    private TextView chooseDayTextView;
    private TextView chooseTimeTextView;
    private Spinner selectTypeSpinner;
    private User user;
    private WalletEnter walletEntry;
    private Button removeEntryButton;
    private Button editEntryButton;
    private String walletId;
    private TextInputLayout selectAmountInputLayout;
    private TextInputLayout selectNameInputLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_wallet_entry);
        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("O'zgartitish");
//        getSupportActionBar().setTitle("Edit wallet entry");

        walletId = getIntent().getExtras().getString("wallet-entry-id");

        selectCategorySpinner = findViewById(R.id.select_category_spinner);
        selectNameEditText = findViewById(R.id.select_name_edittext);
        selectNameInputLayout = findViewById(R.id.select_name_inputlayout);
        selectTypeSpinner = findViewById(R.id.select_type_spinner);
        editEntryButton = findViewById(R.id.edit_entry_button);
        removeEntryButton = findViewById(R.id.remove_entry_button);
        chooseTimeTextView = findViewById(R.id.choose_time_textview);
        chooseDayTextView = findViewById(R.id.choose_day_textview);
        selectAmountEditText = findViewById(R.id.select_amount_edittext);
        selectAmountInputLayout = findViewById(R.id.select_amount_inputlayout);

        choosedDate = Calendar.getInstance();

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



        editEntryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    editWalletEntry(((selectTypeSpinner.getSelectedItemPosition() * 2) - 1) *
                                    CurrencyHelper.convertAmountStringToLong(selectAmountEditText.getText().toString()),
                            choosedDate.getTime(),
                            ((Category) selectCategorySpinner.getSelectedItem()).getCategoryID(),
                            selectNameEditText.getText().toString());
                }  catch (StringException e) {
                    selectNameInputLayout.setError(e.getMessage());
                } catch (NolBalanceDifferenceException e) {
                    selectAmountInputLayout.setError(e.getMessage());
                }
            }
        });

        removeEntryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRemoveWalletEntryDialog();
            }

            public void showRemoveWalletEntryDialog() {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditWalletEntryActivity.this);
                builder.setMessage("O'chirmoqchimisiz?").setPositiveButton("Ha", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        removeWalletEntry();
                    }
                }).setNegativeButton("Yo'q", null).show();

            }
        });



        UserProfileViewModelFactory.getModel(getUid(), this).observe(this, new FirebaseObserver<FirebaseElement<User>>() {
            @Override
            public void onChanged(FirebaseElement<User> firebaseElement) {
                if (firebaseElement.hasNoError()) {
                    user = firebaseElement.getElement();
                    dataUpdated();
                }
            }
        });


        WalletEntryViewModelFactory.getModel(getUid(), walletId, this).observe(this, new FirebaseObserver<FirebaseElement<WalletEnter>>() {
            @Override
            public void onChanged(FirebaseElement<WalletEnter> firebaseElement) {
                if (firebaseElement.hasNoError()) {
                    walletEntry = firebaseElement.getElement();
                    dataUpdated();
                }
            }
        });


    }

    public void dataUpdated() {
        if (walletEntry == null || user == null) return;

        final List<Category> categories = CategorysHelper.getCategories(user);
        EntryCategoryAdapter categoryAdapter = new EntryCategoryAdapter(this,
                R.layout.new_entry_type_spinner_row, categories);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectCategorySpinner.setAdapter(categoryAdapter);

        CurrencyHelper.setupAmountEditText(selectAmountEditText, user);
        choosedDate.setTimeInMillis(-walletEntry.timestamp);
        updateDate();
        selectNameEditText.setText(walletEntry.name);


        selectTypeSpinner.post(new Runnable() {
            @Override
            public void run() {
                if (walletEntry.balanceDifference < 0) selectTypeSpinner.setSelection(0);
                else selectTypeSpinner.setSelection(1);            }
        });

        selectCategorySpinner.post(new Runnable() {
            @Override
            public void run() {
                EntryCategoryAdapter adapter = (EntryCategoryAdapter) selectCategorySpinner.getAdapter();
                selectCategorySpinner.setSelection(adapter.getItemIndex(walletEntry.categoryID));
            }
        });


        long amount = Math.abs(walletEntry.balanceDifference);
        String current = CurrencyHelper.formatCurrency(user.currency, amount);
        selectAmountEditText.setText(current);
        selectAmountEditText.setSelection(current.length() -
                (user.currency.left ? 0 : (user.currency.symbol.length() + (user.currency.space ? 1 : 0))));

    }


    private void updateDate() {
        SimpleDateFormat dataFormatter = new SimpleDateFormat("yyyy-MM-dd");
        chooseDayTextView.setText(dataFormatter.format(choosedDate.getTime()));

        SimpleDateFormat dataFormatter2 = new SimpleDateFormat("HH:mm");
        chooseTimeTextView.setText(dataFormatter2.format(choosedDate.getTime()));
    }

    public void editWalletEntry(long balanceDifference, Date entryDate, String entryCategory, String entryName) throws StringException, NolBalanceDifferenceException {
        if (balanceDifference == 0) {
            throw new NolBalanceDifferenceException("Minimal summadan kam");
        }

//        if (entryName == null || entryName.length() == 0) {
//            throw new StringException("Matn kiritilmagan");
//        }

        long finalBalanceDifference = balanceDifference - walletEntry.balanceDifference;
        user.wallet.sum += finalBalanceDifference;
        UserProfileViewModelFactory.saveModel(getUid(), user);

        FirebaseDatabase.getInstance().getReference().child("wallet-entries").child(getUid())
                .child("default").child(walletId).setValue(new WalletEnter(entryCategory, entryName, entryDate.getTime(), balanceDifference));
        finish();
    }

    public void removeWalletEntry() {
        user.wallet.sum -= walletEntry.balanceDifference;
        UserProfileViewModelFactory.saveModel(getUid(), user);

        FirebaseDatabase.getInstance().getReference().child("wallet-entries").child(getUid())
                .child("default").child(walletId).removeValue();
        finish();
    }


    private void pickTime() {
        new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                choosedDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
                choosedDate.set(Calendar.MINUTE, minute);
                updateDate();

            }
        }, choosedDate.get(Calendar.HOUR_OF_DAY), choosedDate.get(Calendar.MINUTE), true).show();
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
                        choosedDate.set(year, monthOfYear, dayOfMonth);
                        updateDate();

                    }
                }, year, month, day).show();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        onBackPressed();
        return true;
    }

}
