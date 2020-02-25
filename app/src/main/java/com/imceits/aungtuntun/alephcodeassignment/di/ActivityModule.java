package com.imceits.aungtuntun.alephcodeassignment.di;


import com.imceits.aungtuntun.alephcodeassignment.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector(modules = {FragmentModule.class})
    abstract MainActivity contributeMainActivity();

}
