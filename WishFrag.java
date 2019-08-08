package com.example.firstapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;


public class WishFrag extends Fragment {
    private static RecyclerView.Adapter adapter;
    private GridLayoutManager layoutManager;
    private static RecyclerView rv;
    View v;
    SharedPreferences prefs;
    SharedPreferences.OnSharedPreferenceChangeListener osl;
    ArrayList<ResultData> ar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_wish, container, false);
        ar = new ArrayList<ResultData>();
        rv = (RecyclerView) v.findViewById(R.id.rvwl);
        rv.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(), 2));
        Result_Adapter rad = new Result_Adapter(getActivity().getApplicationContext(), ar);
        rv.setAdapter(rad);
        osl=new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                setting_list();
            }
        };
        prefs = getActivity().getApplicationContext().getSharedPreferences("pn", MODE_PRIVATE);
        prefs.registerOnSharedPreferenceChangeListener(osl);
        setting_list();
        return v;
    }

    public void setting_list() {
        Map<String, ?> keys = prefs.getAll();
        float sum=0;
        ar.clear();
        for (Map.Entry<String, ?> entry : keys.entrySet()) {
            Log.d("map values", entry.getKey() + ": " +
                    entry.getValue().toString());
            Gson gson = new Gson();
            String js = entry.getValue().toString();
            ResultData rd = gson.fromJson(js, ResultData.class);
            sum+=Float.parseFloat(rd.getCost());
            ar.add(rd);
        }
        LinearLayout ll = (LinearLayout) v.findViewById(R.id.wlno);
        LinearLayout lltot = (LinearLayout) v.findViewById(R.id.wlbot);
        if (ar.size() > 0) {

            ll.setVisibility(View.GONE);
            Result_Adapter radi = new Result_Adapter(getActivity().getApplicationContext(), ar, "wish");
            rv.swapAdapter(radi, false);
            rv.setVisibility(View.VISIBLE);
            TextView tt=(TextView)v.findViewById(R.id.wltotal);
            String stx="Wishlist total("+ar.size()+" items):";
            tt.setText(stx);
            TextView tt2=(TextView)v.findViewById(R.id.wltotal2);
            String stx2="$"+sum;
            tt2.setText(stx2);
            lltot.setVisibility(View.VISIBLE);
        } else {

            rv.setVisibility(View.GONE);
            ll.setVisibility(View.VISIBLE);
            lltot.setVisibility(View.GONE);
        }

    }


    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            setting_list();
        }

    }


    @Override
    public void onResume() {
        super.onResume();
        setting_list();

    }
}
