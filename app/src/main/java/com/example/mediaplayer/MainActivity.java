package com.example.mediaplayer;/* Created by Shay Mualem 22/07/2021 */


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.mediaplayer.ActionsMediaPlayer.Actions;
import com.example.mediaplayer.ActionsMediaPlayer.ActionsPlayer;
import com.example.mediaplayer.Dialogs.AddSongDialog;
import com.example.mediaplayer.ManagerSongs.ManagerListSongs;
import com.example.mediaplayer.SongsRecyclerView.SongAdapter;
import com.example.mediaplayer.SongsRecyclerView.SongItem;
import com.example.mediaplayer.SongsRecyclerView.SongRecyclerView_Fragment;
import com.example.mediaplayer.utils.CameraManagerUrl;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;


public class MainActivity extends AppCompatActivity implements ActionsPlayer, AddSongDialog.AddSongDialogListener {
    final String REGISTER_FRAGMENT_TAG = "register_fragemnt";
    boolean isPlaying = false;
    private ArrayList<String> listOfSongs = new ArrayList<>();
    private Intent intent;
    private ManagerListSongs managerListSongs;
    private AtomicReference<SongAdapter> songAdapter;
    //private RecyclerView recyclerView;
    private ImageView playBtn;
    private SongRecyclerView_Fragment songRecyclerViewFragment;

    private BroadcastReceiver pausePlayingAudio = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Log.d("BroadcastReceiver", "context: " + context + "Intent: " + intent);
            playBtn.setImageResource(R.drawable.playxhdpi);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playBtn = findViewById(R.id.btn_play_main);
        final ImageView nextBtn = findViewById(R.id.btn_next_main);
        final ImageView addBtn = findViewById(R.id.addLinkBtn);


        List<SongItem> songsList = new ArrayList<>();

        managerListSongs = ManagerListSongs.getInstance();

        CameraManagerUrl.init(this);

        ////songAdapter = new AtomicReference<>(new SongAdapter(managerListSongs.getListOfSongsItems(), this));
        ////recyclerView.setAdapter(songAdapter.get());

        /////////////////

        songRecyclerViewFragment = new SongRecyclerView_Fragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_out, android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.fragment_list_and_big, songRecyclerViewFragment, REGISTER_FRAGMENT_TAG);
        fragmentTransaction.commit();

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


        addBtn.setOnClickListener(view -> {
            AddSongDialog addSongDialog = new AddSongDialog();
            addSongDialog.show(getSupportFragmentManager(), "add song dialog");

        });

        nextBtn.setOnClickListener(view -> nextClick());

        register_pausePlayingAudio();

    }

    @Override
    public void nextClick() {
        //managerListSongs.nextClick();

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

    //register
    private void register_pausePlayingAudio() {
        IntentFilter intentFilter = new IntentFilter(Actions.PAUSE_SONG);
        registerReceiver(pausePlayingAudio, intentFilter);
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
                    .make(getWindow().getDecorView().findViewById(R.id.RelativeLayout), "" + e.getMessage(), Snackbar.LENGTH_LONG);
            snackbar.show();
        }

        // update the view list

        ////songAdapter.set(new SongAdapter(managerListSongs.getListOfSongsItems(), this));
        ////recyclerView.setAdapter(songAdapter.get());

        //Hide the Keyboard
        com.example.shiftmanagerhit.Utility.HidesKeyboard.hideKeyboard(this);
    }
}

