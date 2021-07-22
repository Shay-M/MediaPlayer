package com.example.mediaplayer;/* Created by Shay Mualem 22/07/2021 */

import android.util.Log;

import java.util.ArrayList;

public class ListSongsManager {


    private ArrayList<String> listOfSongs;
    private int currentPlaying = 0;


    //    ListSongsManager(ArrayList<String> listOfSongsGet) {
    ListSongsManager() {

        listOfSongs = new ArrayList<>();
//        listOfSongs = listOfSongsGet;
        listOfSongs.add("https://www.soundhelix.com/examples/mp3/SoundHelix-Song-7.mp3");
        listOfSongs.add("https://www.soundhelix.com/examples/mp3/SoundHelix-Song-15.mp3");
        listOfSongs.add("https://file-examples-com.github.io/uploads/2017/11/file_example_MP3_2MG.mp3");

    }

    public ArrayList<String> getListOfSongs() {
        return listOfSongs;
    }

    public void addSong(String string) {
        this.listOfSongs.add(string);
        Log.d("ListSongsManager", "addSong: " + listOfSongs);
    }

}
