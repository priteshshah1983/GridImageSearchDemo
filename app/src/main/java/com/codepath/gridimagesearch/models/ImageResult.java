package com.codepath.gridimagesearch.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ImageResult implements Parcelable {

    private String mFullUrl;
    private String mThumbUrl;
    private String mTitle;
    private int mThumbWidth;
    private int mThumbHeight;

    public int getThumbHeight() {
        return mThumbHeight;
    }

    public void setThumbHeight(int thumbHeight) {
        mThumbHeight = thumbHeight;
    }

    public int getThumbWidth() {
        return mThumbWidth;
    }

    public void setThumbWidth(int thumbWidth) {
        mThumbWidth = thumbWidth;
    }

    @Override
    public String toString() {
        String separator = ", ";
        return "Full URL" + " : " + this.getFullUrl() + separator + "Thumb URL" + " : " + this.getThumbUrl() + separator + "Title" + " : " + this.getTitle() + separator + "Thumb Width" + " : " + this.getThumbWidth() + separator + this.getTitle() + separator + "Thumb Height" + " : " + this.getThumbHeight();
    }

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
        String width = photoJSON.optString("tbWidth");
        if (width != null) {
            imageResult.setThumbWidth(Integer.parseInt(width));
        }
        String height = photoJSON.optString("tbHeight");
        if (height != null) {
            imageResult.setThumbHeight(Integer.parseInt(height));
        }
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mFullUrl);
        dest.writeString(this.mThumbUrl);
        dest.writeString(this.mTitle);
    }

    public ImageResult() {
    }

    private ImageResult(Parcel in) {
        this.mFullUrl = in.readString();
        this.mThumbUrl = in.readString();
        this.mTitle = in.readString();
    }

    public static final Parcelable.Creator<ImageResult> CREATOR = new Parcelable.Creator<ImageResult>() {
        public ImageResult createFromParcel(Parcel source) {
            return new ImageResult(source);
        }

        public ImageResult[] newArray(int size) {
            return new ImageResult[size];
        }
    };
}
