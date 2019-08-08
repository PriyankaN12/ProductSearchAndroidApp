package com.example.firstapp;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;


public class Similiar extends Fragment {
    private static RecyclerView.Adapter adapter;
    private LinearLayoutManager layoutManager;
    private static RecyclerView rv;
    String id;
    View v;
    Bundle dd;
    ArrayList<SimData> sim;
    ArrayList<SimData> def;
    Spinner col,ord;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v= inflater.inflate(R.layout.fragment_similiar, container, false);
        dd = this.getArguments();
        rv=(RecyclerView) v.findViewById(R.id.rvsim);
        rv.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        simiAdapter rad=new simiAdapter(getActivity().getApplicationContext(),sim);
        rv.setAdapter(rad);
        col = (Spinner) v.findViewById(R.id.simcol);
        ord= (Spinner) v.findViewById(R.id.simord);
        swtw();
        String [] tc={"Default","Name","Price","Days"};
        ArrayList<String> cc=new ArrayList<String>(Arrays.asList(tc));
        String[] tor={"Ascending","Descending"};
        ArrayList<String> or=new ArrayList<String>(Arrays.asList(tor));
        col.setAdapter(new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,cc));
        ord.setAdapter(new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,or));
        final LinearLayout prg = (LinearLayout)v.findViewById(R.id.progsim);
        if (dd != null) {
            id = dd.getString("id");
            String url = "https://prodsearch-236607.appspot.com/findsim?id=" + id;
            RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
            Log.d("Similartab url: ", url);
            StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("Similartabdetails", "Response is: " + response);
                            try{
                                JSONObject jso= new JSONObject(response);

                                if(jso.has("getSimilarItemsResponse") && jso.getJSONObject("getSimilarItemsResponse").has("itemRecommendations")) {
                                    JSONObject job = jso.getJSONObject("getSimilarItemsResponse").getJSONObject("itemRecommendations");
                                    if (job.has("item")) {
                                        sim=new ArrayList<SimData>();
                                        String id = "N/A",title="N/A",img="N/A",days="N/A",sc="N/A",c="N/A",vurl="N/A",prd="",dad="";
                                        JSONArray ja1=job.getJSONArray("item");
                                        for(int i=0;i<ja1.length();i++){
                                            JSONObject jot=ja1.getJSONObject(i);
                                            if(jot.has("title")){
                                                title=jot.getString("title");
                                            }
                                            if(jot.has("viewItemURL")){
                                                vurl=jot.getString("viewItemURL");
                                            }
                                            if(jot.has("imageURL")){
                                                img=jot.getString("imageURL");
                                            }
                                            if(jot.has("shippingCost")){
                                                JSONObject jo1=jot.getJSONObject("shippingCost");
                                                if(jo1.has("__value__")){
                                                    String tmp=jo1.getString("__value__");
                                                    if(Math.round(Float.parseFloat(tmp))==0){
                                                        sc="Free Shipping";
                                                    }
                                                    else{
                                                        sc="$"+tmp;
                                                    }
                                                }
                                            }
                                            if(jot.has("timeLeft")){
                                                String tmp=jot.getString("timeLeft");
                                                int i1=tmp.indexOf('P');
                                                int i2=tmp.indexOf('D');
                                                String tmp1=tmp.substring(i1+1,i2);
                                                dad=tmp1;
                                                if(Integer.parseInt(tmp1)<=1){
                                                    days=tmp1+" Day Left";
                                                }
                                                else{
                                                    days=tmp1+"Days Left";
                                                }
                                            }
                                            if(jot.has("buyItNowPrice")){
                                                JSONObject jo1=jot.getJSONObject("buyItNowPrice");
                                                if(jo1.has("__value__")){
                                                    String tmp=jo1.getString("__value__");
                                                    prd=tmp;
                                                    c="$"+tmp;
                                                }
                                            }

                                            SimData sd=new SimData(title,vurl,c,sc,days,img);
                                            sd.setDad(dad);
                                            sd.setPrd(prd);
                                            Log.d("simitab","sd created"+sd.toString());
                                            sim.add(sd);

                                        }
                                        if(sim.size()>0){
                                            LinearLayout lk=(LinearLayout)v.findViewById(R.id.sim);
                                            lk.setVisibility(View.VISIBLE);
                                            Log.d("simitab","!!!!@@@############@@@@@@@!!!!!!!"+sim.toString());
                                            def=(ArrayList<SimData>) sim.clone();
                                            col.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                @Override
                                                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                                                    changeOrder(col.getSelectedItem().toString(),ord.getSelectedItem().toString());
                                                }

                                                @Override
                                                public void onNothingSelected(AdapterView<?> parentView) {
                                                    // your code here
                                                }

                                            });
                                            ord.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                @Override
                                                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                                                    changeOrder(col.getSelectedItem().toString(),ord.getSelectedItem().toString());
                                                }

                                                @Override
                                                public void onNothingSelected(AdapterView<?> parentView) {
                                                    // your code here
                                                }

                                            });
                                            simiAdapter radi=new simiAdapter(getActivity().getApplicationContext(),sim);
                                            rv.swapAdapter(radi,false);
                                            rv.setVisibility(View.VISIBLE);
                                        }
                                        else{
                                            LinearLayout l0=(LinearLayout) v.findViewById(R.id.simno);
                                            l0.setVisibility(View.VISIBLE);
                                            prg.setVisibility(View.GONE);
                                        }
//
//

                                    }
                                    else{
                                        LinearLayout l0=(LinearLayout) v.findViewById(R.id.simno);
                                        l0.setVisibility(View.VISIBLE);
                                        prg.setVisibility(View.GONE);
                                    }

                                }
                                else{
                                    LinearLayout l0=(LinearLayout) v.findViewById(R.id.simno);
                                    l0.setVisibility(View.VISIBLE);
                                    prg.setVisibility(View.GONE);
                                }
                            }catch (Exception e) {
                                e.printStackTrace();
                                LinearLayout l0=(LinearLayout) v.findViewById(R.id.simno);
                                l0.setVisibility(View.VISIBLE);
                                prg.setVisibility(View.GONE);

                            }
                            prg.setVisibility(View.GONE);


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("Similartab", "That didn't work!");
                    LinearLayout l0=(LinearLayout) v.findViewById(R.id.simno);
                    l0.setVisibility(View.VISIBLE);
                    prg.setVisibility(View.GONE);
                }
            });

            queue.add(stringRequest);
        }
        else{
            LinearLayout l0=(LinearLayout) v.findViewById(R.id.simno);
            l0.setVisibility(View.VISIBLE);
            prg.setVisibility(View.GONE);
        }

        return v;

    }
    public void changeOrder(String f,String o){
        if(f=="Default"){

            simiAdapter radi=new simiAdapter(getActivity().getApplicationContext(),def);
            rv.swapAdapter(radi,false);
        }
        else{
            sim=order(sim,f,o);
            simiAdapter radi=new simiAdapter(getActivity().getApplicationContext(),sim);
            rv.swapAdapter(radi,false);
        }
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void swtw(){
        col.setMinimumWidth(350);
        ord.setMinimumWidth(300);
    }
    public ArrayList<SimData> order(ArrayList<SimData> roles,String field,String ord) {
        final String fd=field;
        final String ordt=ord;
        Collections.sort(roles, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {

                if(fd.equals("Name")) {
                    String x1 = (String)((SimData) o1).getTitle();
                    String x2 = (String)((SimData) o2).getTitle();
                    if(ordt.equals("Ascending")){
                        return x1.compareTo(x2);
                    }
                    else{
                        return x2.compareTo(x1);
                    }
                }
                else if(fd.equals("Price")) {
                    Float x1 = (float)Float.parseFloat(((SimData) o1).getPrd());
                    Float x2 = (float)Float.parseFloat(((SimData) o2).getPrd());
                    if(ordt.equals("Ascending")){
                        return Float.compare(x1,x2);
                    }
                    else{
                        return Float.compare(x2,x1);
                    }
                }
                else{
                    int x1 = (int)Integer.parseInt(((SimData) o1).getDad());
                    int x2 = (int)Integer.parseInt(((SimData) o2).getDad());
                    if(ordt.equals("Ascending")){
                        return x1-(x2);
                    }
                    else{
                        return x2-(x1);
                    }
                }





            }
        });
       return roles;
    }



}
