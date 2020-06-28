package com.factor8.dagger2.ui.main.profile;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.factor8.dagger2.SessionManager;
import com.factor8.dagger2.models.User;
import com.factor8.dagger2.ui.auth.AuthResource;

import javax.inject.Inject;

public class ProfileViewModel extends ViewModel {

    private static final String TAG = "ProfileViewModel";
    private final SessionManager sessionManager;
    
    @Inject
    public ProfileViewModel(SessionManager sessionManager){
        Log.d(TAG, "ProfileViewModel: ViewModel is Ready");
        this.sessionManager = sessionManager;
    }

    public LiveData<AuthResource<User>> getAuthenticatedUser(){
        return sessionManager.getAuthUser();
    }
}
