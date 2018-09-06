package com.example.saksham.chatapp;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class ChatActivity extends Application {

    @Override
    public void onCreate(){
        super.onCreate();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

    }



}
