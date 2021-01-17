/*
 * *
 *  * Created by Yadgarov Islombek on 2021
 *  * Copyright (c).  All rights reserved.
 *  * Last modified 17.01.21 22:56
 *  بِسْمِ ٱللّٰهِ ٱلرَّحْمَٰنِ ٱلرَّحِيم  *
 *
 */

package com.aid.moneymanager.firebase.models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class WalletEntryCategory {
    public String htmlColorCode;
    public String visibleName;

    public WalletEntryCategory() {

    }

    public WalletEntryCategory(String visibleName, String htmlColorCode) {
        this.htmlColorCode = htmlColorCode;
        this.visibleName = visibleName;
    }

}