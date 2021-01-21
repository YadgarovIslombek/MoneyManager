/*
 * *
 *  * Created by Yadgarov Islombek on 2021
 *  * Copyright (c).  All rights reserved.
 *  * Last modified 21.01.21 23:26
 *  بِسْمِ ٱللّٰهِ ٱلرَّحْمَٰنِ ٱلرَّحِيم  *
 *
 */

package com.aid.moneymanager.utils;

import  android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.aid.moneymanager.firebase.models.Currency;
import com.aid.moneymanager.firebase.models.User;

public class CurrencyHelper {
    public static String formatCurrency(Currency curency, long money) {
        long absMoney = Math.abs(money);
        return (curency.left ? (curency.symbol + (curency.space ? " " : "")): "") +
                (money < 0 ? "-" : "") + (absMoney / 100) + "." +
                (absMoney % 100 < 10 ? "0" : "") +
                (absMoney % 100)  +
                (curency.left ? "" : ((curency.space ? " " : "") + curency.symbol));
    }
    public static void setupAmountEditText(EditText editText, User user) {
        editText.setText(CurrencyHelper.formatCurrency(user.currency,0), TextView.BufferType.EDITABLE);
        editText.addTextChangedListener(new TextWatcher() {
            private String current = "";
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }


            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (!charSequence.toString().equals(current)) {
                    editText.removeTextChangedListener(this);
                    current = CurrencyHelper.formatCurrency(user.currency,convertAmountStringToLong(charSequence));
                    editText.setText(current);
                    editText.setSelection(current.length() -
                            (user.currency.left ? 0 : (user.currency.symbol.length() + (user.currency.space ? 1 : 0))));

                    editText.addTextChangedListener(this);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    public static long convertAmountStringToLong(CharSequence s) {
        String cleanString = s.toString().replaceAll("[^0-9]", "");
        return Long.valueOf(cleanString);
    }
}
