package com.imceits.aungtuntun.alephcodeassignment.utils;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class AutoClearedValue<T> {

    private T value;

    public AutoClearedValue(Fragment fragment, T value) {
        this.value = value;
        FragmentManager fragmentManager = fragment.getFragmentManager();
        if (fragmentManager != null) {
            fragmentManager.registerFragmentLifecycleCallbacks(new FragmentManager.FragmentLifecycleCallbacks() {
                @Override
                public void onFragmentViewDestroyed(@NonNull FragmentManager fm, @NonNull Fragment f) {
                    if (f == fragment) {
                        AutoClearedValue.this.value = null;
                        fragmentManager.unregisterFragmentLifecycleCallbacks(this);
                    }
                }
            }, false);
        }
    }

    public T get() {
        return value;
    }
}
