/*
 * *
 *  * Created by Yadgarov Islombek on 2021
 *  * Copyright (c).  All rights reserved.
 *  * Last modified 17.01.21 23:00
 *  بِسْمِ ٱللّٰهِ ٱلرَّحْمَٰنِ ٱلرَّحِيم  *
 *
 */

package com.aid.moneymanager.firebase.models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class UserSettings {
    public static final int HOME_COUNTER_KIRIM_CHIQIM = 0;
    public static final int HOME_COUNTER_TYPE_SHOW_LIMIT = 1;

    public static final int HOME_COUNTER_OYLIK = 0;
    public static final int HOME_COUNTER_HAFTALIK = 1;


    public int dayMonthStart = 0;
    public int dayWeekStart = 0;
    public long limit;
    public int homeCounter = UserSettings.HOME_COUNTER_KIRIM_CHIQIM;
    public int homeCounter2 = UserSettings.HOME_COUNTER_OYLIK;

    public UserSettings() {

    }

}
