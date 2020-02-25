package com.imceits.aungtuntun.alephcodeassignment.di;

import android.annotation.SuppressLint;
import android.app.Application;

import com.imceits.aungtuntun.alephcodeassignment.ToolsApp;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton
@Component(modules = {AppModule.class, ActivityModule.class, AndroidInjectionModule.class})
public interface AppComponent {

    @SuppressLint("all")
    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);
        AppComponent build();
    }

    void inject(ToolsApp app);
}
