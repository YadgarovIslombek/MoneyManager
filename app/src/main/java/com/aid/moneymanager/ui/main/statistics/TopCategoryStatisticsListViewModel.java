/*
 * *
 *  * Created by Yadgarov Islombek on 2021
 *  * Copyright (c).  All rights reserved.
 *  * Last modified 22.01.21 0:13
 *  بِسْمِ ٱللّٰهِ ٱلرَّحْمَٰنِ ٱلرَّحِيم  *
 *
 */

package com.aid.moneymanager.ui.main.statistics;


import com.aid.moneymanager.firebase.models.Currency;
import com.aid.moneymanager.models.Category;

public class TopCategoryStatisticsListViewModel {
    private final float percentage;
    private long money;
    private final Currency currency;
    private final Category category;
    private String categoryName;

    public TopCategoryStatisticsListViewModel(Category category, String categoryName, Currency currency, long money, float percentage) {
        this.category = category;
        this.categoryName = categoryName;
        this.currency = currency;
        this.money = money;
        this.percentage = percentage;

    }

    public String getCategoryName() {
        return categoryName;
    }

    public Currency getCurrency() {
        return currency;
    }

    public long getMoney() {
        return money;
    }

    public Category getCategory() {
        return category;
    }

    public float getPercentage() {
        return percentage;
    }
}
