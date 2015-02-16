package com.codepath.gridimagesearch.net;


import com.codepath.gridimagesearch.models.GoogleAPISettings;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class GoogleImageSearchAPIClient {
    private static final String API_BASE_URL = "https://ajax.googleapis.com/ajax/services/search/";
    private static final String API_VERSION = "1.0";
    // We are currently only getting 8 images at a time!
    private static final int API_DEFAULT_PAGE_SIZE = 8;


    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        // Default parameters
        if (params == null) {
            params = new RequestParams();
        }
        params.put("v", API_VERSION);
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return API_BASE_URL + relativeUrl;
    }

    // Method for accessing the search API
    public static void getImages(final GoogleAPISettings apiSettings, final String query, final int start, JsonHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("v", API_VERSION);
        params.put("rsz", API_DEFAULT_PAGE_SIZE);
        params.put("start", start);

        if (apiSettings.getImageType() != null) {
            params.put("imgtype", apiSettings.getImageType());
        }
        if (apiSettings.getColorFilter() != null) {
            params.put("imgcolor", apiSettings.getColorFilter());
        }
        if (apiSettings.getImageSize() != null) {
            params.put("imgsz", apiSettings.getImageSize());
        }
        if (apiSettings.getSiteFilter() != null) {
            params.put("as_sitesearch", apiSettings.getSiteFilter());
        }
        try {
            String url = getAbsoluteUrl("images");
            params.put("q", URLEncoder.encode(query, "utf-8"));
            client.get(url, params, handler);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}

