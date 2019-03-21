package com.duskagk.lecturestack;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.duskagk.lecturestack.View.Fcloud;
import com.duskagk.lecturestack.View.Fhome;
import com.duskagk.lecturestack.View.Fstudy;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout=(TabLayout)findViewById(R.id.main_tab);
        pager=(ViewPager)findViewById(R.id.main_viewpager);
        ViewAdapter vadt=new ViewAdapter(getSupportFragmentManager());;
        vadt.AddFragment(new Fcloud(),"클라우드");
        vadt.AddFragment(new Fhome(),"홈");
        vadt.AddFragment(new Fstudy(),"스터디라인");
        pager.setAdapter(vadt);
        tabLayout.setupWithViewPager(pager);

    }

    private class ViewAdapter extends FragmentPagerAdapter {
        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> FragmentListTitles= new ArrayList<>();
//        private Context mContext;

        public ViewAdapter(FragmentManager fm) {
            super(fm);
//            mContext = context;

        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return FragmentListTitles.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return FragmentListTitles.get(position);
        }
        public void AddFragment(Fragment fragment,String Title){
            fragmentList.add(fragment);
            FragmentListTitles.add(Title);
        }
    }
}
