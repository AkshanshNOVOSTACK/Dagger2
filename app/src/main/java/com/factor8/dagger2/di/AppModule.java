package com.factor8.dagger2.di;

import android.app.Application;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.factor8.dagger2.R;
import com.factor8.dagger2.models.User;
import com.factor8.dagger2.util.Constants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


@Module
public class AppModule {

    @Singleton
    @Provides
    static RequestOptions provideRequestOptions(){
        return RequestOptions. placeholderOf(R.drawable.ic_launcher_foreground).error(R.drawable.ic_launcher_background);
    }
    @Singleton
    @Provides
    static RequestManager provideGlideInstance(Application application, RequestOptions requestOptions){
         return Glide.with(application).setDefaultRequestOptions(requestOptions);
    }

    @Singleton
    @Provides
    static Retrofit provideRetrofitInstance(){
         return new Retrofit.Builder()
                 .baseUrl(Constants.BASE_URL)
                 .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                 .addConverterFactory(GsonConverterFactory.create())
                 .build();
    }



}
