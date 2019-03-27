package com.duskagk.lecturestack;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.duskagk.lecturestack.Model.SubModel;
import com.duskagk.lecturestack.Model.UserModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

public class add_subject extends AppCompatActivity {

    private static final int PICK_FROM_ALBUM = 10;
    ImageView bookimg;
    private Uri imgUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject);

        Button btnPush=(Button)findViewById(R.id.subjectPush);
        Button imgbtn=(Button)findViewById(R.id.subjectImage);
        bookimg=(ImageView)findViewById(R.id.addbook);
        final EditText subName=(EditText)findViewById(R.id.subjectBookName);
        imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent,PICK_FROM_ALBUM);
            }
        });
        btnPush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sub=subName.getText().toString();
                FirebaseStorage.getInstance().getReference().child("bookImg").child(sub).putFile(imgUri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                FirebaseStorage.getInstance().getReference().child("bookImg").child(subName.getText().toString()).getDownloadUrl()
                                        .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                String url=uri.toString();
                                                SubModel subModel=new SubModel();
                                                subModel.bookImg=url;
                                                subModel.subName=subName.getText().toString();
                                                FirebaseFirestore.getInstance().collection("subject").document(subModel.subName)
                                                        .set(subModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        finish();
                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {

                                                    }
                                                });
//                                                FirebaseDatabase.getInstance().getReference().child("subject").child(subName.getText().toString()).setValue(subModel);


                                            }
                                        });
                            }
                        });
            }
        });



    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==PICK_FROM_ALBUM&& resultCode==RESULT_OK){
            bookimg.setImageURI(data.getData());
            imgUri=data.getData();
        }
    }

}
