/*
 * *
 *  * Created by Yadgarov Islombek on 2021
 *  * Copyright (c).  All rights reserved.
 *  * Last modified 21.01.21 1:14
 *  بِسْمِ ٱللّٰهِ ٱلرَّحْمَٰنِ ٱلرَّحِيم  *
 *
 */

package com.aid.moneymanager.firebase.models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Curency {
    public String symbol;
    public boolean left;
    public boolean space;

    public Curency() {

    }

    public Curency(String symbol, boolean left, boolean space) {
        this.symbol = symbol;
        this.left=left;
        this.space=space;
    }
}
