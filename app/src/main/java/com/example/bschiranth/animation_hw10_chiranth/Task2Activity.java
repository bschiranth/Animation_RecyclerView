package com.example.bschiranth.animation_hw10_chiranth;

//import android.app.ActionBar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.ChangeTransform;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.TransitionSet;
import android.util.Log;
import android.view.View;

import java.util.HashMap;

public class Task2Activity extends AppCompatActivity implements recyclerViewFragment.tickCheckBox {
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task2_layout);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportFragmentManager().beginTransaction().replace(
                R.id.replace_container,recyclerViewFragment.newInstance(R.id.rv1)).commit();
    }

///////////////////////..

    @Override
    public void puttick(MovieData md,HashMap movie) {
        for(int i=0;i<md.getSize();i++){
            movie = md.getItem(i);
            movie.put("selection",true);
        }
    }

    @Override
    public void cleartick(MovieData md,HashMap movie) {
        for (int i = 0; i < md.getSize(); i++) {
            movie = md.getItem(i);
            movie.put("selection", false);
        }
    }



    @Override
    public void loadMovie(int position, HashMap movie,View sharedImage) {
        movieFragment details = movieFragment.newInstance(movie);
        Log.d("m1", "________________________________________________________________________________________________");
        details.setSharedElementEnterTransition(new DetailsTransition());
        Log.d("m2", "________________________________________________________________________________________________");
        details.setEnterTransition(new Fade());
        Log.d("m3", "________________________________________________________________________________________________");
        details.setExitTransition(new Fade());
        Log.d("m4", "________________________________________________________________________________________________");
        details.setSharedElementReturnTransition(new DetailsTransition());
        Log.d("m5", "________________________________________________________________________________________________");
                //sharedImage is a view common to fragments for sahred element transition
                //  Log.d("chgchvhgv","kjhkdjchskjdfhkjdhfkwjhfkewrhf");
                getSupportFragmentManager().beginTransaction()
                        .addSharedElement(sharedImage, sharedImage.getTransitionName())//does fragment animation
                        .replace(R.id.replace_container, details).addToBackStack(null)
                        .commit();
    }

    public class DetailsTransition extends TransitionSet{
        public DetailsTransition(){
            setOrdering(ORDERING_TOGETHER); //A flag used to indicate that the child transitions of this set should all start at the same time.
            addTransition(new ChangeBounds()).addTransition(new ChangeTransform()).addTransition(new ChangeImageTransform());
        }
    }

}
