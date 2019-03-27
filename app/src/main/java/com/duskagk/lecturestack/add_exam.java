package com.duskagk.lecturestack;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.duskagk.lecturestack.Model.ExamModel;
import com.duskagk.lecturestack.Model.SubModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class add_exam extends AppCompatActivity {

    Spinner add_subname;
    List<SubModel> subModels;
    ArrayList<String> subarr;
    ArrayAdapter<String> arrayAdapter;
    Button examPush;
    EditText addQuestion;
    EditText answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exam);

        examPush=(Button)findViewById(R.id.examPush);
        addQuestion=(EditText)findViewById(R.id.add_exam);
        answer=(EditText)findViewById(R.id.examAnswer);
        final ExamModel examModel=new ExamModel();

        examPush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                examModel.question=addQuestion.getText().toString();
                examModel.answer.put(answer.getText().toString(),true);
                examModel.answer.put("오답",false);
                examModel.answer.put("ㄴㅇㄹ나ㅣㅇㄹ",false);
                examModel.uid= FirebaseAuth.getInstance().getCurrentUser().getUid();

                FirebaseFirestore.getInstance().collection("subject").document("opengl")
                        .collection("exam").add(examModel);

//                FirebaseDatabase.getInstance().getReference().child("subject").child("opengl").child("exam").child("1-1").push().setValue(examModel);

            }
        });

    }
}



//
//
//        add_subname=(Spinner)findViewById(R.id.examSubject);
//                subarr=new ArrayList<>();
//        FirebaseDatabase.getInstance().getReference().child("subject")
//        .addValueEventListener(new ValueEventListener() {
//@Override
//public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//        for(DataSnapshot snapshot : dataSnapshot.getChildren()){
//        subarr.add(snapshot.getKey());
//        }
//
//        }
//
//@Override
//public void onCancelled(@NonNull DatabaseError databaseError) {
//
//        }
//        });
//        ArrayAdapter<String> arrayAdapter;
//        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, subarr);
//        add_subname.setAdapter(arrayAdapter);
