/*
 * *
 *  * Created by Yadgarov Islombek on 2021
 *  * Copyright (c).  All rights reserved.
 *  * Last modified 27.01.21 22:42
 *  بِسْمِ ٱللّٰهِ ٱلرَّحْمَٰنِ ٱلرَّحِيم  *
 *
 */

package com.aid.moneymanager.firebase.viewModel_fact;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.aid.moneymanager.firebase.models.User;
import com.aid.moneymanager.firebase.viewModels.UserProfileBaseViewModel;
import com.google.firebase.database.FirebaseDatabase;

public class UserProfileViewModelFactory implements ViewModelProvider.Factory {
    private String uid;

    private UserProfileViewModelFactory(String uid) {
        this.uid = uid;

    }

    public static void saveModel(String uid, User user) {
        FirebaseDatabase.getInstance().getReference()
                .child("users").child(uid).setValue(user);
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new UserProfileBaseViewModel(uid);
    }

    public static UserProfileBaseViewModel getModel(String uid, FragmentActivity activity) {
        return ViewModelProviders.of(activity, new UserProfileViewModelFactory(uid)).get(UserProfileBaseViewModel.class);
    }

}