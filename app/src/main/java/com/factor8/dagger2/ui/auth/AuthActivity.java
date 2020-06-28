package com.factor8.dagger2.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.RequestManager;
import com.factor8.dagger2.R;
import com.factor8.dagger2.models.User;
import com.factor8.dagger2.ui.main.MainActivity;
import com.factor8.dagger2.viewmodels.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class AuthActivity extends DaggerAppCompatActivity implements View.OnClickListener {
    private AuthViewModel viewModel;
    private EditText userId;
    private ProgressBar progressBar;
    @Inject
    RequestManager requestManager;

    @Inject
    ViewModelProviderFactory providerFactory;

    private static final String TAG = "AuthActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        userId = findViewById(R.id.user_id_input);
        findViewById(R.id.login_button).setOnClickListener(this);
        progressBar = findViewById(R.id.progress_bar);
        viewModel = ViewModelProviders.of(this,providerFactory).get(AuthViewModel.class);
        setLogo();
        subscribeObservers();
    }
    private void setLogo(){
        requestManager
                .load("https://venturebeat.com/wp-content/uploads/2020/06/bigsur.jpg?resize=1024%2C575&strip=all")
                .into((ImageView)findViewById(R.id.login_logo));
    }

    private void subscribeObservers(){
                viewModel.observeAuthState().observe(this, new Observer<AuthResource<User>>() {
                    @Override
                    public void onChanged(AuthResource<User> userAuthResource) {
                        if(userAuthResource != null){
                             switch(userAuthResource.status){
                                 case LOADING: {
                                        showProgressBar(true);
                                     break;
                                 }
                                 case AUTHENTICATED: {
                                     showProgressBar(false);
                                     Log.d(TAG, "onChanged: LOGIN SUCCESS: " + userAuthResource.data.getEmail());
                                     onLoginSuccess();
                                     break;
                                 }
                                 case ERROR: {
                                     showProgressBar(false);
                                     Toast.makeText(AuthActivity.this, userAuthResource.message
                                             + "\nDid you enter a number between 1 and 10?", Toast.LENGTH_SHORT).show();
                                     break;
                                 }
                                 case NOT_AUTHENTICATED: {
                                     showProgressBar(false);
                                     break;
                                 }
                             }
                        }
                    }
                });
    }

    private void showProgressBar(boolean isVisible){
        if(isVisible){
            progressBar.setVisibility(View.VISIBLE);
        }else{
            progressBar.setVisibility(View.GONE);
        }
    }

    private void onLoginSuccess(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.login_button:{
                attemptLogin();
                break;
            }
        }
    }

    private void attemptLogin(){
        if (TextUtils.isEmpty(userId.getText().toString())) {
            return;
        }
        viewModel.authenticateWithId(Integer.parseInt(userId.getText().toString()));
    }
}