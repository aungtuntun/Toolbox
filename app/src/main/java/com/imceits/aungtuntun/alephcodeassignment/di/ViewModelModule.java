package com.imceits.aungtuntun.alephcodeassignment.di;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.imceits.aungtuntun.alephcodeassignment.ui.DetailViewModel;
import com.imceits.aungtuntun.alephcodeassignment.ui.FriendViewModel;
import com.imceits.aungtuntun.alephcodeassignment.ui.ToolViewModel;

import dagger.Binds;
import dagger.MapKey;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
abstract class ViewModelModule {

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ToolViewModelFactory estateViewModelFactory);

    @Binds
    abstract Context bindContext(Application application);

    @Binds
    @IntoMap
    @ViewModelKey(ToolViewModel.class)
    abstract ViewModel bindToolViewModel(ToolViewModel toolViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(FriendViewModel.class)
    abstract ViewModel bindFriendViewModel(FriendViewModel friendViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(DetailViewModel.class)
    abstract ViewModel bindDetailViewModel(DetailViewModel detailViewModel);

}
