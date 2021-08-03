package com.example.mediaplayer;/* Created by Shay Mualem 22/07/2021 */


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.mediaplayer.ActionsMediaPlayer.Actions;
import com.example.mediaplayer.ActionsMediaPlayer.ActionsPlayer;
import com.example.mediaplayer.Dialogs.AddSongDialog;
import com.example.mediaplayer.ManagerSongs.ManagerListSongs;
import com.example.mediaplayer.SongsRecyclerView.SongAdapter;
import com.example.mediaplayer.SongsRecyclerView.SongRecyclerView_UpdateUI_Fragment;
import com.example.mediaplayer.SongsRecyclerView.SoundBigFragment;
import com.example.mediaplayer.utils.CameraManagerUrl;
import com.google.android.material.snackbar.Snackbar;


public class MainActivity extends AppCompatActivity implements ActionsPlayer, AddSongDialog.AddSongDialogListener {
    final String REGISTER_FRAGMENT_TAG = "register_fragment";
    boolean isPlaying = false;
    private Intent intent;
    private ManagerListSongs managerListSongs;
    private ImageView playBtn;
    private final BroadcastReceiver pausePlayingAudio = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Log.d("pausePlayingAudio", "context: " + context + "Intent: " + intent);
            playBtn.setImageResource(R.drawable.pausexhdpi);
            isPlaying = false;

        }
    };
    private final BroadcastReceiver closePlayingAudio = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Log.d("closePlayingAudio", "context: " + context + "Intent: " + intent);
            playBtn.setImageResource(R.drawable.playxhdpi);
            isPlaying = false;

        }
    };
    private ImageView backBigPic;

    ///
    private SongRecyclerView_UpdateUI_Fragment songRecyclerViewFragment;
    private SongAdapter.RecyclerViewListener recyclerViewListener;

    //register to actions
    private void register_pausePlayingAudio() {
        IntentFilter intentFilter = new IntentFilter(Actions.PAUSE_SONG);
        registerReceiver(pausePlayingAudio, intentFilter);
    }

    private void register_closePlayingAudio() {
        IntentFilter intentFilter = new IntentFilter(Actions.CLOSE_SONG);
        registerReceiver(closePlayingAudio, intentFilter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Removing top action bar: https://www.geeksforgeeks.org/different-ways-to-hide-action-bar-in-android-with-examples/
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        playBtn = findViewById(R.id.btn_play_main);
        backBigPic = findViewById(R.id.back_big_pic);
        final ImageView nextBtn = findViewById(R.id.btn_next_main);
        final ImageView backBtn = findViewById(R.id.btn_prev_main);
        final ImageView addBtn = findViewById(R.id.addLinkBtn);


        managerListSongs = ManagerListSongs.getInstance();

        CameraManagerUrl.init(this);


        /////////////////
        recyclerViewListener = new SongAdapter.RecyclerViewListener() {
            @Override
            public void onItemClick(int position, View view) {
                Log.d("onItemClick", "position: " + position);
            }

            @Override
            public void onLongClick(int position, View view) {
                Log.d("onLongClick", "position: " + position);
            }

            @Override
            public void onImgClick(int position, View view) {
                Log.d("onImgClick", "position: " + position);
                String songName = managerListSongs.getListOfSongsItems().get(position).getName();

                SoundBigFragment soundBigFragment = SoundBigFragment.newInstance(songName, managerListSongs.getListOfSongsItems().get(position).getUri());
                backBigPic.setVisibility(View.VISIBLE);
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                fragmentTransaction.replace(R.id.fragment_list_and_big, soundBigFragment, REGISTER_FRAGMENT_TAG);

                fragmentTransaction.commit();

            }
        };

        ////
        backBigPic.setOnClickListener(v -> showListOfSongFragment());
        showListOfSongFragment();

        //////////////////


        //play button
        playBtn.setOnClickListener(view -> {
            if (!isPlaying) {

                //first time
                if (intent == null) {
                    intent = new Intent(MainActivity.this, MusicPlayerService.class);
                    intent.putExtra("command", "new_instance");

                    startService(intent);

                } else playClick();
                isPlaying = true;
                playBtn.setImageResource(R.drawable.pausexhdpi);
            } else {
                isPlaying = false;
                pauseClick();
                playBtn.setImageResource(R.drawable.playxhdpi);
            }
        });

        nextBtn.setOnClickListener(view -> nextClick());
        backBtn.setOnClickListener(view -> prevClick());

        // add a song
        addBtn.setOnClickListener(view -> {
            AddSongDialog addSongDialog = new AddSongDialog();
            addSongDialog.show(getSupportFragmentManager(), "add song dialog");

        });

        register_pausePlayingAudio();
        register_closePlayingAudio();

    }

    private void showListOfSongFragment() {

        songRecyclerViewFragment = new SongRecyclerView_UpdateUI_Fragment(recyclerViewListener);
        backBigPic.setVisibility(View.GONE);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.fragment_list_and_big, songRecyclerViewFragment, REGISTER_FRAGMENT_TAG);
        fragmentTransaction.commit();

    }

    @Override
    public void nextClick() {

        intent = new Intent(MainActivity.this, MusicPlayerService.class);
        intent.putExtra("command", Actions.NEXT_SONG);

        startService(intent);
    }

    @Override
    public void prevClick() {

        intent = new Intent(MainActivity.this, MusicPlayerService.class);
        intent.putExtra("command", Actions.PREV_SONG);

        startService(intent);
    }

    @Override
    public void playClick() {

        intent = new Intent(MainActivity.this, MusicPlayerService.class);
        intent.putExtra("command", Actions.PLAY_SONG);

        startService(intent);
    }

    @Override
    public void pauseClick() {
        intent = new Intent(MainActivity.this, MusicPlayerService.class);
        intent.putExtra("command", Actions.PAUSE_SONG);

        startService(intent);
    }


    /**
     * parameter from the interface AddSongDialogListener
     */
    @Override
    public void applyAddSong(String Name, String songUrl, Uri imgUri) {
        try {
            managerListSongs.addSong(songUrl, imgUri);

        } catch (Exception e) {
            e.printStackTrace();
            Snackbar snackbar = Snackbar
                    .make(getWindow().getDecorView().findViewById(R.id.RelativeLayout),
                            "" + e.getMessage(), Snackbar.LENGTH_LONG);
            snackbar.show();
        }

        //Hide the Keyboard
        com.example.shiftmanagerhit.Utility.HidesKeyboard.hideKeyboard(this);
    }

}

