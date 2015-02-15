package com.codepath.gridimagesearch.models;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ImageResult {
    private String mFullUrl;
    private String mThumbUrl;
    private String mTitle;

    public String getThumbUrl() {
        return mThumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        mThumbUrl = thumbUrl;
    }

    public String getFullUrl() {
        return mFullUrl;
    }

    public void setFullUrl(String fullUrl) {
        mFullUrl = fullUrl;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public static ImageResult fromJson(JSONObject photoJSON) {
        ImageResult imageResult = new ImageResult();
        imageResult.setFullUrl(photoJSON.optString("url"));
        imageResult.setThumbUrl(photoJSON.optString("tbUrl"));
        imageResult.setTitle(photoJSON.optString("title"));

        return imageResult;
    }

    public static ArrayList<ImageResult> fromJson(JSONArray jsonArray) {
        ArrayList<ImageResult> imageResults = new ArrayList<>(jsonArray.length());
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject photoJSON = null;
            try {
                photoJSON = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            ImageResult photo = ImageResult.fromJson(photoJSON);
            if (photo != null) {
                imageResults.add(photo);
            }
        }

        return imageResults;
    }
}
