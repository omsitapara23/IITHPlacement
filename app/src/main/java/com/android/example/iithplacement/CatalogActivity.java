package com.android.example.iithplacement;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.Layout;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

import static com.android.example.iithplacement.R.id.fab;

public class CatalogActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Uri uri;
    String IITHWebsite="https://iith-placement-cell.000webhostapp.com/";
    private int position = -1;
    private int aplhaImage = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);
        /*ImageView imageView = (ImageView) findViewById(R.id.catalogImage);
        imageView.setImageResource(R.drawable.front_catalog);
        imageView.setAdjustViewBounds(true);*/
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final View view = (View) findViewById(R.id.mainRelativeBackground);
        int orientation = getResources().getConfiguration().orientation;
        if(orientation == Configuration.ORIENTATION_LANDSCAPE) {
            view.setBackgroundResource (R.drawable.front_catalog);
        } else {
            view.setBackgroundResource (R.drawable.iith4);
        }

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
                        Animation anim_in = AnimationUtils.loadAnimation(CatalogActivity.this, R.anim.fade_in);
                        Animation anim_out= AnimationUtils.loadAnimation(CatalogActivity.this, R.anim.fade_out);

                        view.setBackgroundResource(imageArray[position]);
                        view.setAnimation(anim_in);



                    }
                });
            }
        }, 0, 6000);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uri = Uri.parse(IITHWebsite);
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);


            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
