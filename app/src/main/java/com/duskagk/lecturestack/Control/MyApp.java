package com.duskagk.lecturestack.Control;

import android.app.Application;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class MyApp extends Application {
    private boolean mGlobalLogin = false;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    DatabaseReference mdatbase=FirebaseDatabase.getInstance().getReference();

    public boolean isLogin(){
        return mGlobalLogin;
    }
    public void setLogin(boolean mGlobalLogin) {
        this.mGlobalLogin = mGlobalLogin;
    }
    public void setExam(){

    }
}
