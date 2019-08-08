package com.example.firstapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
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
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Details_tab extends AppCompatActivity {

    private pageAdapter mPageAdapter;
    private ViewPager mViewPage;
    public Bundle data;
    SharedPreferences mPrefs;
    String itid;
    String obj;
    Intent ine;
    String tt;
    String et;
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_tab);
        mPageAdapter=new pageAdapter(getSupportFragmentManager());
        mViewPage=(ViewPager) findViewById(R.id.containerdet);
        setupViewPager(mViewPage);
        ine=getIntent();
        itid=ine.getStringExtra("itemid");
        obj=ine.getStringExtra("item");
        tt=ine.getStringExtra("title");
        et=ine.getStringExtra("etitle");
        mPrefs = getSharedPreferences("pn",MODE_PRIVATE);
        fab=findViewById(R.id.fab);
        Toolbar tb=(Toolbar) findViewById(R.id.toolbardet);
        ImageView fb=(ImageView) findViewById(R.id.fb);
        tb.setTitle(tt);
        tb.setNavigationIcon(R.drawable.arrow);
        tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tlu=tt;
                try {
                    tlu= URLEncoder.encode(tt,"UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                String url="https://www.facebook.com/dialog/share?app_id=1183569605158609&display=popup&href="+ine.getStringExtra("fburl");
                url+="&quote=Buy "+tlu+"&hashtag=%23CSCI571Spring2019Ebay";

                    Log.d("simiadapter","siiiiiimmmmmmm!!!!!!!!!!!!!"+url);
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);


            }
        });
        if(mPrefs.contains(itid)){
            fab.setImageResource(R.drawable.remove_cart);
        }
        else{
            fab.setImageResource(R.drawable.add_cart);
        }
        fab.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                SharedPreferences.Editor prefsEditor = mPrefs.edit();
                if(!mPrefs.contains(itid)) {
                    Gson gson = new Gson();

                    prefsEditor.putString(itid, obj);
                    prefsEditor.commit();
                    fab.setImageResource(R.drawable.remove_cart);

                    Toast.makeText(getApplicationContext(),et+" was added to wishlist",Toast.LENGTH_LONG).show();
                }
                else{
                    prefsEditor.remove(itid);
                    prefsEditor.commit();
                    fab.setImageResource(R.drawable.add_cart);
                    Toast.makeText(getApplicationContext(),et+" was removed from wishlist",Toast.LENGTH_LONG).show();
                }
            }
        });
        Log.d("details tab","--------------------------"+data.toString());
        TabLayout tabLayout=(TabLayout) findViewById(R.id.tabsdet);
        tabLayout.setupWithViewPager(mViewPage);
        setupTabIcons(tabLayout);
    }
    private void setupTabIcons(TabLayout tabLayout) {
        final TabLayout tbl=tabLayout;
        tabLayout.getTabAt(0).setIcon(R.drawable.producttab);
        tabLayout.getTabAt(1).setIcon(R.drawable.shiptab);
        tabLayout.getTabAt(2).setIcon(R.drawable.phototab);
        tabLayout.getTabAt(3).setIcon(R.drawable.simitab);
        tabLayout.getTabAt(0).getIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(1).getIcon().setColorFilter(getResources().getColor(R.color.dull), PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(2).getIcon().setColorFilter(getResources().getColor(R.color.dull), PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(3).getIcon().setColorFilter(getResources().getColor(R.color.dull), PorterDuff.Mode.SRC_IN);


        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(Color.WHITE,PorterDuff.Mode.SRC_IN);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //for removing the color of first icon when switched to next tab
                tbl.getTabAt(0).getIcon().setColorFilter(getResources().getColor(R.color.dull), PorterDuff.Mode.SRC_IN);
                //for other tabs
                tab.getIcon().setColorFilter(getResources().getColor(R.color.dull), PorterDuff.Mode.SRC_IN);

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    private void setupViewPager(ViewPager viewPager){
        Intent ine=getIntent();
        data = new Bundle();
        data.putString("id",ine.getStringExtra("itemid"));
        data.putString("sc",ine.getStringExtra("shipcost"));
        data.putString("title",ine.getStringExtra("title"));
        pageAdapter adapter=new pageAdapter(getSupportFragmentManager(),data);
        ProdDetFrag pdf=new ProdDetFrag();
        pdf.setArguments(data);
        adapter.addFrag(pdf,"Product");
        adapter.addFrag(new Shipping_tab(),"Shipping");
        adapter.addFrag(new Photos(),"Photos");
        adapter.addFrag(new Similiar(),"Similiar");
        viewPager.setAdapter(adapter);
    }
}
