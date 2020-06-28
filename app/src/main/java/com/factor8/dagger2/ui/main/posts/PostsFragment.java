package com.factor8.dagger2.ui.main.posts;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.factor8.dagger2.R;
import com.factor8.dagger2.models.Post;
import com.factor8.dagger2.ui.main.Resource;
import com.factor8.dagger2.util.VerticalSpacingItemDecoration;
import com.factor8.dagger2.viewmodels.ViewModelProviderFactory;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class PostsFragment extends DaggerFragment {
    private static final String TAG = "PostsFragment";
    private PostsViewModel viewModel;
    private RecyclerView recyclerView;

    @Inject
    ViewModelProviderFactory providerFactory;
    
    @Inject
    PostsRecyclerAdapter adapter;
    @Inject
    LinearLayoutManager linearLayoutManager;

    @Inject
    VerticalSpacingItemDecoration verticalSpacingItemDecoration;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_posts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.recycler_view);
        viewModel = ViewModelProviders.of(this,providerFactory).get(PostsViewModel.class);
        initRecyclerView();
        subscribeObservers();
    }

    private void subscribeObservers(){
            viewModel.observePosts().removeObservers(getViewLifecycleOwner());
            viewModel.observePosts().observe(getViewLifecycleOwner(), new Observer<Resource<List<Post>>>() {
                @Override
                public void onChanged(Resource<List<Post>> listResource) {
                    if(listResource!=null){
                        switch (listResource.status){

                            case LOADING:{
                                Log.d(TAG, "onChanged: LOADING...");
                                break;
                            }

                            case SUCCESS:{
                                Log.d(TAG, "onChanged: got posts...");
                                adapter.setPosts(listResource.data);
                                break;
                            }

                            case ERROR:{
                                Log.e(TAG, "onChanged: ERROR..." + listResource.message );
                                break;
                            }
                        }
                    }
                }
            });
    }
    private void initRecyclerView(){
        recyclerView.setLayoutManager(linearLayoutManager);
//        VerticalSpacingItemDecoration itemDecoration = new VerticalSpacingItemDecoration(15);
        recyclerView.addItemDecoration(verticalSpacingItemDecoration);
        recyclerView.setAdapter(adapter);
    }
}