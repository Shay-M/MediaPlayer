package com.example.mediaplayer.SongsRecyclerView;/* Created by Shay Mualem 23/07/2021 */

import java.io.Serializable;

public class SongItem implements Serializable {

    private String songDuration;
    private String url;
    private String name;
    private String uri;

//    public SongItem(String url, String name, String uri) {
//        this.url = url;
//        this.name = name;
//        if (uri == null)
//            uri = ("file:///android_asset/musicxhdpi.png"); //Uri.parse
//        this.uri = uri;
//
//    }

    public SongItem() {
    }

    public SongItem(String stringUrl, String songName, String imgUri, String songDuration) {
        this.url = stringUrl;
        this.name = songName;
        if (uri == null)
            uri = ("file:///android_asset/musicxhdpi.png"); //Uri.parse
        this.uri = imgUri;
        this.songDuration = songDuration;
    }

    public String getUri() {
        return uri;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getSongDuration() {
        return songDuration;
    }
}
