package com.instigatemobile.imessenger.controllers;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.instigatemobile.imessenger.models.Profile;


public class DataBase {
    private DatabaseReference database;
    private static DataBase DB;
    ProfileCallbackInterface callback;

    private DataBase() {
        database = FirebaseDatabase.getInstance().getReference("users");
    }

    public static DataBase initDataBase() {
        if (DB == null) {
            DB = new DataBase();
        }
        return  DB;
    }

    public boolean insertProfile(Profile profile) {
        //String userId = database.push().getKey();
        //database.child(userId).setValue(profile);

        FirebaseUser user =  FirebaseAuth.getInstance().getCurrentUser();
        database.child(user.getUid()).setValue(profile);

        return true;
    }

    public void changeProfileAvatar(String url) {
        FirebaseUser user =  FirebaseAuth.getInstance().getCurrentUser();
        database.child(user.getUid()).child("avatar").setValue(url);
    }

    public void changeProfileBackground(String url) {
        FirebaseUser user =  FirebaseAuth.getInstance().getCurrentUser();
        database.child(user.getUid()).child("background").setValue(url);
    }

    public void getCurrentProfile(ProfileCallbackInterface profileCallback) {
        this.callback = profileCallback;
        FirebaseUser auth =  FirebaseAuth.getInstance().getCurrentUser();
        database.child(auth.getUid()).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
                        Profile profile = dataSnapshot.getValue(Profile.class);
                        System.out.println(profile.getAvatar());
                        callback.responseProfile(profile);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Profile profile = new Profile();
                        callback.responseProfile(profile);
                        // [START_EXCLUDE]
                        //setEditingEnabled(true);
                        // [END_EXCLUDE]
                    }
                });

    }

}