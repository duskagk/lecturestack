package com.duskagk.lecturestack.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.duskagk.lecturestack.MainActivity;
import com.duskagk.lecturestack.Model.SubModel;
import com.duskagk.lecturestack.R;
import com.duskagk.lecturestack.add_subject;
import com.duskagk.lecturestack.login;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class Fhome extends Fragment {
    Button btn_login;
    Button btn_test;
    private LinearLayoutManager mLayoutManager;


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
        RecyclerView rcview=(RecyclerView)view.findViewById(R.id.f_home_rcview);
        mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rcview.setLayoutManager(mLayoutManager);
        rcview.setAdapter(new SubjectRecyclerView());
        TextView user=(TextView)view.findViewById(R.id.f_home_user);

        user.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail().toString());


        return view;
    }

    class SubjectRecyclerView extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        List<SubModel> subModels;
        public SubjectRecyclerView() {
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
            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.c_mysubject,parent,false);
            return new CustomViewHolder1(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            Glide.with(holder.itemView.getContext()).load(subModels.get(position).bookImg).into(((CustomViewHolder1)holder).bookImg);

            ((CustomViewHolder1)holder).subName.setText(subModels.get(position).subName);
        }

        @Override
        public int getItemCount() {
            return subModels.size();
        }
        private class CustomViewHolder1 extends RecyclerView.ViewHolder{
            public ImageView bookImg;
            public TextView subName;
            public CustomViewHolder1(View itemView) {
                super(itemView);
                bookImg=(ImageView)itemView.findViewById(R.id.book_img);
                subName=(TextView)itemView.findViewById(R.id.subName);
            }
        }
    }
}
