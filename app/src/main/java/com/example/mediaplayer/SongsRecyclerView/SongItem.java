package com.example.mediaplayer.SongsRecyclerView;/* Created by Shay Mualem 23/07/2021 */

import android.widget.ImageView;

public class SongItem {

    private String Url;
    private String Name;
    private String Duration;
    private ImageView imageView;

    public SongItem(String url, String name, String duration, ImageView imageView) {
        Url = url;
        if (name != null)
            Name = name;
        //Name = url.substring(url.lastIndexOf("/") + 1);

//        UrlValidator urlValidator = new UrlValidator();
//        urlValidator.isValid("http://my favorite site!");

        Name = name;
        Duration = duration;
        this.imageView = imageView;


    }

    public SongItem() {
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getImageView() {
        return 0;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }
}
