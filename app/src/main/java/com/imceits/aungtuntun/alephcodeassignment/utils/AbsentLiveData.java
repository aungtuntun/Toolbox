package com.imceits.aungtuntun.alephcodeassignment.utils;

import androidx.lifecycle.LiveData;

public class AbsentLiveData extends LiveData {
    @SuppressWarnings("unchecked")
    private AbsentLiveData() {
        postValue(null);
    }

    @SuppressWarnings("unchecked")
    public static <T> LiveData<T> create() {
        return new AbsentLiveData();
    }
}
