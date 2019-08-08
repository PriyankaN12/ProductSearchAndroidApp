package com.example.firstapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class ProdDetFrag extends Fragment {
    View v;
    Bundle dd;
    LayoutInflater inflater;
    @Override
    public View onCreateView(LayoutInflater inflater1, ViewGroup container, Bundle savedInstanceState) {
        inflater=inflater1;
        v = inflater.inflate(R.layout.fragment_products_tab, container, false);
        dd = this.getArguments();

        if (dd != null) {
//            Log.d("proddetails  ", dd.getString("id"));
            String id = dd.getString("id");
            String url = "https://prodsearch-236607.appspot.com/findproddet?id=" + id;
            RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
            Log.d("proddetails url: ", url);
            StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("proddetails", "Response is: " + response);
                            String title="",cp="",sp="",br="",sub="";
                            ArrayList<String> specs=new ArrayList<String>();
                            ArrayList<String> pics=new ArrayList<String>();

                            try {
                                JSONObject jsonob = new JSONObject(response);
                                int ki=0;
                                if(jsonob.has("Item")){

                                    JSONObject jo1=jsonob.getJSONObject("Item");
                                    if(jo1.has("PictureURL")){
                                        JSONArray ja1=jo1.getJSONArray("PictureURL");
                                        for(int i=0;i<ja1.length();i++){
                                            pics.add(ja1.getString(i));
                                            ki=1;
                                        }
                                        setImage(pics,v);
                                        if(ja1.length()==0){
                                            HorizontalScrollView hr=(HorizontalScrollView)v.findViewById(R.id.horsc);
                                            hr.setVisibility(View.GONE);
                                        }

                                    }
                                    else{
                                        HorizontalScrollView hr=(HorizontalScrollView)v.findViewById(R.id.horsc);
                                        hr.setVisibility(View.GONE);
                                    }
                                    if(jo1.has("Title")){
                                        title=jo1.getString("Title");
                                        TextView t=(TextView) v.findViewById(R.id.prtitle);
                                        t.setText(title);
                                        ki=1;
                                    }
                                    else{
                                        TextView t=(TextView) v.findViewById(R.id.prtitle);
                                        t.setVisibility(View.GONE);
                                    }
                                    if(jo1.has("CurrentPrice")){
                                        JSONObject jot=jo1.getJSONObject("CurrentPrice");
                                        if(jot.has("Value")){
                                            cp="$"+jot.getString("Value");
                                            TextView t=(TextView) v.findViewById(R.id.prprice);
                                            t.setText(cp);
                                            TextView t1=(TextView) v.findViewById(R.id.prhiprde);
                                            t1.setText(cp);
                                            ki=1;

                                        }
                                        else{
                                            TextView t=(TextView) v.findViewById(R.id.prprice);
                                            t.setVisibility(View.GONE);
                                        }
                                    }
                                    else{
                                        TextView t=(TextView) v.findViewById(R.id.prprice);
                                        t.setVisibility(View.GONE);
                                    }
                                    String ship=dd.getString("sc");
                                    if(ship!=null){
                                        if(ship.equals("Free")){
                                            sp=" With Free Shipping";
                                        }
                                        else{
                                            sp=" With $"+ship+" Shipping";
                                        }
                                        TextView t=(TextView)v.findViewById(R.id.prship);
                                        t.setText(sp);
                                    }
                                    else{
                                        TextView t=(TextView) v.findViewById(R.id.prship);
                                        t.setVisibility(View.GONE);
                                    }
                                    if(jo1.has("Subtitle")){
                                        sub=jo1.getString("Subtitle");
                                        TextView t=(TextView)v.findViewById(R.id.prsubdesc);
                                        t.setText(sub);
                                        t.setWidth(600);
                                        ki=1;
                                    }
                                    else{
                                        TableRow t=(TableRow) v.findViewById(R.id.prsub);
                                        t.setVisibility(View.GONE);
                                    }
                                    if(jo1.has("ItemSpecifics")){
                                        JSONObject jot=jo1.getJSONObject("ItemSpecifics");
                                        if(jot.has("NameValueList")){
                                            JSONArray jat1=jot.getJSONArray("NameValueList");
                                            int brs=0;
                                            for(int j=0;j<jat1.length();j++){
                                                String name=jat1.getJSONObject(j).getString("Name");
                                                String val=jat1.getJSONObject(j).getJSONArray("Value").getString(0);
                                                String vc= val.substring(0, 1).toUpperCase() + val.substring(1);
                                                ki=1;
                                                if(name.equals("Brand")){
                                                    br=vc;
                                                    brs=1;
                                                    specs.add(0,vc);
                                                }
                                                else{

                                                    specs.add(vc);
                                                }

                                            }
                                            setSpec(specs,v);
                                            if(br.length()>0 && brs==1){
                                                TextView t=(TextView) v.findViewById(R.id.prhibrde);
                                                t.setText(br);
                                            }
                                            else{
                                                TableRow t=(TableRow) v.findViewById(R.id.prhibr);
                                                t.setVisibility(View.GONE);
                                            }

                                        }

                                    }
                                    else{
                                        TableRow tr1=(TableRow)v.findViewById(R.id.prhibr);
                                        tr1.setVisibility(View.GONE);
                                        LinearLayout ll=(LinearLayout)v.findViewById(R.id.prspecs);
                                        ll.setVisibility(View.GONE);

                                    }




                                }
                                else{
                                    LinearLayout ll1=(LinearLayout)v.findViewById(R.id.prno);
                                    ll1.setVisibility(View.VISIBLE);
                                }
                            if(ki==0){
                                LinearLayout ll1=(LinearLayout)v.findViewById(R.id.prno);
                                ll1.setVisibility(View.VISIBLE);
                            }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("proddetails", "That didn't work!");
                    LinearLayout ll1=(LinearLayout)v.findViewById(R.id.prno);
                    ll1.setVisibility(View.VISIBLE);
                }
            });


                    queue.add(stringRequest);
        }
        else{
            LinearLayout ll1=(LinearLayout)v.findViewById(R.id.prno);
            ll1.setVisibility(View.VISIBLE);
        }
        return v;
    }

    public void setImage(ArrayList<String> im,View v){
        LinearLayout mGallery = (LinearLayout) v.findViewById(R.id.id_gallery);
        Iterator<String> imi = im.iterator();
        while(imi.hasNext()){
            View view1 = inflater.inflate(R.layout.prod_image,
                    mGallery, false);
            String img=imi.next();
            ImageView imgv = (ImageView) view1.findViewById(R.id.prodimg);

            Picasso.with(getActivity().getApplicationContext()).load(img).into(imgv);
            mGallery.addView(view1);
        }
    }
    public void setSpec(ArrayList<String> a,View v){
        if(a.size()>0){
            LinearLayout l1=(LinearLayout) v.findViewById(R.id.prspli);
            Iterator<String> ai = a.iterator();
            LinearLayout.LayoutParams params = new
                    LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0,0,0,10);
            while (ai.hasNext()){
                String sp=ai.next();
                TextView tv = new TextView(getActivity().getApplicationContext());
                String spe="&#8226; "+sp;
                tv.setText(Html.fromHtml(spe));
                tv.setLayoutParams(params);
                l1.addView(tv);
            }

        }
    }
}
