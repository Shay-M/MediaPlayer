package com.example.mediaplayer;/* Created by Shay Mualem 22/07/2021 */
/*

import android.annotation.SuppressLint;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

*/
/*import android.app.NotificationChannel;
import android.app.NotificationManager;
import androidx.core.app.NotificationCompat;*//*


public class MainActivity extends AppCompatActivity {


    boolean isPlaying = false;
    Button playBtn;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playBtn = findViewById(R.id.play_btn);

        playBtn.setOnClickListener(view -> {

            if (isPlaying) {
                playBtn.setText("Play!");
                stopMusic();
            } else {
                playBtn.setText("Stop!");
                playMusic();
            }
            isPlaying = !isPlaying;
        });

        if (getIntent().hasExtra("playing")) {
            playBtn.setText("Stop");
            isPlaying = true;
        }


        */
/*NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        NotificationChannel channel = new NotificationChannel("id", "name", NotificationManager.IMPORTANCE_DEFAULT);
        notificationManager.createNotificationChannel(channel);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "id");

        builder.setSmallIcon(android.R.drawable.ic_media_play).setContentTitle("Playing music")
                .setContentText("Playing legendery Bob, enjoy");


        notificationManager.notify(1,builder.build());*//*


    }

    private void playMusic() {
        Intent intent = new Intent(this, MusicPlayerService.class);
//        intent.putExtra("link", "http://syntax.org.il/xtra/bob.m4a");
        intent.putExtra("link", "https://file-examples-com.github.io/uploads/2017/11/file_example_MP3_2MG.mp3");

        startService(intent);
    }

    private void stopMusic() {
        Intent intent = new Intent(this, MusicPlayerService.class);
        stopService(intent);
    }
}
*/


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediaplayer.ActionsMediaPlayer.Actions;
import com.example.mediaplayer.ActionsMediaPlayer.ActionsPlayer;
import com.example.mediaplayer.ManagerSongs.ManagerListSongs;
import com.example.mediaplayer.SongsRecyclerView.SongAdapter;
import com.example.mediaplayer.SongsRecyclerView.SongItem;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;


public class MainActivity extends AppCompatActivity implements ActionsPlayer {
    boolean isPlaying = false;
    private ArrayList<String> listOfSongs = new ArrayList<>();
    private Intent intent;
    private ManagerListSongs managerListSongs;
    //////////////////

    private BroadcastReceiver pausePlayingAudio = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
//
            Log.d("BroadcastReceiver", "context: " + context + "Intent: " + intent);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText linkEt = findViewById(R.id.link);
        final ImageButton playBtn = findViewById(R.id.btn_play_main);
        final ImageButton nextBtn = findViewById(R.id.btn_next_main);
        final ImageButton addBtn = findViewById(R.id.addLinkBtn);
        RecyclerView recyclerView = findViewById(R.id.recycler_view_songs);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<SongItem> songsList = new ArrayList<>();

        managerListSongs = ManagerListSongs.getInstance();


        AtomicReference<SongAdapter> songAdapter = new AtomicReference<>(new SongAdapter(managerListSongs.getListOfSongsItems()));
        recyclerView.setAdapter(songAdapter.get());


        //play button
        playBtn.setOnClickListener(view -> {
            if (!isPlaying) {
                //first time
                if (intent == null) {
                    intent = new Intent(MainActivity.this, MusicPlayerService.class);
                    intent.putExtra("command", "new_instance");
//            intent.putExtra("managerListSongs", (Parcelable) managerListSongs);
                    Log.d("playBtn", "managerListSongs: " + managerListSongs);

                    startService(intent);


                } else playClick();
                isPlaying = true;
//                playBtn.setBackground(Drawable.createFromPath("R.drawable.sound_icon"));
                playBtn.setImageResource(R.drawable.sound_icon);
            } else {
                isPlaying = false;
                pauseClick();
                playBtn.setImageResource(R.drawable.ic_launcher_foreground);
            }
        });


        addBtn.setOnClickListener(view -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.MyDialogTheme);
            View dialogView = getLayoutInflater().inflate(R.layout.add_song_dialog, null);

            EditText linkText = dialogView.findViewById(R.id.dialog_link);
            ImageButton takeApic = dialogView.findViewById(R.id.take_a_pic);
            ImageButton addApic = dialogView.findViewById(R.id.add_a_pic);

            builder.setView(dialogView).setPositiveButton("Cancel", (dialog, which) -> {
            }).show();


//            String link = linkEt.getText().toString();
//            if (!link.isEmpty()) {
//                try {
//                    managerListSongs.addSong(link);
//                    Log.d(">>>addBtn", "managerListSongs: " + managerListSongs);
//
//                    // update the view list
//                    songAdapter.set(new SongAdapter(managerListSongs.getListOfSongsItems()));
//                    recyclerView.setAdapter(songAdapter.get());
//                    com.example.shiftmanagerhit.Utility.HidesKeyboard.hideKeyboard(this);
//
//                } catch (Exception e) {
////                    e.printStackTrace();
//                    Snackbar snackbar = Snackbar
//                            .make(view, "" + e.getMessage(), Snackbar.LENGTH_LONG);
//                    snackbar.show();
//                }
//            }
//            linkEt.setText("");
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

}

