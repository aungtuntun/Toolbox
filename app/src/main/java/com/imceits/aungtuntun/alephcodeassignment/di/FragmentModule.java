package com.imceits.aungtuntun.alephcodeassignment.di;

import com.imceits.aungtuntun.alephcodeassignment.ui.DetailFragment;
import com.imceits.aungtuntun.alephcodeassignment.ui.FriendFragment;
import com.imceits.aungtuntun.alephcodeassignment.ui.ToolFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract ToolFragment contributeToolFragment();

    @ContributesAndroidInjector
    abstract FriendFragment contributeFriendFragment();

    @ContributesAndroidInjector
    abstract DetailFragment contributeDetailFragment();
}
