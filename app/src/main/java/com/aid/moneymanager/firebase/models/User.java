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

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class User {
    public Currency currency = new Currency("Som", true, true);
    public UserSettings userSettings = new UserSettings();
    public Wallet wallet = new Wallet();
    public Map<String, WalletEntryCategory> customCategories = new HashMap<>();

    public User() {

    }
}