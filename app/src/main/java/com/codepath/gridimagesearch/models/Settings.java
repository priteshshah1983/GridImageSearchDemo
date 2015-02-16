package com.codepath.gridimagesearch.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Settings implements Parcelable {

    private String mImageSize;
    private String mColorFilter;
    private String mImageType;
    private String mSiteFilter;

    @Override
    public String toString() {
        String separator = ", ";
        return "Image Size" + " : " + this.getImageSize() + separator + "Color Filter" + " : " + this.getColorFilter() + separator + "Image Type" + " : " + this.getImageType() + separator + "Site Filter" + " : " + this.getSiteFilter();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mImageSize);
        dest.writeString(this.mColorFilter);
        dest.writeString(this.mImageType);
        dest.writeString(this.mSiteFilter);
    }

    public Settings() {
    }

    private Settings(Parcel in) {
        this.mImageSize = in.readString();
        this.mColorFilter = in.readString();
        this.mImageType = in.readString();
        this.mSiteFilter = in.readString();
    }

    public static final Parcelable.Creator<Settings> CREATOR = new Parcelable.Creator<Settings>() {
        public Settings createFromParcel(Parcel source) {
            return new Settings(source);
        }

        public Settings[] newArray(int size) {
            return new Settings[size];
        }
    };
}
