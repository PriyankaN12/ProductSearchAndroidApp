package com.example.firstapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


public class Photos extends Fragment {
    private static RecyclerView.Adapter adapter;
    private LinearLayoutManager layoutManager;
    private static RecyclerView rv;
    String tle;
    View v;
    Bundle dd;
    ArrayList<String> ph;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v= inflater.inflate(R.layout.fragment_photos, container, false);
        dd = this.getArguments();
        rv=(RecyclerView) v.findViewById(R.id.rvph);
        rv.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        photoAdapter rad=new photoAdapter(getActivity().getApplicationContext(),ph);
        rv.setAdapter(rad);
        int i=0;
        final LinearLayout prg = (LinearLayout)v.findViewById(R.id.progph);
        if (dd != null) {
            tle = dd.getString("title");
            String query=tle;
            try {
                Log.d("urlencode","before========"+query);
                query=query.replaceAll("[^A-Za-z0-9 ]", "");
                Log.d("urlencode","mid========"+query);
                query = URLEncoder.encode(query,"ASCII");
                Log.d("urlencode",query);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            String url = "https://prodsearch-236607.appspot.com/findprodphotos?title=" + query;


            RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
            Log.d("phototab url: ", url);
            StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("phototabdetails", "Response is: " + response);
                        try{
                            JSONObject jso= new JSONObject(response);

                            if(jso.has("items")){
                                ph=new ArrayList<String>();
                                JSONArray ja1=jso.getJSONArray("items");
                                for(int i=0;i<ja1.length();i++){
                                    JSONObject jot=ja1.getJSONObject(i);
                                    if(jot.has("link")){
                                        ph.add(jot.getString("link"));
                                    }
                                }
                                if(ph.size()>0){
                                    Log.d("phototab","@@@############@@@@@@@"+ph);
                                    photoAdapter radi=new photoAdapter(getActivity().getApplicationContext(),ph);
                                    rv.swapAdapter(radi,false);
                                    rv.setVisibility(View.VISIBLE);
                                }
                                else{
                                    LinearLayout l0=(LinearLayout) v.findViewById(R.id.phno);
                                    l0.setVisibility(View.VISIBLE);
                                }


                            }
                            else{
                                LinearLayout l0=(LinearLayout) v.findViewById(R.id.phno);
                                l0.setVisibility(View.VISIBLE);
                            }
                        }catch (Exception e) {
                            e.printStackTrace();
                            LinearLayout l0=(LinearLayout) v.findViewById(R.id.phno);
                            l0.setVisibility(View.VISIBLE);
                            prg.setVisibility(View.GONE);
                        }
                        prg.setVisibility(View.GONE);

                    }
                }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("phototab", "That didn't work!");
                    LinearLayout l0=(LinearLayout) v.findViewById(R.id.phno);
                    l0.setVisibility(View.VISIBLE);
                    prg.setVisibility(View.GONE);
                }
            });

            queue.add(stringRequest);
        }
        else{
            LinearLayout l0=(LinearLayout) v.findViewById(R.id.phno);
            l0.setVisibility(View.VISIBLE);
            prg.setVisibility(View.GONE);
        }


        return v;

    }




}
