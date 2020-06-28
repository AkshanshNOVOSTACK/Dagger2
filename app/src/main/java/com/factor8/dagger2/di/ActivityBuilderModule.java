package com.factor8.dagger2.di;

import android.app.Application;

import com.factor8.dagger2.di.Auth.AuthModule;
import com.factor8.dagger2.di.Auth.AuthScope;
import com.factor8.dagger2.di.Auth.AuthViewModelModule;
import com.factor8.dagger2.di.main.MainFragmentBuilderModule;
import com.factor8.dagger2.di.main.MainModule;
import com.factor8.dagger2.di.main.MainScope;
import com.factor8.dagger2.di.main.MainViewModelsModule;
import com.factor8.dagger2.ui.auth.AuthActivity;
import com.factor8.dagger2.ui.main.MainActivity;

import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilderModule {

        @AuthScope
        @ContributesAndroidInjector(
                modules = {
                        AuthViewModelModule.class
                        , AuthModule.class
                }
        )
         abstract AuthActivity contributeAuthActivity();

        @MainScope
    @ContributesAndroidInjector(
            modules = {
                    MainFragmentBuilderModule.class,
                    MainViewModelsModule.class,
                    MainModule.class
            }
    )
    abstract MainActivity contributeMainActivity();

        @Provides
        static boolean getApp(Application application){
            return application == null;
        }
}
