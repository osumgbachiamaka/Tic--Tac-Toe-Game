package com.example.chiamaka.tic;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Chiamaka on 06/04/2018.
 */

public class Mode extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.mode);

        ImageView buttonAI =(ImageView)findViewById(R.id.ai);
        buttonAI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Mode.this, SecondScreen.class);
                intent.putExtra("gameType", true);
                startActivity(intent);
            }
        });

        ImageView buttonFriend =(ImageView)findViewById(R.id.friend);
        buttonFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Mode.this, SecondScreen.class);
                intent.putExtra("gameType", false);
                startActivity(intent);
            }
        });
    }

}
