package com.example.bschiranth.animation_hw10_chiranth;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.HashMap;


public class movieFragment extends Fragment {

    public HashMap<String, ?> movie;


    //if you dont declare static it wont be accessible in all functions
    private static final String str = "movie";

    public movieFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            movie = (HashMap<String, ?>) getArguments().getSerializable(str);
        }

        setHasOptionsMenu(true);
        setRetainInstance(true);
    }


    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState){
        View rootView = null;
        rootView = inflater.inflate(R.layout.fragment_movie_layout, container, false);
        ///////////////////////////////////////////////////////////////////////
       final ImageView imv1 = (ImageView) rootView.findViewById(R.id.imageId);
        final TextView tv1 = (TextView) rootView.findViewById(R.id.movieDescrId);
        final TextView tv2 = (TextView) rootView.findViewById(R.id.movieNameId);
        final TextView tv3 = (TextView) rootView.findViewById(R.id.starId);
        final TextView tv4 = (TextView) rootView.findViewById(R.id.yearId);
        final TextView ratingText = (TextView) rootView.findViewById(R.id.ratingText);
        final RatingBar ratingBar = (RatingBar) rootView.findViewById(R.id.indicator_ratingbar);
        //----------------------------------------------------//
       // Log.d("m1","________________________________________________________________________________________________");
        imv1.setImageResource((Integer) movie.get("image"));
        imv1.setTransitionName((String) movie.get("name")); //setname
       // Log.d("m2", "________________________________________________________________________________________________");
        tv1.setText((String) movie.get("description"));
       // tv1.setTransitionName((String) movie.get("name"));
        tv2.setText((String) movie.get("name"));
        tv3.setText((String) movie.get("stars"));
        tv4.setText((String) movie.get("year"));

        double ratingBarValue = (Double) movie.get("rating");
        float f = (float)ratingBarValue ;
        f=(f*10)/20;
        ratingBar.setRating(f); //Pass the float value
        //////////
        String message = String.format("%.1f", ratingBar.getRating());
        ratingText.setText(message);
        ///////////

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.share_actionview, menu);

        MenuItem shareItem = menu.findItem(R.id.action_share);
        ShareActionProvider shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);

        Intent intentShare = new Intent(Intent.ACTION_SEND);
        intentShare.setType("text/plain");
        intentShare.putExtra(Intent.EXTRA_TEXT, (String) movie.get("name"));
        shareActionProvider.setShareIntent(intentShare);

        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public static movieFragment newInstance(HashMap<String, ?> movie) {


        movieFragment mf = new movieFragment();
        Bundle args = new Bundle();
        args.putSerializable(str, movie);
        mf.setArguments(args);
        mf.setRetainInstance(true);
        return mf;
    }

}
