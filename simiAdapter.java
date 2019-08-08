package com.example.firstapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class simiAdapter extends RecyclerView.Adapter<simiAdapter.ViewHolder>{

    private Context cv;
    private List<SimData> data;

    public simiAdapter(Context cv, List<SimData> data) {
        this.cv = cv;
        this.data = data;
    }

    @NonNull
    @Override
    public simiAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater layoutInflater=LayoutInflater.from(cv);
        view=layoutInflater.inflate(R.layout.similiar_card,viewGroup,false);
        return new simiAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull simiAdapter.ViewHolder viewHolder, final int i) {
        try {
            String imv=data.get(i).getImg();
            Log.d("imgggggggg",imv);

            Picasso.with(cv).load(imv).placeholder(R.drawable.placeholder).error(R.drawable.error).into(viewHolder.img);
            String title=data.get(i).getTitle().toUpperCase();

            viewHolder.tl.setText(title);
            viewHolder.tl.setTextSize(18);
            viewHolder.tl.setTextColor(Color.BLACK);
            viewHolder.dy.setText(data.get(i).getDays());
            viewHolder.dy.setTextColor(Color.GRAY);
            viewHolder.sc.setText(data.get(i).getShipcost());
            viewHolder.sc.setTextColor(Color.GRAY);
            viewHolder.c.setText(data.get(i).getCost());
            viewHolder.c.setTextColor(Color.parseColor("#9308E3"));
            viewHolder.c.setTypeface(null,Typeface.BOLD);




            viewHolder.cvv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url=data.get(i).getViewItemURL();
                    if(!url.equals("N/A")){
                        Log.d("simiadapter","siiiiiimmmmmmm!!!!!!!!!!!!!"+url);
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        cv.startActivity(intent);
                    }

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tl, dy, sc,c;
        ImageView img;
        CardView cvv;
        public ViewHolder(View iv){
            super(iv);
            cvv =(CardView) iv.findViewById(R.id.simcard);
            tl =(TextView) iv.findViewById(R.id.simtitle);
            dy =(TextView) iv.findViewById(R.id.simdays);
            sc =(TextView) iv.findViewById(R.id.simship);
            c=(TextView) iv.findViewById(R.id.simprice);
            img =(ImageView) iv.findViewById(R.id.simphoto);
        }
    }

}
