package com.android.example.iithplacement;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.util.Timer;
import java.util.TimerTask;

public class CreditsActivity extends AppCompatActivity {

    private int position = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);

        final View view = (View) findViewById(R.id.mainRelativeBackground);
        final int[] imageArray = {R.drawable.iith4, R.drawable.iith5, R.drawable.iith6};

        Timer mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                // As timer is not a Main/UI thread need to do all UI task on runOnUiThread
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // increase your position so new image will show
                        position++;
                        // check whether position increased to length then set it to 0
                        // so it will show images in circuler
                        if (position >= imageArray.length)
                            position = 0;
                        // Set Image
                        Animation anim_in = AnimationUtils.loadAnimation(CreditsActivity.this, R.anim.fade_in);
                        Animation anim_out= AnimationUtils.loadAnimation(CreditsActivity.this, R.anim.fade_out);

                        view.setBackgroundResource(imageArray[position]);
                        view.setAnimation(anim_in);



                    }
                });
            }
        }, 0, 6000);
    }

}
