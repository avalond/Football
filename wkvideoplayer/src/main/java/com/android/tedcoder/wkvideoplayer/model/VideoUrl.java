

package com.android.tedcoder.wkvideoplayer.model;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class VideoUrl implements Parcelable {
    private String mFormatName;//视频格式名称，例如高清，标清，720P等等
    private ArrayList<String> mFormatUrls;//视频Url
    private boolean isOnlineVideo = true;//是否在线视频 默认在线视频

    private Bundle bundle=new Bundle();
    public String getFormatName() {
        return mFormatName;
    }

    public void setFormatName(String formatName) {
        mFormatName = formatName;
    }

    public ArrayList<String> getFormatUrls() {
        return mFormatUrls;
    }

    public void setFormatUrls( ArrayList<String> formatUrl) {
        mFormatUrls = formatUrl;
        bundle.putSerializable("list",mFormatUrls);
    }

    public boolean isOnlineVideo() {
        return isOnlineVideo;
    }

    public void setIsOnlineVideo(boolean isOnlineVideo) {
        this.isOnlineVideo = isOnlineVideo;
    }

    public boolean equal(VideoUrl url) {
        if (null != url) {
            return getFormatName().equals(url.getFormatName()) && getFormatUrls().equals(url.getFormatUrls());
        }
        return false;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    public VideoUrl() {
    }

    public VideoUrl(Parcel source) {
        mFormatName = source.readString();
        bundle = source.readBundle();
        mFormatUrls = bundle.getStringArrayList("list");
        isOnlineVideo = source.readInt() == 1;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mFormatName);
        dest.writeBundle(bundle);
        dest.writeInt(isOnlineVideo ? 1 : 0);
    }


    public static final Parcelable.Creator<VideoUrl> CREATOR = new Parcelable.Creator<VideoUrl>() {

        @Override
        public VideoUrl[] newArray(int size) {
            return new VideoUrl[size];
        }

        @Override
        public VideoUrl createFromParcel(Parcel source) {
            return new VideoUrl(source);
        }
    };
}
