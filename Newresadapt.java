package com.example.firstapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.util.LruCache;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static com.example.firstapp.R.color.pink;

public class Newresadapt extends RecyclerView.Adapter<Newresadapt.ViewHolder>{

    private Context cv;
    private List<ResultData> data;
    SharedPreferences mPrefs;
    String frm;
    Drawable dr;
    String title;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    public Newresadapt(Context cv, List<ResultData> data) {
        this.cv = cv;
        this.data = data;
        this.frm="";
    }
    public Newresadapt(Context cv, List<ResultData> data,String frm) {
        this.cv = cv;
        this.data = data;
        this.frm=frm;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater layoutInflater=LayoutInflater.from(cv);
        view=layoutInflater.inflate(R.layout.results_card,viewGroup,false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        try {
            String imv=data.get(i).getViewItemURL();
            Log.d("imv",imv);

//            viewHolder.img.setImageUrl(imv,mImageLoader);

            Picasso.with(cv).load(imv).placeholder(R.drawable.placeholder).error(R.drawable.error).into(viewHolder.img);
            mPrefs = cv.getSharedPreferences("pn",MODE_PRIVATE);
            title=data.get(i).getTitle().toUpperCase();
//            if(title.length()>35 )
//                title=title.substring(0,title.substring(0,35).lastIndexOf(' '))+"...";
            viewHolder.tl.setText(title);
            viewHolder.tl.setMinLines(3);
//            Layout tvl = viewHolder.tl.getLayout();

            viewHolder.tl.setTypeface(null, Typeface.BOLD);
            viewHolder.tl.setTextColor(Color.BLACK);
            viewHolder.zp.setText("Zip: "+data.get(i).getPostalCode());
            viewHolder.zp.setTextColor(Color.GRAY);
            if(data.get(i).getShipcost().equals("Free")){
                viewHolder.sc.setText(data.get(i).getShipcost()+" Shipping");
            }
            else{
                viewHolder.sc.setText("$"+data.get(i).getShipcost());
            }


            viewHolder.sc.setTextColor(Color.GRAY);
            viewHolder.c.setText("$"+data.get(i).getCost());
            viewHolder.c.setTextColor(cv.getResources().getColor(R.color.pink));
            viewHolder.c.setTypeface(null, Typeface.BOLD);
            viewHolder.cond.setText(data.get(i).getCondition());
            viewHolder.cond.setTextColor(cv.getResources().getColor(R.color.grey));

            if(mPrefs.contains(data.get(i).getId())){
                dr= cv.getResources().getDrawable(R.drawable.remove_cart);
            }
            else{
                dr= cv.getResources().getDrawable(R.drawable.add_cart);
            }
            viewHolder.wb.setCompoundDrawablesWithIntrinsicBounds(dr, null, null, null);
            viewHolder.wb.setText("");
            mPrefs.registerOnSharedPreferenceChangeListener(
                    new SharedPreferences.OnSharedPreferenceChangeListener() {
                        public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                            notifyDataSetChanged();

                        }
                    });

            viewHolder.wb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences.Editor prefsEditor = mPrefs.edit();
                    if(!mPrefs.contains(data.get(i).getId())) {
                        Gson gson = new Gson();
                        String js = gson.toJson(data.get(i));
                        prefsEditor.putString(data.get(i).getId(), js);
                        prefsEditor.commit();
                        Drawable ddr= cv.getResources().getDrawable(R.drawable.remove_cart);
                        viewHolder.wb.setCompoundDrawablesWithIntrinsicBounds(ddr, null, null, null);
                        TextView tn=new TextView(cv);
                        ViewGroup.LayoutParams lpt= new ViewGroup.LayoutParams(150, ViewGroup.LayoutParams.WRAP_CONTENT);
                        Toast.makeText(cv.getApplicationContext(),data.get(i).getTitle().toString()+" was added to wishlist",Toast.LENGTH_LONG).show();
                    }


                    else{
                        prefsEditor.remove(data.get(i).getId());
                        prefsEditor.commit();
                        Drawable ddr= cv.getResources().getDrawable(R.drawable.add_cart);
                        viewHolder.wb.setCompoundDrawablesWithIntrinsicBounds(ddr, null, null, null);
                        if(frm.equals("wish")){
                            Log.d("resultadapter","wish!!!!!!!!!!!!!!!!!!!!!!!!!!!"+data.get(i).getId());
                            ResultData resd = null;
                            for(ResultData usr:data) {
                                if(usr.getId().equals(data.get(i).getId())) {
                                    resd = usr;
                                    break;
                                }
                            }
                            data.remove(resd);
                            Toast.makeText(cv.getApplicationContext(),data.get(i).getTitle().toString()+" was removed from wishlist",Toast.LENGTH_LONG).show();
                            notifyDataSetChanged();

                        }

                    }

                }
            });
//            viewHolder.wb.setBackgroundResource(R.drawable.add_cart);
            viewHolder.cvv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(cv.getApplicationContext(),data.get(i).toString(),Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(cv,Details_tab.class);
                    intent.putExtra("itemid",data.get(i).getId());
                    intent.putExtra("title",data.get(i).getTitle());
                    intent.putExtra("shipcost",data.get(i).getShipcost());
                    Log.d("resadapter","viewitemurlset+++");
                    intent.putExtra("fburl",data.get(i).getVurl());
                    Gson gson = new Gson();
                    String js = gson.toJson(data.get(i));
                    intent.putExtra("item",js);
                    intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                    cv.startActivity(intent);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tl, zp, sc,cond,c,wb;
        ImageView img;
        CardView cvv;
        //        NetworkImageView img ;
        public ViewHolder(View iv){
            super(iv);
            cvv =(CardView) iv.findViewById(R.id.rescard);
            tl =(TextView) iv.findViewById(R.id.title);
            zp =(TextView) iv.findViewById(R.id.zip);
            sc =(TextView) iv.findViewById(R.id.ship_cost);
            cond=(TextView) iv.findViewById(R.id.cond);
            c=(TextView) iv.findViewById(R.id.cost);
            img =(ImageView) iv.findViewById(R.id.imageViewres);
            wb=(TextView) iv.findViewById(R.id.wishbut);
        }
    }
}