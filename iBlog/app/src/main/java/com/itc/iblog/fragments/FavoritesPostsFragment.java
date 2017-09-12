package com.itc.iblog.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;

import android.support.design.widget.FloatingActionButton;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import com.google.firebase.storage.UploadTask;
import com.itc.iblog.activities.MainActivity;
import com.itc.iblog.R;
import com.itc.iblog.adapters.ListAdapter;
import com.itc.iblog.models.PostModel;

import java.io.IOException;
import java.util.ArrayList;

import java.util.Date;

import java.util.List;


public class FavoritesPostsFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Activity main;
    private List<PostModel> myDataset;
    private String postId;
    private Bitmap bitmap;
    private Uri file;
    private int IMAGE;
    private DatabaseReference ref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_posts, container, false);
        final FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        myDataset = new ArrayList<>();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        ref = database.getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                myDataset = new ArrayList<>();
                if (dataSnapshot.hasChild("favoritesPosts")) {
                    for (DataSnapshot messageSnapshot : dataSnapshot.child("favoritesPosts").getChildren()) {
                        final String postId = messageSnapshot.getValue(new GenericTypeIndicator<String>() {});
                        System.out.println("bla " + postId);
                        DatabaseReference postRef = database.getReference("Posts").child(postId);
                                postRef.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        PostModel post = dataSnapshot.getValue(PostModel.class);
                                        System.out.println("bla " + dataSnapshot.getValue());
                                       // myDataset.add(post);
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                    }
                }
                mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
                mRecyclerView.setHasFixedSize(true);
                mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(RecyclerView mRecyclerView, int dx, int dy) {
                        if (dy > 0 || dy<0 && fab != null && fab.isShown()) {
                            fab.hide();
                        }
                    }

                    @Override
                    public void onScrollStateChanged(RecyclerView mRecyclerView, int newState) {
                        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                            fab.show();
                        }
                        super.onScrollStateChanged(mRecyclerView, newState);
                    }
                });


                // use a linear layout manager
                mLayoutManager = new LinearLayoutManager(getActivity());
                mRecyclerView.setLayoutManager(mLayoutManager);

                // specify an adapter (see also next example)
                mAdapter = new ListAdapter(myDataset, (MainActivity)getActivity());
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        return view;
    }
}