package com.factor8.dagger2.di.main;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.factor8.dagger2.network.main.MainApi;
import com.factor8.dagger2.ui.main.MainActivity;
import com.factor8.dagger2.ui.main.posts.PostsFragment;
import com.factor8.dagger2.ui.main.posts.PostsRecyclerAdapter;
import com.factor8.dagger2.util.VerticalSpacingItemDecoration;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class MainModule {
    @MainScope
    @Provides
    static MainApi provideMainApi(Retrofit retrofit){
         return retrofit.create(MainApi.class);
    }

    @MainScope
    @Provides
    static PostsRecyclerAdapter providerAdapter(){
        return new PostsRecyclerAdapter();
    }

    @MainScope
    @Provides
    static LinearLayoutManager providerLinearlayoutManager(MainActivity mainActivity){
        return new LinearLayoutManager(mainActivity, RecyclerView.VERTICAL, false);
    }

    @MainScope
    @Provides
    static VerticalSpacingItemDecoration providerVerticalSpacingItemDecoration(){
       return  new VerticalSpacingItemDecoration(15);
    }

}
