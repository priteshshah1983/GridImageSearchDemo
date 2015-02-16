package com.codepath.gridimagesearch.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.codepath.gridimagesearch.R;
import com.codepath.gridimagesearch.adapters.EndlessScrollListener;
import com.codepath.gridimagesearch.adapters.ImageResultsAdapter;
import com.codepath.gridimagesearch.fragments.EditSettingsFragment;
import com.codepath.gridimagesearch.models.GoogleAPISettings;
import com.codepath.gridimagesearch.models.ImageResult;
import com.codepath.gridimagesearch.models.Settings;
import com.codepath.gridimagesearch.net.GoogleImageSearchAPIClient;
import com.etsy.android.grid.StaggeredGridView;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class SearchActivity extends ActionBarActivity implements EditSettingsFragment.EditSettingsDialogListener {

    public static final String SETTINGS = "com.codepath.gridimagesearch.settings";

    private static final String TAG = "SearchActivity";
    private static final String SETTING_IMAGE_SIZE = "com.codepath.gridimagesearch.settings.image_size";
    private static final String SETTING_COLOR_FILTER = "com.codepath.gridimagesearch.settings.color_filter";
    private static final String SETTING_IMAGE_TYPE = "com.codepath.gridimagesearch.settings.image_filter";
    private static final String SETTING_SITE_FILTER = "com.codepath.gridimagesearch.settings.site_filter";

    private StaggeredGridView gvResults;
    private ArrayList<ImageResult> imageResults;
    private ImageResultsAdapter aImageResults;
    private Settings settings;

    private SharedPreferences mSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        loadLastKnownSettings();
        setupViews();

        imageResults = new ArrayList<>();
        aImageResults = new ImageResultsAdapter(this, imageResults);
        gvResults.setAdapter(aImageResults);
    }

    private void showEditSettingsDialog() {
        FragmentManager fm = getSupportFragmentManager();
        // TODO: Hardcoded string
        EditSettingsFragment editSettingsDialog = EditSettingsFragment.newInstance(settings, "Advanced Filters");
        editSettingsDialog.show(fm, "fragment_edit_settings");
    }

    private void notifyUserAboutNoInternetConnectivity() {
        notifyUserAboutGenericError(R.string.no_internet_connection_label);
    }

    private void notifyUserAboutAPIError() {
        notifyUserAboutGenericError(R.string.api_failure_label);
    }

    private void notifyUserAboutGenericError(int textId) {
        new AlertDialog.Builder(this).setTitle(textId)
                .setNeutralButton(R.string.ok_label,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
    }

    private void setupViews() {
//        etQuery = (EditText) findViewById(R.id.etQuery);
        gvResults = (StaggeredGridView) findViewById(R.id.gvResults);
        gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(SearchActivity.this, ImageDisplayActivity.class);
                ImageResult imageResult = imageResults.get(position);
                i.putExtra(ImageDisplayActivity.EXTRA_RESULT, imageResult);
                startActivity(i);
            }
        });
        // Attach the listener to the AdapterView onCreate
        gvResults.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                customLoadMoreDataFromApi(page);
                // or customLoadMoreDataFromApi(totalItemsCount);
            }
        });
    }

    private void customLoadMoreDataFromApi(int offset) {
        int start = (offset - 1) * 8;
//        int start = (offset) * 8;
        Log.d(TAG, "start = " + start);
        if (start <= 56) {
            getImages(start);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // perform query here
                getImages(0);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            showEditSettingsDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onImageSearch(View view) {
        getImages(0);
    }

    private void getImages(final int start) {
        if (!isNetworkAvailable()) {
            notifyUserAboutNoInternetConnectivity();
        } else {
            SearchView searchView = (SearchView) findViewById(R.id.action_search);
//            String query = etQuery.getText().toString();
            String query = searchView.getQuery().toString();
            Log.d(TAG, "searching for " + query);

            String s = getResources().getStringArray(R.array.image_sizes_array)[0];

            GoogleAPISettings apiSettings = new GoogleAPISettings(settings, this);
            GoogleImageSearchAPIClient.getImages(apiSettings, query, start, new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    JSONObject responseDataJSON = response.optJSONObject("responseData");
                    if (responseDataJSON != null) {
                        JSONArray resultsJSON = responseDataJSON.optJSONArray("results");
                        if (resultsJSON != null) {
                            if (start == 0) {
                                Log.d(TAG, "Clearing list");
                                aImageResults.clear();
                            }
                            aImageResults.addAll(ImageResult.fromJson(resultsJSON));
                            Log.d(TAG, "count = " + imageResults.size());
                        }
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Log.e(TAG, "Failed to call API: " + throwable);
                    notifyUserAboutAPIError();
                }
            });
        }
    }

    private Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    @Override
    public void onFinishEditDialog(Settings settings) {
        Log.d(TAG, "Updated settings = " + settings);
        persistSettingsPermanently(settings);
        this.settings = settings;
    }

    // TODO: Saving settings is not an activity responsibility
    // Move it out to a model class
    private void persistSettingsPermanently(Settings settings) {
        // Save to shared preferences
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString(SETTING_IMAGE_SIZE, settings.getImageSize());
        editor.putString(SETTING_COLOR_FILTER, settings.getColorFilter());
        editor.putString(SETTING_IMAGE_TYPE, settings.getImageType());
        editor.putString(SETTING_SITE_FILTER, settings.getSiteFilter());

        // http://stackoverflow.com/a/5960732/400552
        editor.apply();
    }

    private void loadLastKnownSettings() {
        mSettings = getSharedPreferences(SETTINGS, 0);
        String imageSize = mSettings.getString(SETTING_IMAGE_SIZE, null);
        String colorFilter = mSettings.getString(SETTING_COLOR_FILTER, null);
        String imageType = mSettings.getString(SETTING_IMAGE_TYPE, null);
        String siteFilter = mSettings.getString(SETTING_SITE_FILTER, null);
        if (imageSize != null || colorFilter != null || imageType != null || siteFilter != null) {
            // Create a new Settings object
            settings = new Settings();
            settings.setImageSize(imageSize);
            settings.setColorFilter(colorFilter);
            settings.setImageType(imageType);
            settings.setSiteFilter(siteFilter);
        }
    }
}
