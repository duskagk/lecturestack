package com.duskagk.lecturestack;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.duskagk.lecturestack.Model.SubModel;
import com.duskagk.lecturestack.Model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Select_subject extends AppCompatActivity {
    private LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_subject);


        RecyclerView rcview=(RecyclerView)findViewById(R.id.select_subject_rcview);
        mLayoutManager = new LinearLayoutManager(this);

        rcview.setLayoutManager(mLayoutManager);
        rcview.setAdapter(new SelectSubjectRcView());

    }

    class SelectSubjectRcView extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        List<SubModel> subModels;
        public SelectSubjectRcView() {
            subModels=new ArrayList<>();
            FirebaseFirestore.getInstance().collection("subject").get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                for(QueryDocumentSnapshot item : task.getResult()){
                                    SubModel subModel=item.toObject(SubModel.class);
                                    subModels.add(item.toObject(SubModel.class));
                                }
                                notifyDataSetChanged();
                            }
                        }
                    });

//            FirebaseDatabase.getInstance().getReference().child("subject").addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    subModels.clear();
//                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
//                        SubModel subModel=snapshot.getValue(SubModel.class);
//                        subModels.add(snapshot.getValue(SubModel.class));
//                    }
//                    notifyDataSetChanged();
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//            });
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.c_mysubject,parent,false);
            return new CustomViewHolder2(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
            Glide.with(holder.itemView.getContext()).load(subModels.get(position).bookImg).into(((CustomViewHolder2)holder).bookImg);
            ((CustomViewHolder2)holder).subName.setText(subModels.get(position).subName);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    FirebaseFirestore.getInstance().collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .collection("MySubject").document(subModels.get(position).subName).set(subModels.get(position));

//                    FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("MySubject").child(subModels.get(position).subName)
//                            .setValue(subModels.get(position));
                }
            });
        }

        @Override
        public int getItemCount() {
            return subModels.size();
        }
        private class CustomViewHolder2 extends RecyclerView.ViewHolder{
            public ImageView bookImg;
            public TextView subName;
            public CustomViewHolder2(View itemView) {
                super(itemView);
                bookImg=(ImageView)itemView.findViewById(R.id.book_img);
                subName=(TextView)itemView.findViewById(R.id.subName);
            }
        }
    }
}
