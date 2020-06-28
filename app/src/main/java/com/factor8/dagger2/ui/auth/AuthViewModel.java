package com.factor8.dagger2.ui.auth;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.ViewModel;

import com.factor8.dagger2.SessionManager;
import com.factor8.dagger2.models.User;
import com.factor8.dagger2.network.Auth.AuthApi;

import javax.inject.Inject;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class AuthViewModel extends ViewModel {
    private static final String TAG = "AuthViewModel";
    private final AuthApi authApi;
   private SessionManager sessionManager;

    @Inject
    public AuthViewModel(AuthApi authApi, SessionManager sessionManager) {
        Log.d(TAG, "AuthViewModel: View moel is woking");
        this.authApi = authApi;
        this.sessionManager = sessionManager;
        if(this.authApi != null)
            Log.d(TAG, "AuthViewModel: Auth API is not null");

    }

    public void authenticateWithId(int userId){
        Log.d(TAG, "authenticateWithId: Attempting to login");
        sessionManager.authenticateWithId(queryUserId(userId));
    }

    private LiveData<AuthResource<User>> queryUserId(int userId){
        return LiveDataReactiveStreams.fromPublisher(
                authApi.getUser(userId)
                        .onErrorReturn(new Function<Throwable, User>() {
                            @Override
                            public User apply(Throwable throwable) throws Exception {
                                User errorUser = new User();
                                errorUser.setId(-1);
                                return errorUser;
                            }
                        })
                        .map(new Function<User, AuthResource<User>>() {
                            @Override
                            public AuthResource<User> apply(User user) throws Exception {
                                if(user.getId() == -1){
                                    return AuthResource.error("Could not authenticate", (User)null);
                                }
                                return AuthResource.authenticated(user);
                            }
                        })
                        .subscribeOn(Schedulers.io())
        );
    }

    public LiveData<AuthResource<User>> observeAuthState(){//<--- WIll be Observing this from the activity/fragment
        return sessionManager.getAuthUser();
    }


}
