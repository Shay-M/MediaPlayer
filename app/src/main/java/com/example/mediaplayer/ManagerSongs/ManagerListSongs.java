package com.example.mediaplayer.ManagerSongs;/* Created by Shay Mualem 22/07/2021 */

import android.os.Parcel;
import android.util.Log;

import com.example.mediaplayer.ActionsMediaPlayer.ActionsPlayer;
import com.example.mediaplayer.MusicPlayerService;
import com.example.mediaplayer.SongsRecyclerView.SongItem;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ManagerListSongs implements ActionsPlayer {


    private static ManagerListSongs instead;
    private final ArrayList<String> listOfUrlSongs;
    private List<SongItem> listOfSongsItems;
    private MusicPlayerService musicPlayerService;
    private int currentPlaying;

    private ManagerListSongs() {
        currentPlaying = 0; // todo
        listOfUrlSongs = new ArrayList<>();
        ArrayList<String> listOfUrlSongsToAddFirstTime = new ArrayList<>();
        listOfSongsItems = new ArrayList<>();


        listOfUrlSongsToAddFirstTime.add("https://www.soundhelix.com/examples/mp3/SoundHelix-Song-7.mp3");
        listOfUrlSongsToAddFirstTime.add("https://www.soundhelix.com/examples/mp3/SoundHelix-Song-15.mp3");
        listOfUrlSongsToAddFirstTime.add("https://file-examples-com.github.io/uploads/2017/11/file_example_MP3_2MG.mp3");

        for (String songUrl : listOfUrlSongsToAddFirstTime) {
            try {
                this.addSong(songUrl);
            } catch (Exception e) {
                Log.d("ManagerListSongs", "songUrl: " + e.getMessage() + " songUrl: " + songUrl);
            }
//            Set<String> noDuplicatesDlistOfUrlSongs = new LinkedHashSet<>(listOfUrlSongs);
//            https://stackoverflow.com/questions/203984/how-do-i-remove-repeated-elements-from-arraylist
//            Log.d(">>>>>>", ">>>songUrl: " + songUrl);

//            todo get from sd  list and  currentPlaying = 0;
        }

    }

    protected ManagerListSongs(Parcel in) {
        listOfUrlSongs = in.createStringArrayList();
    }

    public static ManagerListSongs getInstance() {
        if (instead == null)
            instead = new ManagerListSongs();
        return instead;
    }

    public int getCurrentPlaying(boolean next_p) {

        if (next_p) {
            currentPlaying++;
            if (currentPlaying == listOfUrlSongs.size())
                currentPlaying = 0;
        } else {
            currentPlaying--;
            if (currentPlaying < 0)
                currentPlaying = listOfUrlSongs.size() - 1;

        }

        return currentPlaying;
    }

    public List<SongItem> getListOfSongsItems() {
        return listOfSongsItems;
    }

    public ArrayList<String> getListOfUrlSongs() {
        Log.d("ManagerListSongs", "addSong: " + listOfUrlSongs);
        return listOfUrlSongs;
    }

    /**
     * @param stringUrl song url to add
     * @throws Exception if the url is not in a valid
     */
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
            listOfUrlSongs.add(stringUrl);
            Log.d("ManagerListSongs", "addSong: " + listOfUrlSongs);
            listOfSongsItems.add(new SongItem(stringUrl, NameOfSongFromUrl, "1", null));

        } else throw new Exception("the URL is not in a valid form: " + "Unsupported file");

    }


    @Override
    public void nextClick() {

    }

    @Override
    public void prevClick() {

    }

    @Override
    public void playClick() {

    }

    @Override
    public void pauseClick() {

    }
}


