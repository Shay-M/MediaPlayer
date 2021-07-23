package com.example.mediaplayer;/* Created by Shay Mualem 22/07/2021 */

import android.util.Log;

import com.example.mediaplayer.SongsRecyclerView.SongItem;

import java.util.ArrayList;
import java.util.List;

public class ManagerListSongs {


    private ArrayList<String> listOfUrlSongs;
    private List<SongItem> listOfSongsItems;
    private int currentPlaying = 0;
    //    ListSongsManager(ArrayList<String> listOfSongsGet) {
    ManagerListSongs() {

        listOfUrlSongs = new ArrayList<>();
//        listOfSongs = listOfSongsGet;
        listOfUrlSongs.add("https://www.soundhelix.com/examples/mp3/SoundHelix-Song-7.mp3");
        listOfUrlSongs.add("https://www.soundhelix.com/examples/mp3/SoundHelix-Song-15.mp3");
        listOfUrlSongs.add("https://file-examples-com.github.io/uploads/2017/11/file_example_MP3_2MG.mp3");

        listOfSongsItems = new ArrayList<>();

//        ManagerListSongs managerListSongs = new ManagerListSongs();
        listOfSongsItems.add(new SongItem("null", this.getListOfUrlSongs().get(1), "1", null));
//        listOfSongsItems.add(new SongItem("null", managerListSongs.getListOfUrlSongs().get(0), "2", null));
//        listOfSongsItems.add(new SongItem("null", managerListSongs.getListOfUrlSongs().get(1), "1", null));
//        listOfSongsItems.add(new SongItem("null", managerListSongs.getListOfUrlSongs().get(0), "2", null));
//        listOfSongsItems.add(new SongItem("null", managerListSongs.getListOfUrlSongs().get(1), "1", null));
//        listOfSongsItems.add(new SongItem("null", managerListSongs.getListOfUrlSongs().get(0), "2", null));
//        listOfSongsItems.add(new SongItem("null", managerListSongs.getListOfUrlSongs().get(1), "1", null));
//        listOfSongsItems.add(new SongItem("null", managerListSongs.getListOfUrlSongs().get(0), "2", null));


    }

    public List<SongItem> getListOfSongsItems() {
        return listOfSongsItems;
    }

    public ArrayList<String> getListOfUrlSongs() {
        return listOfUrlSongs;
    }

    public void addSong(String string) {
        this.listOfUrlSongs.add(string);
        Log.d("ListSongsManager", "addSong: " + listOfUrlSongs);
    }

}
