/*
 * *
 *  * Created by Yadgarov Islombek on 2021
 *  * Copyright (c).  All rights reserved.
 *  * Last modified 19.01.21 22:02
 *  بِسْمِ ٱللّٰهِ ٱلرَّحْمَٰنِ ٱلرَّحِيم  *
 *
 */

package com.aid.moneymanager.firebase;

import com.google.firebase.database.DatabaseError;

public class FirebaseElement<T> {
    private T element;
    private DatabaseError databaseError;

    public FirebaseElement(T element) {
        this.element = element;
    }
    public FirebaseElement(DatabaseError databaseError) {
        this.databaseError = databaseError;
    }

    public T getElement() {
        return element;
    }

    public boolean hasNoError() {
        return element != null;
    }
}
