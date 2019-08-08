package com.example.firstapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Results extends AppCompatActivity {
    private static RecyclerView.Adapter adapter;
    private GridLayoutManager layoutManager;
    private static RecyclerView rv;
    String itemcount;
    List<ResultData> resob;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        resob = new ArrayList<ResultData>();
        rv=(RecyclerView) findViewById(R.id.rv);
        rv.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        Result_Adapter rad=new Result_Adapter(getApplicationContext(),resob);
        rv.setAdapter(rad);

        Toolbar toolbar = findViewById(R.id.toolbartt);
        toolbar.setNavigationIcon(R.drawable.arrow);
        toolbar.setTitle("Search Results");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("results","onclick invoked");
                finish();
            }
        });
        final LinearLayout prg = (LinearLayout)findViewById(R.id.prog);

        Intent ine=getIntent();
        key=ine.getStringExtra("keyword");
        String url="https://prodsearch-236607.appspot.com/findprod?";
        url+="keyword="+ine.getStringExtra("keyword");
        url+="&category="+ine.getStringExtra("category");
        String zip="";
        if(ine.hasExtra("zipcode")){
            url+="&zip="+ine.getStringExtra("zipcode");
        }
        else{
            url+="&zip=90007";
        }
        if(ine.hasExtra("new")){
            url+="&new=true";
        }
        else{
            url+="&new=false";
        }
        if(ine.hasExtra("used")){
            url+="&used=true";
        }
        else{
            url+="&used=false";
        }
        if(ine.hasExtra("unspecified")){
            url+="&unspecified=true";
        }
        else{
            url+="&unspecified=false";
        }
        if(ine.hasExtra("local")){
            url+="&local=true";
        }
        else{
            url+="&local=false";
        }
        if(ine.hasExtra("free")){
            url+="&free=true";
        }
        else{
            url+="&free=false";
        }
        if(ine.hasExtra("dist")){
            String dd=ine.getStringExtra("dist");
            Log.d("search","distance-------------------: "+ dd);
            url+="&dist="+dd;
        }
        else{
            url+="&dist=10";
        }
        Log.d("search","Url: "+ url);
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            Log.d("search", "Cur zipCode is: " + response);
                            JSONObject jo = new JSONObject(response);
                            if (jo.has("findItemsAdvancedResponse")) {
                                JSONArray ja = jo.getJSONArray("findItemsAdvancedResponse");
                                JSONObject jsonob = ja.getJSONObject(0);
                                if (jsonob.has("searchResult")) {
                                    JSONArray res = jsonob.getJSONArray("searchResult");
                                    JSONObject job = res.getJSONObject(0);
                                    itemcount = job.getString("@count");
                                    if (job.has("item")) {
                                        JSONArray it = job.getJSONArray("item");

                                        for (int i = 0; i < it.length(); i++) {
                                            JSONObject iti = it.getJSONObject(i);
                                            String id = "N/A", title = "N/A", img = "N/A", pc = "N/A", cond = "N/A", sc = "N/A", c = "N/A", vurl = "";

                                            if (iti.has("itemId")) {
                                                id = iti.getJSONArray("itemId").getString(0);
                                            }
                                            if (iti.has("title")) {
                                                title = iti.getJSONArray("title").getString(0);
                                            }
                                            if (iti.has("galleryURL")) {
                                                img = iti.getJSONArray("galleryURL").getString(0);
                                            }
                                            if (iti.has("viewItemURL")) {
                                                vurl = iti.getJSONArray("viewItemURL").getString(0);
                                            }

                                            if (iti.has("postalCode")) {
                                                pc = iti.getJSONArray("postalCode").getString(0);
                                            }

                                            if (iti.has("shippingInfo")) {
                                                JSONArray ja1 = iti.getJSONArray("shippingInfo");
                                                JSONObject jo1 = ja1.getJSONObject(0);
                                                if (jo1.has("shippingServiceCost")) {
                                                    JSONArray ja2 = jo1.getJSONArray("shippingServiceCost");
                                                    JSONObject jo2 = ja2.getJSONObject(0);
                                                    if (jo2.has("__value__")) {
                                                        sc = jo2.getString("__value__");
                                                        if (Float.parseFloat(sc) == 0) {
                                                            sc = "Free";
                                                        }
                                                    }
                                                }

                                            }
                                            if (iti.has("sellingStatus")) {
                                                JSONArray ja1 = iti.getJSONArray("sellingStatus");
                                                JSONObject jo1 = ja1.getJSONObject(0);
                                                if (jo1.has("currentPrice")) {
                                                    JSONArray ja2 = jo1.getJSONArray("currentPrice");
                                                    JSONObject jo2 = ja2.getJSONObject(0);
                                                    if (jo2.has("__value__")) {
                                                        c = jo2.getString("__value__");
                                                    }
                                                }
                                            }
                                            if (iti.has("condition")) {
                                                JSONArray ja1 = iti.getJSONArray("condition");
                                                JSONObject jo1 = ja1.getJSONObject(0);
                                                if (jo1.has("conditionDisplayName")) {
                                                    cond = jo1.getJSONArray("conditionDisplayName").getString(0);
                                                }
                                            }

                                            ResultData rd = new ResultData(title, img, pc, id, c, cond, sc, vurl);
                                            resob.add(rd);
                                        }


                                        create_list(resob);
                                    } else {
                                        LinearLayout l0 = (LinearLayout) findViewById(R.id.resno);
                                        l0.setVisibility(View.VISIBLE);
                                        prg.setVisibility(View.GONE);
                                    }
                                } else {
                                    LinearLayout l0 = (LinearLayout) findViewById(R.id.resno);
                                    l0.setVisibility(View.VISIBLE);
                                    prg.setVisibility(View.GONE);
                                }

                            }
                            else {
                                LinearLayout l0 = (LinearLayout) findViewById(R.id.resno);
                                l0.setVisibility(View.VISIBLE);
                                prg.setVisibility(View.GONE);
                            }
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    prg.setVisibility(View.GONE);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("srch","That didn't work!");
                prg.setVisibility(View.GONE);
                LinearLayout l0=(LinearLayout) findViewById(R.id.resno);
                l0.setVisibility(View.VISIBLE);
            }
        });
        queue.add(stringRequest);
    }
    public void create_list(List<ResultData> a){
        if(a.size()>0) {
            Log.d("Swapping", "swapped");
            Result_Adapter radi = new Result_Adapter(getApplicationContext(), a);
            rv.swapAdapter(radi, false);
            TextView ic = (TextView) findViewById(R.id.rescount);
            ic.setText(itemcount);
            TextView itc = (TextView) findViewById(R.id.restitle);
            itc.setText(key);
            LinearLayout ll = (LinearLayout) findViewById(R.id.resitc);
            rv.setVisibility(View.VISIBLE);
            ll.setVisibility(View.VISIBLE);
        }
        else{
            LinearLayout l0=(LinearLayout) findViewById(R.id.resno);
            l0.setVisibility(View.VISIBLE);
//            prg.setVisibility(View.GONE);
        }

    }
    public void onResume() {

        super.onResume();
        Result_Adapter radi=new Result_Adapter(getApplicationContext(),resob);
        rv.swapAdapter(radi,false);
    }

}
