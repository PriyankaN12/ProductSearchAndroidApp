package com.example.firstapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
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

import static android.content.Context.MODE_PRIVATE;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class photoAdapter extends RecyclerView.Adapter<photoAdapter.ViewHolder> {

    private Context cv;
    private List<String> data;
    SharedPreferences mPrefs;

    public photoAdapter(Context cv, List<String> data) {
        this.cv = cv;
        this.data = data;
    }

    @NonNull
    @Override
    public photoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater layoutInflater=LayoutInflater.from(cv);
        view=layoutInflater.inflate(R.layout.photos_card,viewGroup,false);
        mPrefs = cv.getSharedPreferences("pn",MODE_PRIVATE);
        return new photoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull photoAdapter.ViewHolder viewHolder, final int i) {
        try {
            String imv=data.get(i);
            Log.d("imv",imv);

            Picasso.with(cv).load(imv).placeholder(R.drawable.placeholder).error(R.drawable.error).into(viewHolder.img);
//            viewHolder.cvv.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(cv.getApplicationContext(),data.get(i).toString(),Toast.LENGTH_LONG).show();
//                    Intent intent=new Intent(cv,Details_tab.class);
//                    intent.putExtra("itemid",data.get(i).getId());
//                    intent.putExtra("title",data.get(i).getTitle());
//                    intent.putExtra("shipcost",data.get(i).getShipcost());
//                    intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
//                    cv.startActivity(intent);
//                }
//            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        CardView cvv;
        public ViewHolder(View iv){
            super(iv);
            cvv =(CardView) iv.findViewById(R.id.photocard);
            img =(ImageView) iv.findViewById(R.id.photo);
        }
    }
}
