/*
 * *
 *  * Created by Yadgarov Islombek on 2021
 *  * Copyright (c).  All rights reserved.
 *  * Last modified 22.01.21 0:04
 *  بِسْمِ ٱللّٰهِ ٱلرَّحْمَٰنِ ٱلرَّحِيم  *
 *
 */

package com.aid.moneymanager.firebase.viewModels;


import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.aid.moneymanager.firebase.FirebaseElement;
import com.aid.moneymanager.firebase.FirebaseObserver;
import com.aid.moneymanager.firebase.FirebaseQueryLiveDataElement;
import com.aid.moneymanager.firebase.models.WalletEnter;
import com.google.firebase.database.FirebaseDatabase;


public class WalletEntryBaseViewModel extends ViewModel {
    protected final FirebaseQueryLiveDataElement<WalletEnter> liveData;
    protected final String uid;

    public WalletEntryBaseViewModel(String uid, String walletEntryId) {
        this.uid=uid;
        liveData = new FirebaseQueryLiveDataElement<>(WalletEnter.class, FirebaseDatabase.getInstance().getReference()
                .child("wallet-entries").child(uid).child("default").child(walletEntryId));    }

    public void observe(LifecycleOwner owner, FirebaseObserver<FirebaseElement<WalletEnter>> observer) {
        if(liveData.getValue() != null) observer.onChanged(liveData.getValue());
        liveData.observe(owner, new Observer<FirebaseElement<WalletEnter>>() {
            @Override
            public void onChanged(@Nullable FirebaseElement<WalletEnter> element) {
                if(element != null) observer.onChanged(element);
            }
        });
    }

    public void removeObserver(Observer<FirebaseElement<WalletEnter>> observer) {
        liveData.removeObserver(observer);
    }


}
