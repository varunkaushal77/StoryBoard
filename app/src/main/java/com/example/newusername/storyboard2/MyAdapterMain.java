package com.example.newusername.storyboard2;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by NewUsername on 8/1/2016.
 */
public class MyAdapterMain extends RecyclerView.Adapter<MyAdapterMain.MyViewHolder> {

    ArrayList<Feeditem> feeditems;
    Context  context;

    public MyAdapterMain(Context context, ArrayList<Feeditem>feeditems)
    {
        this.feeditems=feeditems;
        this.context=context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.custom_row_main_item,parent,false);
        MyViewHolder holder=new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        YoYo.with(Techniques.FadeIn).playOn(holder.cardView);
        final Feeditem current=feeditems.get(position);
        holder.Title.setText("");
        holder.Description.setText(current.getTitle());
        holder.Date.setText(current.getPubdate());
        Picasso.with(context).load(current.getThumbnailUrl()).into(holder.Thumbnail);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,NewsDetails.class);
                intent.putExtra("Link",current.getLink());
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {

        return feeditems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Title,Description,Date;
        ImageView Thumbnail;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            Title=(TextView)itemView.findViewById(R.id.title_text);
            Description=(TextView)itemView.findViewById(R.id.description_text);
            Date=(TextView)itemView.findViewById(R.id.date_text);
            Thumbnail=(ImageView)itemView.findViewById(R.id.thumb_img);
            cardView=(CardView)itemView.findViewById(R.id.cardview);
        }
    }
}
