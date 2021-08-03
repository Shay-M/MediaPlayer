package com.example.mediaplayer.SongsRecyclerView;/* Created by Shay Mualem 23/07/2021 */

import android.net.Uri;

import java.io.Serializable;

public class SongItem implements Serializable {

    private String Url;
    private String Name;
    private Uri uri;

    public SongItem(String url, String name, Uri uri) {
        Url = url;
        Name = name;
        if (uri == null)
            uri = Uri.parse("file:///android_asset/musicxhdpi.png");
        this.uri = uri;

    }

    public SongItem() {
    }

    public Uri getUri() {
        return uri;
    }

    public String getUrl() {
        return Url;
    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }


}
