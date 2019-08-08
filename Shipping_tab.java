package com.example.firstapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.Html;
import android.text.Layout;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.UnderlineSpan;
import android.text.util.Linkify;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.wssholmes.stark.circular_score.CircularScoreView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.os.Build.VERSION_CODES.N;
import static android.text.Html.FROM_HTML_MODE_COMPACT;
import static java.lang.Integer.parseInt;


public class Shipping_tab extends Fragment {

    View v;
    Bundle dd;
    String sturl;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v= inflater.inflate(R.layout.fragment_shipping_tab, container, false);
        dd = this.getArguments();
        final LinearLayout prg = (LinearLayout)v.findViewById(R.id.progsh);
        if (dd != null) {

            String id = dd.getString("id");
            final String ship=dd.getString("sc");
            String url = "https://prodsearch-236607.appspot.com/findproddet?id=" + id;
            RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
            Log.d("shiptab url: ", url);
            StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("shiptabdetails", "Response is: " + response);
                            String stname="",fsc="",frs="",sub="";
                            sturl="";
                            ArrayList<String> specs=new ArrayList<String>();
                            ArrayList<String> pics=new ArrayList<String>();
                            try {
                                JSONObject jsonob = new JSONObject(response);
                                if(jsonob.has("Item")){
                                    int sb=0;
                                    LinearLayout ls1=(LinearLayout)v.findViewById(R.id.ship);
                                    ls1.setVisibility(View.VISIBLE);
                                    final float inPixels= getActivity().getResources().getDimension(R.dimen.dimen_entry_in_dp);
                                    int pix=Math.round(inPixels);
                                    final float intop= getActivity().getResources().getDimension(R.dimen.dimentop);
                                    int top=Math.round(intop);
                                    final float inlft= getActivity().getResources().getDimension(R.dimen.dimenleft);
                                    int lft=Math.round(inlft);
                                    TableLayout.LayoutParams lp = new TableLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.WRAP_CONTENT);
                                    lp.setMargins(lft,top,0,0);
                                    TableLayout.LayoutParams lptt = new TableLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.WRAP_CONTENT);
                                    lptt.setMargins(lft,0,0,0);
                                    ViewGroup.LayoutParams lpt= new ViewGroup.LayoutParams(pix, ViewGroup.LayoutParams.WRAP_CONTENT);
                                    JSONObject jo1=jsonob.getJSONObject("Item");
                                    TableLayout tl = (TableLayout) v.findViewById(R.id.shsoldtab);
                                    TableLayout tl1 = (TableLayout) v.findViewById(R.id.shshiptab);
                                    TableLayout tl2 = (TableLayout) v.findViewById(R.id.shrettab);

                                    int pos=0;
                                    int pos1=0;
                                    int pos2=0;
                                    if(jo1.has("Storefront")) {
                                            JSONObject jo2 = jo1.getJSONObject("Storefront");
                                            if (jo2.has("StoreName")) {
                                                stname = jo2.getString("StoreName");
                                                TableRow row = new TableRow(getActivity().getApplicationContext());

                                                row.setLayoutParams(lptt);
                                                LinearLayout ll1 = new LinearLayout(getActivity().getApplicationContext());

                                                TextView tv = new TextView(getActivity().getApplicationContext());

                                                tv.setLayoutParams(lpt);
                                                TextView tv1 = new TextView(getActivity().getApplicationContext());
                                                tv.setText("Store Name");

                                                tv.setTypeface(null,Typeface.BOLD);
                                                tv.setTextColor(Color.BLACK);

                                                tv1.setText(stname);
                                                tv1.setLinkTextColor(getResources().getColor(R.color.lightpink));
                                                if (jo2.has("StoreURL")) {
                                                    sturl = jo2.getString("StoreURL");
                                                    SpannableString content = new SpannableString(stname);
                                                    content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
                                                    tv1.setText(content);
//                                                    String stso = "<a href='" + sturl + "'>" + stname + "</a>";
                                                    tv1.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            Uri uri = Uri.parse(sturl);
                                                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                            startActivity(intent);
                                                        }
                                                    });

                                                    tv1.setClickable(true);
//                                                    tv1.setMovementMethod(LinkMovementMethod.getInstance());
                                                    tv1.setTextColor(getActivity().getResources().getColor(R.color.pink));
                                                }
                                                ll1.addView(tv);
                                                ll1.addView(tv1);
                                                row.addView(ll1);
                                                tl.addView(row,pos);
                                                pos+=1;
                                                sb=1;

                                            }
                                        }
                                        if(jo1.has("Seller")){
                                            JSONObject jo2=jo1.getJSONObject("Seller");
                                            if(jo2.has("FeedbackScore")){
                                                fsc=jo2.getString("FeedbackScore");
                                                TableRow row= new TableRow(getActivity().getApplicationContext());
                                                if(pos!=0){
                                                    row.setLayoutParams(lp);
                                                }
                                                else {
                                                    row.setLayoutParams(lptt);
                                                }

                                                LinearLayout ll1=new LinearLayout(getActivity().getApplicationContext());

                                                TextView tv = new TextView(getActivity().getApplicationContext());
                                                tv.setLayoutParams(lpt);
                                                TextView tv1 = new TextView(getActivity().getApplicationContext());
                                                tv.setText("Feedback score");
                                                tv.setTypeface(null,Typeface.BOLD);
                                                tv.setTextColor(Color.BLACK);
                                                tv1.setText(fsc);
                                                ll1.addView(tv);
                                                ll1.addView(tv1);
                                                row.addView(ll1);
                                                tl.addView(row,pos);
                                                pos+=1;
                                                sb=1;

                                            }
                                            if(jo2.has("PositiveFeedbackPercent")){
                                                String pfc=jo2.getString("PositiveFeedbackPercent");
                                                TableRow row= (TableRow) v.findViewById(R.id.circ);
                                                CircularScoreView cc=(CircularScoreView) v.findViewById(R.id.circd);
                                                cc.setScore(Math.round(Float.parseFloat(pfc)));
                                                row.setVisibility(View.VISIBLE);
                                                pos+=1;
                                                sb=1;
                                            }
                                            if(jo2.has("FeedbackRatingStar")){
                                                frs=jo2.getString("FeedbackRatingStar");
                                                Log.d("feedback","----------------!!!@@@"+frs);
                                                TableRow row= new TableRow(getActivity().getApplicationContext());
                                                row.setLayoutParams(lptt);
                                                LinearLayout ll1=new LinearLayout(getActivity().getApplicationContext());


                                                TextView tv = new TextView(getActivity().getApplicationContext());

                                                tv.setLayoutParams(lpt);
                                                ImageView im=new ImageView(getActivity().getApplicationContext());
                                                tv.setText("Feedback star");
                                                tv.setTextColor(Color.BLACK);
                                                tv.setTypeface(null,Typeface.BOLD);
                                                Drawable drawable;
                                                int ind=frs.indexOf("Shooting");
                                                String color;
                                                if(ind!=-1){
                                                    drawable = ContextCompat.getDrawable(getActivity(), R.drawable.starcirc);
                                                    color=frs.substring(0,ind);
                                                }
                                                else{
                                                    drawable = ContextCompat.getDrawable(getActivity(), R.drawable.starcircout);
                                                    color=frs;
                                                }
                                                Drawable wrappedDrawable;
                                                switch (color){
                                                    case "Blue":
                                                        wrappedDrawable = DrawableCompat.wrap(drawable);
                                                        DrawableCompat.setTint(wrappedDrawable, ContextCompat.getColor(getActivity(), R.color.blue));
                                                        break;
                                                    case "Red":
                                                        wrappedDrawable = DrawableCompat.wrap(drawable);
                                                        DrawableCompat.setTint(wrappedDrawable, ContextCompat.getColor(getActivity(), R.color.red));
                                                        break;
                                                    case "Green":
                                                        wrappedDrawable = DrawableCompat.wrap(drawable);
                                                        DrawableCompat.setTint(wrappedDrawable, ContextCompat.getColor(getActivity(), R.color.green));
                                                        break;
                                                    case "Turquoise":
                                                        wrappedDrawable = DrawableCompat.wrap(drawable);
                                                        DrawableCompat.setTint(wrappedDrawable, ContextCompat.getColor(getActivity(), R.color.turquoise));
                                                        break;
                                                    case "Yellow":
                                                         wrappedDrawable = DrawableCompat.wrap(drawable);
                                                        DrawableCompat.setTint(wrappedDrawable, ContextCompat.getColor(getActivity(), R.color.yellow));
                                                        break;
                                                    case "Purple":
                                                        wrappedDrawable = DrawableCompat.wrap(drawable);
                                                        DrawableCompat.setTint(wrappedDrawable, ContextCompat.getColor(getActivity(), R.color.violet));
                                                        break;
                                                    case "Silver":
                                                        wrappedDrawable = DrawableCompat.wrap(drawable);
                                                        DrawableCompat.setTint(wrappedDrawable, ContextCompat.getColor(getActivity(), R.color.silver));
                                                        break;
                                                    default:
                                                        wrappedDrawable = DrawableCompat.wrap(drawable);
                                                        DrawableCompat.setTint(wrappedDrawable, ContextCompat.getColor(getActivity(), R.color.black));
                                                        break;
                                                }
                                                im.setImageDrawable(drawable);

//                                                im.setColorFilter(getActivity().getResources().getColor(R.color.);

                                                ll1.addView(tv);
                                                ll1.addView(im);
                                                row.addView(ll1);

                                                tl.addView(row,pos);
                                                pos+=1;
                                                sb=1;

                                            }

                                        }
                                        if(ship!=null && ship.length()>0){
                                            Log.d("shipdeat","################"+ship);
                                            TableRow row= new TableRow(getActivity().getApplicationContext());
                                            row.setLayoutParams(lp);
                                            LinearLayout ll1=new LinearLayout(getActivity().getApplicationContext());

                                            TextView tv = new TextView(getActivity().getApplicationContext());
                                            tv.setLayoutParams(lpt);
                                            TextView tv1 = new TextView(getActivity().getApplicationContext());
                                            tv.setText("Shipping Cost");
                                            tv.setTypeface(null,Typeface.BOLD);
                                            tv.setTextColor(Color.BLACK);
                                            if(ship.equals("Free")){
                                                tv1.setText("Free Shipping");
                                            }
                                            else{
                                                tv1.setText("$"+ship);
                                            }

                                            ll1.addView(tv);
                                            ll1.addView(tv1);
                                            row.addView(ll1);
                                            tl1.addView(row,pos1);
                                            pos1+=1;
                                            sb=1;
                                        }
                                        if(jo1.has("GlobalShipping")){
                                            Boolean s1=jo1.getBoolean("GlobalShipping");
                                            TableRow row= new TableRow(getActivity().getApplicationContext());
                                            row.setLayoutParams(lp);
                                            LinearLayout ll1=new LinearLayout(getActivity().getApplicationContext());

                                            TextView tv = new TextView(getActivity().getApplicationContext());
                                            tv.setLayoutParams(lpt);
                                            TextView tv1 = new TextView(getActivity().getApplicationContext());
                                            tv.setText("Global Shipping");
                                            tv.setTypeface(null,Typeface.BOLD);
                                            tv.setTextColor(Color.BLACK);
                                            if(s1){
                                                tv1.setText("Yes");
                                            }
                                            else{
                                                tv1.setText("No");
                                            }

                                            ll1.addView(tv);
                                            ll1.addView(tv1);
                                            row.addView(ll1);
                                            tl1.addView(row,pos1);
                                            pos1+=1;
                                            sb=1;
                                        }
                                    if(jo1.has("HandlingTime")){
                                        int s1=jo1.getInt("HandlingTime");
                                        TableRow row= new TableRow(getActivity().getApplicationContext());
                                        row.setLayoutParams(lp);
                                        LinearLayout ll1=new LinearLayout(getActivity().getApplicationContext());

                                        TextView tv = new TextView(getActivity().getApplicationContext());
                                        tv.setLayoutParams(lpt);
                                        TextView tv1 = new TextView(getActivity().getApplicationContext());
                                        tv.setText("Handling Time");
                                        tv.setTypeface(null,Typeface.BOLD);
                                        tv.setTextColor(Color.BLACK);
                                        if(s1<2){
                                            tv1.setText(Integer.toString(s1)+" day");
                                        }
                                        else{
                                            tv1.setText(Integer.toString(s1)+" days");
                                        }

                                        ll1.addView(tv);
                                        ll1.addView(tv1);
                                        row.addView(ll1);
                                        tl1.addView(row,pos1);
                                        pos1+=1;
                                        sb=1;
                                    }
                                    if(jo1.has("ConditionDisplayName")){
                                        String s1=jo1.getString("ConditionDisplayName");
                                        TableRow row= new TableRow(getActivity().getApplicationContext());
                                        row.setLayoutParams(lp);
                                        LinearLayout ll1=new LinearLayout(getActivity().getApplicationContext());

                                        TextView tv = new TextView(getActivity().getApplicationContext());
                                        tv.setLayoutParams(lpt);
                                        TextView tv1 = new TextView(getActivity().getApplicationContext());
                                        tv.setText("Condition");
                                        tv.setTypeface(null,Typeface.BOLD);
                                        tv.setTextColor(Color.BLACK);
                                        tv1.setText(s1);
                                        ll1.addView(tv);
                                        ll1.addView(tv1);
                                        row.addView(ll1);
                                        tl1.addView(row,pos1);
                                        pos1+=1;
                                        sb=1;
                                    }
                                    if(jo1.has("ReturnPolicy")) {
                                        JSONObject jo2 = jo1.getJSONObject("ReturnPolicy");
                                        if (jo2.has("ReturnsAccepted")) {
                                            String s1 = jo2.getString("ReturnsAccepted");
                                            TableRow row = new TableRow(getActivity().getApplicationContext());
                                            row.setLayoutParams(lp);
                                            LinearLayout ll1 = new LinearLayout(getActivity().getApplicationContext());
                                            TextView tv = new TextView(getActivity().getApplicationContext());
                                            tv.setLayoutParams(lpt);
                                            TextView tv1 = new TextView(getActivity().getApplicationContext());
                                            tv.setText("Policy");
                                            tv.setTypeface(null, Typeface.BOLD);
                                            tv.setTextColor(Color.BLACK);
                                            tv1.setText(s1);
                                            ll1.addView(tv);
                                            ll1.addView(tv1);
                                            row.addView(ll1);
                                            tl2.addView(row, pos2);
                                            pos2 += 1;
                                            sb = 1;

                                        }
                                        if (jo2.has("ReturnsWithin")) {
                                            String s1 = jo2.getString("ReturnsWithin");
                                            TableRow row = new TableRow(getActivity().getApplicationContext());
                                            row.setLayoutParams(lp);
                                            LinearLayout ll1 = new LinearLayout(getActivity().getApplicationContext());
                                            TextView tv = new TextView(getActivity().getApplicationContext());
                                            tv.setLayoutParams(lpt);
                                            TextView tv1 = new TextView(getActivity().getApplicationContext());
                                            tv.setText("Returns within");
                                            tv.setTypeface(null, Typeface.BOLD);
                                            tv.setTextColor(Color.BLACK);
                                            tv1.setText(s1);
                                            ll1.addView(tv);
                                            ll1.addView(tv1);
                                            row.addView(ll1);
                                            tl2.addView(row, pos2);
                                            pos2 += 1;
                                            sb = 1;

                                        }
                                        if (jo2.has("Refund")) {
                                            String s1 = jo2.getString("Refund");
                                            TableRow row = new TableRow(getActivity().getApplicationContext());
                                            row.setLayoutParams(lp);
                                            LinearLayout ll1 = new LinearLayout(getActivity().getApplicationContext());
                                            TextView tv = new TextView(getActivity().getApplicationContext());
                                            tv.setLayoutParams(lpt);
                                            ViewGroup.LayoutParams lpt1= new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                            TextView tv1 = new TextView(getActivity().getApplicationContext());
                                            tv.setText("Refund Mode");
                                            tv.setTypeface(null, Typeface.BOLD);
                                            tv.setTextColor(Color.BLACK);
                                            tv1.setText(s1);
                                            tv1.setEllipsize(TextUtils.TruncateAt.END);
                                            tv1.setWidth(450);
                                            tv1.setMaxLines(2);
                                            tv1.setLayoutParams(lpt1);
                                            ll1.addView(tv);
                                            ll1.addView(tv1);
                                            row.addView(ll1);
                                            tl2.addView(row, pos2);
                                            pos2 += 1;
                                            sb = 1;

                                        }
                                        if (jo2.has("ShippingCostPaidBy")) {
                                            String s1 = jo2.getString("ShippingCostPaidBy");
                                            TableRow row = new TableRow(getActivity().getApplicationContext());
                                            row.setLayoutParams(lp);
                                            LinearLayout ll1 = new LinearLayout(getActivity().getApplicationContext());
                                            TextView tv = new TextView(getActivity().getApplicationContext());
                                            tv.setLayoutParams(lpt);
                                            TextView tv1 = new TextView(getActivity().getApplicationContext());
                                            tv.setText("Shipped by");
                                            tv.setTypeface(null, Typeface.BOLD);
                                            tv.setTextColor(Color.BLACK);
                                            tv1.setText(s1);
                                            ll1.addView(tv);
                                            ll1.addView(tv1);
                                            row.addView(ll1);
                                            tl2.addView(row, pos2);
                                            pos2 += 1;
                                            sb = 1;

                                        }
                                    }
                                    if(pos==0){
                                        LinearLayout ls=(LinearLayout) v.findViewById(R.id.shsold);
                                        ls.setVisibility(View.GONE);
                                    }
                                    if(pos1==0){
                                        LinearLayout ls=(LinearLayout) v.findViewById(R.id.shship);
                                        ls.setVisibility(View.GONE);

                                    }
                                    if(pos2==0){
                                        LinearLayout ls=(LinearLayout) v.findViewById(R.id.shret);
                                        ls.setVisibility(View.GONE);

                                    }
                                    if(pos1==0 && pos2==0 && pos==0){
                                        LinearLayout l0=(LinearLayout) v.findViewById(R.id.shno);
                                        l0.setVisibility(View.VISIBLE);
                                        prg.setVisibility(View.GONE);
                                    }


                                    }




                            } catch (Exception e) {
                                e.printStackTrace();

                            }
                            prg.setVisibility(View.GONE);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("shiptab", "That didn't work!");
                    LinearLayout l0=(LinearLayout) v.findViewById(R.id.shno);
                    l0.setVisibility(View.VISIBLE);
                    prg.setVisibility(View.GONE);
                }
            });

            queue.add(stringRequest);
        }
        else{
            LinearLayout l0=(LinearLayout) v.findViewById(R.id.shno);
            l0.setVisibility(View.VISIBLE);
            prg.setVisibility(View.GONE);
        }
        return v;
    }


}
