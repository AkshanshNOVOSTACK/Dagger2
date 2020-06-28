package com.factor8.dagger2.ui.main.profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.factor8.dagger2.R;
import com.factor8.dagger2.di.ViewModelFactoryModule;
import com.factor8.dagger2.models.User;
import com.factor8.dagger2.ui.auth.AuthResource;
import com.factor8.dagger2.viewmodels.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


public class ProfileFragment extends DaggerFragment {
    private static final String TAG = "ProfileFragment";

    @Inject
    ViewModelProviderFactory providerFactory;
    private ProfileViewModel viewModel;
    private TextView email, username, website;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Toast.makeText(getActivity(), "Profile Fragment Injected", Toast.LENGTH_SHORT).show();
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        email = view.findViewById(R.id.email);
        username = view.findViewById(R.id.username);
        website = view.findViewById(R.id.website);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onViewCreated: Profile Fragment was created");
        viewModel = ViewModelProviders.of(this,providerFactory).get(ProfileViewModel.class);
        subscribeObserversMethod();

    }

    private void subscribeObserversMethod(){
        viewModel.getAuthenticatedUser().observe(getViewLifecycleOwner(), new Observer<AuthResource<User>>() {
            @Override
            public void onChanged(AuthResource<User> userAuthResource) {
                if(userAuthResource != null){
                    switch(userAuthResource.status){
                        case AUTHENTICATED: {
                              setUserDetails(userAuthResource.data);
                            break;
                        }
                        case ERROR: {
                            setUserErrors(userAuthResource.message);
                            break;
                        }

                    }
                }
            }
        });
    }

    private void setUserErrors(String message) {
        email.setText(message);

    }

    private void setUserDetails(User data) {
        email.setText(data.getEmail());
        username.setText(data.getUsername());
        website.setText(data.getWebsite());
    }
}