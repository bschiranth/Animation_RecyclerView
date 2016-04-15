package com.example.bschiranth.animation_hw10_chiranth;

import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;        //Get reference to toolbar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/

       //final Intent intent = new Intent(this, Task2Activity.class);
       final Intent intent;
        intent = new Intent(this, Task2Activity.class);
        Button button = (Button) findViewById(R.id.Button_id);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              ActivityOptionsCompat options = ActivityOptionsCompat
                        .makeSceneTransitionAnimation(MainActivity.this,v,"activity_transition");//shared name activity
                                startActivity(intent,options.toBundle());
               // startActivity(intent);
            }
        });
    }
}
