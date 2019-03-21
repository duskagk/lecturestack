package com.duskagk.lecturestack.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.duskagk.lecturestack.MainActivity;
import com.duskagk.lecturestack.Model.SubModel;
import com.duskagk.lecturestack.R;
import com.duskagk.lecturestack.add_subject;
import com.duskagk.lecturestack.login;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class Fhome extends Fragment {
    Button btn_login;
    Button btn_test;

    public Fhome() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f_home,container,false);

        btn_login=(Button)view.findViewById(R.id.f_home_btn);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), login.class);
                startActivity(intent);
            }
        });
        btn_test=(Button)view.findViewById(R.id.test);
        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), add_subject.class);
                startActivity(intent);
            }
        });


        return view;
    }

    class SubjectRecyclerView extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        List<SubModel> subModels;
        public SubjectRecyclerView() {
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
            return null;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }
        private class CustomViewHolder1 extends RecyclerView.ViewHolder{

            public CustomViewHolder1(View itemView) {
                super(itemView);
            }
        }
    }
}
