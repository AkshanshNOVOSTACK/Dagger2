package com.factor8.dagger2.di.Auth;

import com.factor8.dagger2.network.Auth.AuthApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class AuthModule {

    @AuthScope
    @Provides
    static AuthApi provideAuthApi(Retrofit retrofit){
         return retrofit.create(AuthApi.class);
    }
}
