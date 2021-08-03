package com.example.mediaplayer.ManagerSongs;/* Created by Shay Mualem 03/08/2021 */

import android.content.Context;
import android.util.Log;

import com.example.mediaplayer.SongsRecyclerView.SongItem;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ManagerSaveSongs {

    public static void saveSongList(Context context, List<SongItem> songs) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    FileOutputStream fos = context.openFileOutput("songs_list", Context.MODE_PRIVATE);
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    oos.writeObject(songs);
                    oos.close();
                    Log.d("saveSongList", "i am happy");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static ArrayList<SongItem> readSongList(Context context) {

        ArrayList<SongItem> songs = null;

        try {
            FileInputStream fis = context.openFileInput("songs_list");
            ObjectInputStream ois = new ObjectInputStream(fis);
            songs = (ArrayList<SongItem>) ois.readObject();
            fis.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return songs;
    }
}
