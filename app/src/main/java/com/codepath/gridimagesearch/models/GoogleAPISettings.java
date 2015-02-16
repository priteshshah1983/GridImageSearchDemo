package com.codepath.gridimagesearch.models;

import android.content.Context;

import com.codepath.gridimagesearch.R;

/**
 * This is the lightweight version of the Settings object passed to the API client
 */
public class GoogleAPISettings {

    private String mImageSize;
    private String mColorFilter;
    private String mImageType;
    private String mSiteFilter;

    public GoogleAPISettings(Settings settings, Context context) {
        if (settings != null) {
            String imageSize = context.getResources().getStringArray(R.array.image_sizes_array)[0];
            if (!settings.getImageSize().equals(imageSize)) {
                this.setImageSize(settings.getImageSize());
            }
            String colorFilter = context.getResources().getStringArray(R.array.color_filters_array)[0];
            if (!settings.getColorFilter().equals(colorFilter)) {
                this.setColorFilter(settings.getColorFilter());
            }
            String imageType = context.getResources().getStringArray(R.array.image_types_array)[0];
            if (!settings.getImageType().equals(imageType)) {
                this.setImageType(settings.getImageType());
            }
            this.setSiteFilter(settings.getSiteFilter());
        }
    }

    public String getImageSize() {
        return mImageSize;
    }

    public void setImageSize(String imageSize) {
        mImageSize = imageSize;
    }

    public String getColorFilter() {
        return mColorFilter;
    }

    public void setColorFilter(String colorFilter) {
        mColorFilter = colorFilter;
    }

    public String getImageType() {
        return mImageType;
    }

    public void setImageType(String imageType) {
        mImageType = imageType;
    }

    public String getSiteFilter() {
        return mSiteFilter;
    }

    public void setSiteFilter(String siteFilter) {
        mSiteFilter = siteFilter;
    }
}
