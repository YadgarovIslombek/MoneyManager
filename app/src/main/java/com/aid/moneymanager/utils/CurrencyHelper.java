/*
 * *
 *  * Created by Yadgarov Islombek on 2021
 *  * Copyright (c).  All rights reserved.
 *  * Last modified 24.01.21 22:54
 *  بِسْمِ ٱللّٰهِ ٱلرَّحْمَٰنِ ٱلرَّحِيم  *
 *
 */

package com.aid.moneymanager.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.aid.moneymanager.firebase.models.Currency;
import com.aid.moneymanager.firebase.models.User;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

public class CurrencyHelper {
    public static String formatCurrency(Currency curency, long money) {
        long absMoney = Math.abs(money);
        NumberFormat numberFormat = NumberFormat.getInstance();
//     DecimalFormat decimalFormat1 = null;
//        if (decimalFormat1 == null) {
//            decimalFormat1 = new DecimalFormat("###,###,###.0");
//            decimalFormat1.setGroupingSize(3);
//            decimalFormat1.setMinimumFractionDigits(0);
//
//            DecimalFormatSymbols s = new DecimalFormatSymbols();
//            s.setGroupingSeparator(' ');
//            DecimalFormatSymbols symbols = decimalFormat1.getDecimalFormatSymbols();
//            s.setDecimalSeparator(symbols.getDecimalSeparator());
//            decimalFormat1.setDecimalFormatSymbols(s);
//
//        }
//        decimalFormat1.setMinimumFractionDigits(showDecimal ? 1 : 0);
//        decimalFormat1.setMaximumFractionDigits(showDecimal ? 1 : 0);
        //                (money < 0 ? "-" : "") + (absMoney / 100) + "." +
//                (absMoney % 100 < 10 ? "0" : "") +
//                (absMoney % 100)+
//                decimalFormat1.format(money)+
        return (curency.left ? (curency.symbol + (curency.space ? " " : "")) : "") +
                numberFormat.format(money) +
                //                (money < 0 ? "-" : "") + (absMoney / 1000.0) + "." +
//                (absMoney % 100 < 10 ? "00" : "") +
//                (absMoney % 100)  +
                (curency.left ? "" : ((curency.space ? " " : "") + curency.symbol));

    }

    public static void setupAmountEditText(EditText editText, User user) {
        editText.setText(CurrencyHelper.formatCurrency(user.currency, 0), TextView.BufferType.EDITABLE);
        editText.addTextChangedListener(new TextWatcher() {
            private String current = "";

            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }


            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (!charSequence.toString().equals(current)) {
                    editText.removeTextChangedListener(this);
                    current = CurrencyHelper.formatCurrency(user.currency, convertAmountStringToLong(charSequence));
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
        if (cleanString.length() == 0) {
            return 0;
        } else {
            return Long.parseLong(cleanString);
        }

    }
}