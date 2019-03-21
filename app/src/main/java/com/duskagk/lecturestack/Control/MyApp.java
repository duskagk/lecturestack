package com.duskagk.lecturestack.Control;

import android.app.Application;

public class MyApp extends Application {
    private boolean mGlobalLogin = false;


    public boolean isLogin(){
        return mGlobalLogin;
    }
    public void setLogin(boolean mGlobalLogin) {
        this.mGlobalLogin = mGlobalLogin;
    }
}
