package com.example.bschiranth.animation_hw10_chiranth;

import android.content.Context;
import android.graphics.Movie;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by bschiranth on 17-02-16.
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder>{

    private List<Map<String,?>> mDataset;
    private Context mContext;
    public MovieData movieData;

    public MyRecyclerViewAdapter(Context myContext,List<Map<String, ?>> myDataset) {
        this.mDataset = myDataset;
        this.mContext = myContext;
    }

    public interface OnItemClickListener{
        public void onItemClick(View v, int position);
        public void onItemLongClick(View v, int position);
        //when the options inside the card view is clicked
        public void onOverflowMenuClick(View v, int position);

    }

        //mitemClickListener is an interface that has onclick methods
    OnItemClickListener mItemClickListener;

    public void setOnItemClickListener(final OnItemClickListener ItemClickListener){mItemClickListener = ItemClickListener;}


    public class ViewHolder extends RecyclerView.ViewHolder{
        ViewGroup cardContainer;
        ImageView imv;
        TextView txt1;
        TextView txt2;
        //CheckBox chck;
        RatingBar rb;
        ImageView cardOptionsImageView;

        public ViewHolder(View v) {
            super(v);//initilizes item view in super class
            imv = (ImageView) v.findViewById(R.id.movieicon);
            cardContainer = (ViewGroup) v.findViewById(R.id.cv);
            txt1 = (TextView) v.findViewById(R.id.movietitle);
            txt2 = (TextView) v.findViewById(R.id.moviedescr);
           // chck = (CheckBox) v.findViewById(R.id.checkBox);
            rb = (RatingBar) v.findViewById(R.id.indicator_ratingbar);
            //find overflow button image view reference
            cardOptionsImageView = (ImageView) v.findViewById(R.id.options_id);
        ///////////////////////////////////////////
                   // HashMap<String,?> stringMap = movieData.getItem(getPosition());

                    v.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mItemClickListener != null)
                                // Log.d("msg1:", "-----------------------Debug message 1----------------------------");


                                mItemClickListener.onItemClick(v, getPosition());
                            //Log.d("msg2:", "-----------------------Debug message 2----------------------------");
                        }
                    });
            ////////////////////////////////////////////////
            v.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mItemClickListener != null)   mItemClickListener.onItemLongClick(v,getPosition());
                    return true;
                }
            });
            ///////////////////////////////////////////////////
            if(cardOptionsImageView!=null) {
                cardOptionsImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mItemClickListener!=null)  mItemClickListener.onOverflowMenuClick(v,getPosition());
                    }
                });
            }
            ///////////////////////////////////////////////////
        }//end of viewholder constructor
    }//end of Viewholder class

    //for new view overrride oncrete new view holder with return type viewholder
    @Override
    public  MyRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View v;

        //handling ratings
     //   if(viewType==0){

            v= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_layout,parent,false);
       // }else{
       //     v= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_layout_right,parent,false);
       // }
        ViewHolder vh = new ViewHolder(v);//calls viewholder constructor with input arg view v

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Map<String, ?> movie = mDataset.get(position);

        holder.imv.setImageResource((Integer) movie.get("image"));
        holder.imv.setTransitionName((String) movie.get("name"));//setname

        holder.txt1.setText((String) movie.get("name"));
        holder.txt2.setText((String) movie.get("description"));

        // holder.rb.setRating(((Double) movie.get("rating")).floatValue());
        double ratingBarValue = (Double) movie.get("rating");
        float f = (float) ratingBarValue;
        f = (f / 2.0f);
        holder.rb.setRating(f); //Pass the float value


    }


    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    @Override
    public int getItemViewType(int position) {
        double rating = (Double)mDataset.get(position).get("rating");
        if(rating < 8){
            return 0;
        }
        else{
            return 1;
        }
    }


}//end of adapter class