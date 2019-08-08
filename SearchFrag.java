package com.example.firstapp;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;


public class SearchFrag extends Fragment {
    AutoCompleteTextView actv;
    LinearLayout zipl;
    RadioButton rd;
    CheckBox checkbox;
    EditText kw;
    TextView mand1,mand2;
    RadioGroup rg;
    View v;
    String zipcode="";
    Spinner spinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_search, container, false);
        kw=v.findViewById(R.id.kw);
        mand1=v.findViewById(R.id.mand1);
        mand2=v.findViewById(R.id.mand2);
        spinner = (Spinner) v.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(),R.array.category_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        actv = (AutoCompleteTextView) v.findViewById(R.id.autoCompleteTextView);
        actv.setThreshold(3);
        actv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("text change","ENOUGH?: "+actv.enoughToFilter());
                String s1 = s.toString().trim();
                Log.d("onTextChanges",s1);
                if(s1.length()>=3){
                    Log.d("text change","length is: "+s1.length());
                    autoComp(s1);
                }
                else{
                    Log.d("text change","else part length is: "+s1.length());
                    actv.dismissDropDown();

                }

            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        zipl=(LinearLayout)v.findViewById(R.id.zip_layout);
        checkbox=(CheckBox)v.findViewById(R.id.encheckBox);
        checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1)
            {
                if (((CheckBox)v1).isChecked())
                {
                    zipl.setVisibility(View.VISIBLE);
                }
                else
                {
                    zipl.setVisibility(View.GONE);
                }
            }
        });


        rg=(RadioGroup) v.findViewById((R.id.radiogrp));
        rd =(RadioButton) rg.findViewById(R.id.cur_rad);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {

                if (checkedId==R.id.zip_rad)
                {
                    actv.setEnabled(true);
                    Log.d("onclick","actv=="+actv.isEnabled());
                }
                else
                {
                    actv.setEnabled(false);
                    Log.d("onclick","actv=="+actv.isEnabled());
                }
            }
        });
        Button button = (Button) v.findViewById(R.id.sr_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v1) {
                searchPr(v);
            }
        });
        Button clr = (Button) v.findViewById(R.id.cl_button);
        clr.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v1) {
                resetstuff(v);
            }
        });
        setstuff(v);
        return v;
    }

    public void autoComp(String s){
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        String url ="https://prodsearch-236607.appspot.com/ac?sw="+s;

        // Request a string response from the provided URL.
        Log.d("autoComp",url);
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.d("autoComp","Response is: "+ response);

                        try {
                            ArrayList<String> codes = new ArrayList<String>();
                            JSONObject jsonob = new JSONObject(response);
                            JSONArray pca = jsonob.getJSONArray("postalCodes");
//                            Log.d("autoComp","ParsedResponse is: "+ response);
                            for (int i=0; i<pca.length();i++) {
                                JSONObject jsonObject = pca.getJSONObject(i);
                                String pc = jsonObject.getString("postalCode");
                                codes.add(pc);
                                Log.d("autoComp","PostalCode is: "+ pc);

                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>
                                    (getActivity().getApplicationContext(), android.R.layout.select_dialog_item, codes);
                            actv.setAdapter(adapter);
                            actv.showDropDown();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("autoComp","That didn't work!");
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
    public void setstuff(View v){
        kw.setText("");
        actv.setText("");
        rg.clearCheck();
        Integer[] chids={R.id.ccheckBox,R.id.ccheckBox2,R.id.ccheckBox3,R.id.socheckBox,R.id.socheckBox2};
        for(int i=0;i<chids.length;i++){
            CheckBox cb=(CheckBox)v.findViewById(chids[i]);
            Log.d("setting","!"+v);
            cb.setChecked(false);
        }
        checkbox.setChecked(false);
        zipl.setVisibility(View.GONE);
        actv.setEnabled(false);
        rd.setChecked(true);
        mand1.setVisibility(View.GONE);
        mand2.setVisibility(View.GONE);
    }
    public void resetstuff(View v){
        kw.setText("");
        actv.setText("");
        checkbox.setChecked(false);
        zipl.setVisibility(View.GONE);
        actv.setEnabled(false);
        rd.setChecked(true);
        spinner.setSelection(0);
        mand1.setVisibility(View.GONE);
        mand2.setVisibility(View.GONE);
    }
    public boolean isnum(String st){
        for(int i=0;i<st.length();i++){
            if(st.charAt(i)<'0' || st.charAt(i)>'9'){

                return false;
            }
        }
        return true;
    }
    public void searchPr(View v){
        mand1.setVisibility(View.GONE);
        mand2.setVisibility(View.GONE);
        String key=(String) kw.getText().toString().trim();
        zipcode= actv.getText().toString().trim();
        int f=1;
        Toast toast=Toast.makeText(getActivity().getApplicationContext(),"Please fix all fields with errors",Toast.LENGTH_SHORT);

        if(key.length() ==0){
            mand1.setVisibility(View.VISIBLE);
            f=0;
            toast.show();
        }
        if(zipl.getVisibility()==View.VISIBLE)
        {
            if(!rd.isChecked()){
                if(zipcode.length()==0 || zipcode.length()>5){
                    mand2.setVisibility(View.VISIBLE);
                    f=0;
                    toast.show();
                }
                else if(!isnum(zipcode)){
                    mand2.setVisibility(View.VISIBLE);
                    f=0;
                    toast.show();
                }
            }
            else{

                Log.d("srch","in search with cur selected!!!");
                RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
                String url ="http://ip-api.com/json/";
                StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try{
                                    JSONObject jsonob = new JSONObject(response);
                                    Log.d("search","Cur zipCode is: "+ response);
                                    zipcode = jsonob.getString("zip");

                                }
                                catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("srch","That didn't work!");
                    }
                });
                queue.add(stringRequest);
            }
        }



        if(f==1){
            Intent myintent=new Intent();
            myintent.setClass(getActivity().getApplicationContext(),Results.class);
            String [] cat={"All","Art","Baby","Books","Clothing, Shoes & Accessories","Computers, Tablets & Networking","Health & Beauty","Music","Video Games & Consoles"};
            String [] catval={"all","550","2984","267","11450","58058","26395","11233","1249"};
            myintent.putExtra("keyword", key);
            String text = spinner.getSelectedItem().toString();
            int xin= Arrays.asList(cat).indexOf(text);
            myintent.putExtra("category", catval[xin]);

            if(zipcode.length()>0){
                myintent.putExtra("zipcode", zipcode);
            }
            CheckBox cc=(CheckBox)v.findViewById(R.id.ccheckBox);
            if(cc.isChecked()){
                myintent.putExtra("new", true);
            }
            cc=(CheckBox)v.findViewById(R.id.ccheckBox2);
            if(cc.isChecked()){
                myintent.putExtra("used", true);
            }
            cc=(CheckBox)v.findViewById(R.id.ccheckBox3);
            if(cc.isChecked()){
                myintent.putExtra("unspecified", true);
            }
            cc=(CheckBox)v.findViewById(R.id.socheckBox);
            if(cc.isChecked()){
                myintent.putExtra("local", true);
            }
            cc=(CheckBox)v.findViewById(R.id.socheckBox2);
            if(cc.isChecked()){
                myintent.putExtra("free", true);
            }
            EditText dst=(EditText) v.findViewById(R.id.dist);
            if(dst.getText().toString().length()>0){
                myintent.putExtra("dist", dst.getText().toString());
            }
            else{
                if(!checkbox.isChecked()){
                    myintent.putExtra("dist", "0");
                }
                else{
                    myintent.putExtra("dist", "10");
                }
            }

            startActivity(myintent);
        }

    }

}
