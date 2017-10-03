package com.android.example.iithplacement;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class Placement_Team_Activity extends AppCompatActivity {

    private int position = -1;
    private String mailString=" ";

    private View.OnClickListener onMailClickListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            String data = ((TextView) v).getText().toString();
            String email = data.substring(data.indexOf(mailString));
            intent.setData(Uri.parse("mailto:" + email));
            intent.putExtra(Intent.EXTRA_SUBJECT, "Query from xyz name regarding xyz");
            intent.putExtra(Intent.EXTRA_TEXT, "Respected Sir/Mam, \n \n \n Yours faithfully, \n xyz.");
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }
    };

    private View.OnClickListener onDailerClick =new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            String data = ((TextView) v).getText().toString();
            String no = data.substring(data.indexOf(mailString));
            intent.setData(Uri.parse("tel:" + no));
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placement__team_);

        TextView facultyEmail =(TextView) findViewById(R.id.director_designation);
        TextView facultyContact = (TextView) findViewById(R.id.faculty_in_charge_contact);

        TextView temMem1Email = (TextView) findViewById(R.id.mem1_email);
        TextView mem1Contact = (TextView) findViewById(R.id.mem1Contact);

        TextView temMem2Email = (TextView) findViewById(R.id.mem2_email);
        TextView mem2Contact = (TextView) findViewById(R.id.mem2Contact);

        TextView temMem3Email = (TextView) findViewById(R.id.mem3_email);
        TextView mem3Contact = (TextView) findViewById(R.id.mem3Contact);

        TextView temMem4Email = (TextView) findViewById(R.id.mem4_email);
        TextView mem4Contact = (TextView) findViewById(R.id.mem4Contact);

        facultyEmail.setOnClickListener(onMailClickListner);
        temMem1Email.setOnClickListener(onMailClickListner);
        temMem2Email.setOnClickListener(onMailClickListner);
        temMem3Email.setOnClickListener(onMailClickListner);
        temMem4Email.setOnClickListener(onMailClickListner);

        facultyContact.setOnClickListener(onDailerClick);
        mem1Contact.setOnClickListener(onDailerClick);
        mem4Contact.setOnClickListener(onDailerClick);
        mem3Contact.setOnClickListener(onDailerClick);
        mem2Contact.setOnClickListener(onDailerClick);



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
                        Animation anim_in = AnimationUtils.loadAnimation(Placement_Team_Activity.this, R.anim.fade_in);
                        Animation anim_out= AnimationUtils.loadAnimation(Placement_Team_Activity.this, R.anim.fade_out);

                        view.setBackgroundResource(imageArray[position]);
                        view.setAnimation(anim_in);



                    }
                });
            }
        }, 0, 6000);
    }
}
