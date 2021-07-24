package com.example.mediaplayer.ManagerSongs;/* Created by Shay Mualem 22/07/2021 */

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.example.mediaplayer.MusicPlayerService;
import com.example.mediaplayer.SongsRecyclerView.SongItem;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

//public class ManagerListSongs {
public class ManagerListSongs implements Parcelable {

    private final ArrayList<String> listOfUrlSongs;
    private  List<SongItem> listOfSongsItems;
    private MusicPlayerService musicPlayerService;
//    private int currentPlaying = 0;

    //    ListSongsManager(ArrayList<String> listOfSongsGet) {
    public ManagerListSongs() {
        listOfUrlSongs = new ArrayList<>();
        ArrayList<String> listOfUrlSongsToAddBrfore = new ArrayList<>();
        listOfSongsItems = new ArrayList<>();
        //musicPlayerService = new MusicPlayerService();


        listOfUrlSongsToAddBrfore.add("https://www.soundhelix.com/examples/mp3/SoundHelix-Song-7.mp3");
        listOfUrlSongsToAddBrfore.add("https://www.soundhelix.com/examples/mp3/SoundHelix-Song-15.mp3");
        listOfUrlSongsToAddBrfore.add("https://file-examples-com.github.io/uploads/2017/11/file_example_MP3_2MG.mp3");

        for (String songUrl : listOfUrlSongsToAddBrfore) {
            try {
                this.addSong(songUrl);
            } catch (Exception e) {
                Log.d("ManagerListSongs", "songUrl: " + e.getMessage() + " songUrl: " + songUrl);
            }
//            Set<String> noDuplicatesDlistOfUrlSongs = new LinkedHashSet<>(listOfUrlSongs);
//            https://stackoverflow.com/questions/203984/how-do-i-remove-repeated-elements-from-arraylist
//            Log.d(">>>>>>", ">>>songUrl: " + songUrl);
        }

    }

    protected ManagerListSongs(Parcel in) {
        listOfUrlSongs = in.createStringArrayList();
    }

    public static final Creator<ManagerListSongs> CREATOR = new Creator<ManagerListSongs>() {
        @Override
        public ManagerListSongs createFromParcel(Parcel in) {
            return new ManagerListSongs(in);
        }

        @Override
        public ManagerListSongs[] newArray(int size) {
            return new ManagerListSongs[size];
        }
    };

    public List<SongItem> getListOfSongsItems() {
        return listOfSongsItems;
    }

    public ArrayList<String> getListOfUrlSongs() {
//        for (SongItem song_i : listOfSongsItems) {
//            listOfUrlSongs.clear();
//            listOfUrlSongs.add(song_i.getUrl());
//        }
        Log.d("ManagerListSongs", "addSong: " + listOfUrlSongs);
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
            listOfUrlSongs.add(stringUrl);
//            Log.d("ManagerListSongs", "addSong: " + stringUrl);
            Log.d("ManagerListSongs", "addSong: " + listOfUrlSongs);
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(listOfUrlSongs);
    }
}
