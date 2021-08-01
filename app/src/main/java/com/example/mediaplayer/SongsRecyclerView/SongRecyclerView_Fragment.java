package com.example.mediaplayer.SongsRecyclerView;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.mediaplayer.ManagerSongs.ManagerListSongs;
import com.example.mediaplayer.R;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

import com.example.mediaplayer.ActionsMediaPlayer.Actions;
import com.example.mediaplayer.ActionsMediaPlayer.ActionsPlayer;
import com.example.mediaplayer.Dialogs.AddSongDialog;
import com.example.mediaplayer.ManagerSongs.ManagerListSongs;
import com.example.mediaplayer.SongsRecyclerView.SongAdapter;
import com.example.mediaplayer.SongsRecyclerView.SongItem;
import com.example.mediaplayer.utils.CameraManagerUrl;
import com.google.android.material.snackbar.Snackbar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SongRecyclerView_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SongRecyclerView_Fragment extends Fragment implements RecyclerViewClickInterface {

    private ArrayList<String> listOfSongs = new ArrayList<>();
    private Intent intent;
    private RecyclerView recyclerView;
    private ManagerListSongs managerListSongs;

    private RecyclerViewClickInterface recyclerViewClickInterface;
    private AtomicReference<SongAdapter> songAdapter;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SongRecyclerView_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SongRecyclerView_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SongRecyclerView_Fragment newInstance(String param1, String param2) {
        SongRecyclerView_Fragment fragment = new SongRecyclerView_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        managerListSongs = ManagerListSongs.getInstance();

        songAdapter = new AtomicReference<>(new SongAdapter(managerListSongs.getListOfSongsItems(), this));
        recyclerView.setAdapter(songAdapter.get());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_song_recycler_view, container, false);
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onLongClick(int position) {

    }
}