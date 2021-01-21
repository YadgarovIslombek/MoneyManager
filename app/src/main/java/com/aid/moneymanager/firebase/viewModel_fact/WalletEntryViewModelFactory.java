/*
 * *
 *  * Created by Yadgarov Islombek on 2021
 *  * Copyright (c).  All rights reserved.
 *  * Last modified 22.01.21 0:04
 *  بِسْمِ ٱللّٰهِ ٱلرَّحْمَٰنِ ٱلرَّحِيم  *
 *
 */

package com.aid.moneymanager.firebase.viewModel_fact;


import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.aid.moneymanager.firebase.viewModels.WalletEntryBaseViewModel;


public class WalletEntryViewModelFactory implements ViewModelProvider.Factory {
    private final String entryId;
    private final String uid;

    private WalletEntryViewModelFactory(String uid, String entryId) {
        this.uid = uid;
        this.entryId = entryId;

    }
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new WalletEntryBaseViewModel(uid, entryId);
    }

    public static WalletEntryBaseViewModel getModel(String uid, String entryId, FragmentActivity activity) {
        return ViewModelProviders.of(activity, new WalletEntryViewModelFactory(uid, entryId)).get(WalletEntryBaseViewModel.class);
    }


}