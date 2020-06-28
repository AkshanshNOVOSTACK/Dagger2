package com.factor8.dagger2.di.main;

import com.factor8.dagger2.ui.main.posts.PostsFragment;
import com.factor8.dagger2.ui.main.profile.ProfileFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainFragmentBuilderModule {

    @ContributesAndroidInjector
    abstract ProfileFragment contributeProfileFragment();

    @ContributesAndroidInjector
    abstract PostsFragment contributePostsFragment();

}
