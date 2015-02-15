package com.codepath.gridimagesearch.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.codepath.gridimagesearch.R;
import com.codepath.gridimagesearch.models.ImageResult;
import com.codepath.gridimagesearch.utilities.TouchImageView;
import com.squareup.picasso.Picasso;


public class ImageDisplayActivity extends ActionBarActivity {

    public static final String EXTRA_RESULT = "com.codepath.gridimagesearch.result";

    private TouchImageView ivImageResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_display);
        // Hide action bar
        getSupportActionBar().hide();
        ivImageResult = (TouchImageView) findViewById(R.id.ivImageResult);
        ImageResult imageResult = getIntent().getParcelableExtra(EXTRA_RESULT);
        Picasso.with(this).load(imageResult.getFullUrl()).into(ivImageResult);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_image_display, menu);
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
}
