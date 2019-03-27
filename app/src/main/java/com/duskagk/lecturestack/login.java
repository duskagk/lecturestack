package com.duskagk.lecturestack;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.duskagk.lecturestack.Control.MyApp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

public class login extends AppCompatActivity {
    private Button login_btn;
    private Button signup_btn;
    private EditText email;
    private EditText pass;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseRemoteConfig firebaseRemoteConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final MyApp myApp = (MyApp)getApplication();
        email=findViewById(R.id.login_edt_id);
        pass=findViewById(R.id.login_edt_pass);
        signup_btn=(Button)findViewById(R.id.login_btn_signup);
        login_btn=(Button)findViewById(R.id.login_btn_login);

        firebaseRemoteConfig= FirebaseRemoteConfig.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseAuth.signOut();
        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(login.this,signup.class);
                startActivity(intent);
                finish();
            }
        });
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginEvent();
            }
        });
        authStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user=firebaseAuth.getCurrentUser();
                if(user!=null){
                    Intent intent = new Intent(login.this, MainActivity.class);
                    startActivity(intent);
                    login.this.finish();
                }
            }
        };



    }
    void loginEvent() {
        firebaseAuth.getInstance().signInWithEmailAndPassword(email.getText().toString(), pass.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(login.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }else{
                            MyApp myApp=(MyApp)getApplication();
                            myApp.setLogin(true);
                        }

                    }
                });
    }
//    @Override
//    protected void onStart() {
//        super.onStart();
//        firebaseAuth.addAuthStateListener(authStateListener);
//    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(authStateListener);
    }
}
