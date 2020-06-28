package com.factor8.dagger2.di;

import androidx.lifecycle.ViewModelProvider;

import com.factor8.dagger2.viewmodels.ViewModelProviderFactory;

import dagger.Binds;
import dagger.BindsInstance;
import dagger.Module;

@Module
public abstract class ViewModelFactoryModule {

    @Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelProviderFactory modelProviderFactory);


}
