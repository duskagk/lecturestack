package com.duskagk.lecturestack;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.duskagk.lecturestack.Model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class signup extends AppCompatActivity {
    private Button signup_btn;
    private EditText id_edt;
    private EditText pass_edt;
    private EditText pass2_edt;
    private EditText nick_edt;
    private ImageView check_img;
    private ImageView profile_img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signup_btn=(Button)findViewById(R.id.signup_btn_sign);
        id_edt=findViewById(R.id.signup_edt_id);
        pass_edt=findViewById(R.id.signup_edt_pass1);
        pass2_edt=findViewById(R.id.signup_edt_pass2);
        nick_edt=findViewById(R.id.signup_edt_nickname);
//        check_img=findViewById(R.id.signup_img_check);


        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final FirebaseFirestore db=FirebaseFirestore.getInstance();
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(id_edt.getText().toString(),pass2_edt.getText().toString())
                        .addOnCompleteListener(signup.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful())
                                {
                                    UserModel userModel=new UserModel();
                                    String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
                                    userModel.uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
                                    userModel.userName=nick_edt.getText().toString();
                                    userModel.userID=id_edt.getText().toString();
                                    db.getInstance().collection("users").document(userModel.uid).set(userModel)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    Intent intent = new Intent(signup.this,MainActivity.class);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                            });

//                                    FirebaseDatabase.getInstance().getReference().child("users").child(uid).setValue(userModel);


                                }
                                else{
                                    Toast.makeText(signup.this,"실패",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });




    }
}
