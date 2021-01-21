/*
 * *
 *  * Created by Yadgarov Islombek on 2021
 *  * Copyright (c).  All rights reserved.
 *  * Last modified 21.01.21 23:42
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
import com.aid.moneymanager.firebase.FirebaseQueryLiveDataSet;
import com.aid.moneymanager.firebase.ListDataSet;
import com.aid.moneymanager.firebase.models.WalletEnter;
import com.google.firebase.database.Query;



public class WalletEntriesBaseViewModel extends ViewModel {
    protected final FirebaseQueryLiveDataSet<WalletEnter> liveData;
    protected final String uid;

    public WalletEntriesBaseViewModel(String uid, Query query) {
        this.uid=uid;
        liveData = new FirebaseQueryLiveDataSet<>(WalletEnter.class, query);
    }

    public void observe(LifecycleOwner owner, FirebaseObserver<FirebaseElement<ListDataSet<WalletEnter>>> observer) {
        observer.onChanged(liveData.getValue());
        liveData.observe(owner, new Observer<FirebaseElement<ListDataSet<WalletEnter>>>() {
            @Override
            public void onChanged(@Nullable FirebaseElement<ListDataSet<WalletEnter>> element) {
                if(element != null) observer.onChanged(element);
            }
        });
    }

    public void removeObserver(Observer<FirebaseElement<ListDataSet<WalletEnter>>> observer) {
        liveData.removeObserver(observer);
    }


}
