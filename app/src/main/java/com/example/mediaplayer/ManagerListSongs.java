package com.example.mediaplayer;/* Created by Shay Mualem 22/07/2021 */

import android.util.Log;

import com.example.mediaplayer.SongsRecyclerView.SongItem;

import java.util.ArrayList;
import java.util.List;

public class ManagerListSongs {

    private ArrayList<String> listOfUrlSongs;
    private List<SongItem> listOfSongsItems;
//    private int currentPlaying = 0;

    //    ListSongsManager(ArrayList<String> listOfSongsGet) {
    ManagerListSongs() {
        listOfUrlSongs = new ArrayList<>();
        listOfUrlSongs.add("https://www.soundhelix.com/examples/mp3/SoundHelix-Song-7.mp3");
        listOfUrlSongs.add("https://www.soundhelix.com/examples/mp3/SoundHelix-Song-15.mp3");
        listOfUrlSongs.add("https://file-examples-com.github.io/uploads/2017/11/file_example_MP3_2MG.mp3");

        listOfSongsItems = new ArrayList<>();
        listOfSongsItems.add(new SongItem("null", this.getListOfUrlSongs().get(1), "1", null));


    }

    public void addSongItem() {
    }

    public List<SongItem> getListOfSongsItems() {
        return listOfSongsItems;
    }

    public ArrayList<String> getListOfUrlSongs() {
        return listOfUrlSongs;
    }

    public void addSong(String stringUrl) {

        String NameOfSongFromUrl = null;
        try {
            NameOfSongFromUrl = stringUrl.substring(stringUrl.lastIndexOf('/') + 1);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        UrlValidator urlValidator = new UrlValidator();
//        urlValidator.isValid("http://my favorite site!");

        this.listOfUrlSongs.add(stringUrl);
        Log.d("NameOfSongFromUrl", "addSong: " + NameOfSongFromUrl);



        //

//        UrlValidator urlValidator = new UrlValidator();
//        urlValidator.isValid("http://my favorite site!");
    }
}
