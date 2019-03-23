package com.duskagk.lecturestack;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.duskagk.lecturestack.Model.SubModel;
import com.duskagk.lecturestack.View.Fhome;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Select_subject extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_subject);



    }

    class SelectSubjectRcView extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        List<SubModel> subModels;
        public SelectSubjectRcView() {
            subModels=new ArrayList<>();
            FirebaseDatabase.getInstance().getReference().child("subject").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    subModels.clear();
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        SubModel subModel=snapshot.getValue(SubModel.class);
                        subModels.add(snapshot.getValue(SubModel.class));
                    }
                    notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.c_mysubject,parent,false);
            return new CustomViewHolder2(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            Glide.with(holder.itemView.getContext()).load(subModels.get(position).bookImg).into(((CustomViewHolder2)holder).bookImg);

            ((CustomViewHolder2)holder).subName.setText(subModels.get(position).subName);
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
