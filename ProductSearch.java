package com.example.firstapp;

import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

public class ProductSearch extends AppCompatActivity {

    private pageAdapter mPageAdapter;
    private ViewPager mViewPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_search);

        mPageAdapter=new pageAdapter(getSupportFragmentManager());
        mViewPage=(ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPage);

        TabLayout tabLayout=(TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPage);
    }

    private void setupViewPager(ViewPager viewPager){
        pageAdapter adapter=new pageAdapter(getSupportFragmentManager());
        adapter.addFrag(new SearchFrag(),"Search");
        adapter.addFrag(new WishFrag(),"Wish List");
        viewPager.setAdapter(adapter);
    }
}
