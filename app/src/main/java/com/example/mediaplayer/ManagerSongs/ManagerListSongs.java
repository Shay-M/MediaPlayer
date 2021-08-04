package com.example.mediaplayer.ManagerSongs;/* Created by Shay Mualem 22/07/2021 */

import static com.example.mediaplayer.ManagerSongs.ManagerSaveSongs.saveSongList;

import android.os.Parcel;
import android.util.Log;

import com.example.mediaplayer.SongsRecyclerView.SongItem;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

public class ManagerListSongs {


    private static ManagerListSongs instead;
    private final ArrayList<String> listOfUrlSongs;
    private ArrayList<SongItem> listOfSongsItems;
    private int currentPlaying;
    private RecyclerViewUpdateUIListener listener;
    private Boolean FirstTime = false;


    private ManagerListSongs() {
        currentPlaying = 0;
        listOfUrlSongs = new ArrayList<>();
        listOfSongsItems = new ArrayList<>();

        try {
            listOfSongsItems = ManagerSaveSongs.readSongList(null);

        } catch (Exception e) {
            FirstTime = true;
            Log.d("ManagerListSongs", "ManagerSaveSongs: " + e.getMessage());

            ArrayList<String> listOfUrlSongsToAddFirstTime = new ArrayList<>();

            listOfUrlSongsToAddFirstTime.add("https://www.syntax.org.il/xtra/bob2.mp3");
            listOfUrlSongsToAddFirstTime.add("https://www.syntax.org.il/xtra/bob1.m4a");
            listOfUrlSongsToAddFirstTime.add("https://www.syntax.org.il/xtra/bob.m4a");

            for (String songUrl : listOfUrlSongsToAddFirstTime) {
                try {
                    this.addSong(songUrl, null);//todo
                } catch (Exception ee) {
                    Log.d("ManagerListSongs", "songUrl: " + ee.getMessage() + " songUrl: " + songUrl);
                }

            }

        }
        if (!FirstTime)
            for (SongItem songItm : listOfSongsItems) {
                listOfUrlSongs.add(songItm.getUrl());
            }

    }

    protected ManagerListSongs(Parcel in) {
        listOfUrlSongs = in.createStringArrayList();
    }

    //singleton
    public static ManagerListSongs getInstance() {
        if (instead == null)
            instead = new ManagerListSongs();
        return instead;
    }

    public void setCurrentPlaying(int currentPlaying) {
        this.currentPlaying = currentPlaying;
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

    public ArrayList<SongItem> getListOfSongsItems() {
        return listOfSongsItems;
    }

    public ArrayList<String> getListOfUrlSongs() {
        return listOfUrlSongs;
    }

    public void SaveSongList() {
        saveSongList(null, listOfSongsItems);
    }

    public void RemovingSongFromList(int position) {
        listOfSongsItems.remove(position);
        listOfUrlSongs.remove(position);
        saveSongList(null, listOfSongsItems);
    }

    /**
     * @param stringUrl song url to add
     * @throws Exception if the url is not in a valid
     */
    public void addSong(String stringUrl, String imgUri) throws Exception {
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

            listOfSongsItems.add(new SongItem(stringUrl, NameOfSongFromUrl, imgUri));
            listener.onUpdateListItem();
        } else throw new Exception("the URL is not in a valid form: " + "Unsupported file");
        SaveSongList();
    }

    public void setListener(RecyclerViewUpdateUIListener listener) {

        this.listener = listener;
    }

    public interface RecyclerViewUpdateUIListener {
        //        void onInsertItem(int position, View view);
        void onUpdateListItem();
    }


}


//  Set<String> noDuplicatesDlistOfUrlSongs = new LinkedHashSet<>(listOfUrlSongs);
//            https://stackoverflow.com/questions/203984/how-do-i-remove-repeated-elements-from-arraylist
//            Log.d(">>>>>>", ">>>songUrl: " + songUrl);

