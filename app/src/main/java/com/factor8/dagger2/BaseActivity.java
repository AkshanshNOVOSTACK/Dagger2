package com.factor8.dagger2;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.factor8.dagger2.models.User;
import com.factor8.dagger2.ui.auth.AuthActivity;
import com.factor8.dagger2.ui.auth.AuthResource;

import java.util.concurrent.BlockingDeque;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public abstract class BaseActivity extends DaggerAppCompatActivity {
    private static final String TAG = "BaseActivity";

    @Inject
    public SessionManager sessionManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        subscribeObservers();
    }

    private void subscribeObservers(){
        sessionManager.getAuthUser().observe(this, new Observer<AuthResource<User>>() {
            @Override
            public void onChanged(AuthResource<User> userAuthResource) {
                Log.d(TAG, "onChanged: ");
                if(userAuthResource != null){
                    switch(userAuthResource.status){
                        case LOADING: {

                            break;
                        }
                        case AUTHENTICATED: {
                            Log.d(TAG, "onChanged: LOGIN SUCCESS: " + userAuthResource.data.getEmail());
                            break;
                        }
                        case ERROR: {

                            break;
                        }
                        case NOT_AUTHENTICATED: {
                            Toast.makeText(BaseActivity.this, "Not Authenticated", Toast.LENGTH_SHORT).show();
                                navLoginScreen();
                            break;
                        }
                    }
                }else{
                    Log.d(TAG, "onChanged: AuthResource<USer>: is null");
                }
            }
        });
    }


    private void navLoginScreen(){
        Intent i = new Intent(this, AuthActivity.class);
        startActivity(i);
        finish();
    }
}
