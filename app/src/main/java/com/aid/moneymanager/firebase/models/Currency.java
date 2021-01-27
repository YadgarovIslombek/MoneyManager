/*
 * *
 *  * Created by Yadgarov Islombek on 2021
 *  * Copyright (c).  All rights reserved.
 *  * Last modified 28.01.21 0:21
 *  بِسْمِ ٱللّٰهِ ٱلرَّحْمَٰنِ ٱلرَّحِيم  *
 *
 */

package com.aid.moneymanager.firebase.models;

import com.google.firebase.database.IgnoreExtraProperties;


@IgnoreExtraProperties
public class Currency {
    public String symbol;
    public boolean left;
    public boolean space;

    public Currency() {

    }

    public Currency(String symbol, boolean left, boolean space) {
        this.symbol = symbol;
        this.left = left;
        this.space = space;
    }
}
