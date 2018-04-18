package com.example.chiamaka.tic;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Chiamaka on 04/04/2018.
 */

public class SecondScreen extends AppCompatActivity {
    boolean mGameType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.secondscreen);

        mGameType = getIntent().getExtras().getBoolean("gameType");

        //setting onclick listener on the easy button
        Button buttonEasy = (Button) findViewById(R.id.buttonEasy);
        buttonEasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SecondScreen.this, PlayingEasy.class);
                startActivity(intent);
            }
        });
        //setting onclick listener on the hard button
        Button buttonHard = (Button) findViewById(R.id.buttonHard);
        buttonHard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SecondScreen.this, PlayingHard.class);
                intent.putExtra("gameType", mGameType);
                startActivity(intent);
            }
        });
    }
}
