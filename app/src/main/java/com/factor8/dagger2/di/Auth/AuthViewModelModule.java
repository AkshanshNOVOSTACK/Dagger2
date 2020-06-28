package com.factor8.dagger2.di.Auth;

import androidx.lifecycle.ViewModel;

import com.factor8.dagger2.di.ViewModelKey;
import com.factor8.dagger2.ui.auth.AuthViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;


@Module
public abstract class AuthViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel.class)
    public abstract ViewModel bindAuthViewModel(AuthViewModel viewModel);

}
