/*
 * *
 *  * Created by Yadgarov Islombek on 2021
 *  * Copyright (c).  All rights reserved.
 *  * Last modified 20.01.21 0:00
 *  بِسْمِ ٱللّٰهِ ٱلرَّحْمَٰنِ ٱلرَّحِيم  *
 *
 */

package com.aid.moneymanager.firebase.models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class WalletEnter {

    public String categoryID;
    public String name;
    public long timestamp;
    public long balanceDifference;
    public WalletEnter() {

    }

    public WalletEnter(String categoryID, String name, long timestamp, long balanceDifference) {
        this.categoryID = categoryID;
        this.name = name;
        this.timestamp = -timestamp;
        this.balanceDifference = balanceDifference;
    }

}