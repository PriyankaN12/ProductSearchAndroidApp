package com.example.firstapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class pageAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mFList=new ArrayList<>();
    private final List<String> mFtList =new ArrayList<>();
    private Bundle fragmentBundle;
    public pageAdapter(FragmentManager fm){
        super(fm);
    }
    public pageAdapter(FragmentManager fm, Bundle data){
        super(fm);
//        Log.d("pager adapeetr","--------------!!!!!!!!!!!!!!"+data);
        fragmentBundle=data;
    }

    public void addFrag(Fragment frag,String t){

        if(fragmentBundle !=null){

            frag.setArguments(fragmentBundle);
            Log.d("pageadapter","frr--------------++++++++++"+frag);
        }
        mFList.add(frag);
        mFtList.add(t);
    }
    @Nullable
    @Override
    public CharSequence getPageTitle(int pos) {
        return mFtList.get(pos);
    }

    @Override
    public Fragment getItem(int i) {



        return mFList.get(i);

    }

    @Override
    public int getCount() {
        return mFList.size();
    }
}
