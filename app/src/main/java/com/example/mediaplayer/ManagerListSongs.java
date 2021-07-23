package com.example.mediaplayer;/* Created by Shay Mualem 22/07/2021 */

import android.util.Log;

import com.example.mediaplayer.SongsRecyclerView.SongItem;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ManagerListSongs {

    private ArrayList<String> listOfUrlSongs;
    private List<SongItem> listOfSongsItems;
//    private int currentPlaying = 0;

    //    ListSongsManager(ArrayList<String> listOfSongsGet) {
    ManagerListSongs() {
        listOfUrlSongs = new ArrayList<>();
//        listOfUrlSongs.add("https://www.soundhelix.com/examples/mp3/SoundHelix-Song-7.mp3");
//        listOfUrlSongs.add("https://www.soundhelix.com/examples/mp3/SoundHelix-Song-15.mp3");
//        listOfUrlSongs.add("https://file-examples-com.github.io/uploads/2017/11/file_example_MP3_2MG.mp3");

        try {
            this.addSong("https://www.soundhelix.com/examples/mp3/SoundHelix-Song-7.mp3");
        } catch (Exception e) {
            e.printStackTrace();
        }

        listOfSongsItems = new ArrayList<>();
//        listOfSongsItems.add(new SongItem("null", "", "1", null));

    }


    public List<SongItem> getListOfSongsItems() {
        return listOfSongsItems;
    }

    public ArrayList<String> getListOfUrlSongs() {
        return listOfUrlSongs;
    }

    public void addSong(String stringUrl) throws Exception {
        //get song name
        String NameOfSongFromUrl = stringUrl.substring(stringUrl.lastIndexOf('/') + 1);
        // Url Validator
        try {
            new URL(stringUrl).toURI();
        } catch (MalformedURLException | URISyntaxException e) {
            Log.d("addSong", "the URL is not in a valid form: " + e.getMessage());
            NameOfSongFromUrl = "";
            throw new Exception("the URL is not in a valid form. " + e.getMessage());
        }

        if (!NameOfSongFromUrl.isEmpty() && NameOfSongFromUrl.contains(".")) {
            this.listOfUrlSongs.add(stringUrl);
            Log.d("ManagerListSongs", "addSong: " + stringUrl);
            listOfSongsItems.add(new SongItem(stringUrl, NameOfSongFromUrl, "1", null));
        } else throw new Exception("the URL is not in a valid form: " + "Unsupported file");

        /*try {
            NameOfSongFromUrl = stringUrl.substring(stringUrl.lastIndexOf('/') + 1);
            URL url = new URL(stringUrl);
            URLConnection conn = url.openConnection();
            conn.connect();
        } catch (MalformedURLException e) {
            Log.d("addSong", "the URL is not in a valid form. " + e.getMessage());
            NameOfSongFromUrl = "";
            //t
        } catch (IOException e) {
            Log.d("addSong", "the connection couldn't be established. " + e.getMessage());
            NameOfSongFromUrl = "";
            throw new Exception("the connection couldn't be established. " + e.getMessage());
        }*/
    }
}
