package com.example.mediaplayer;
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


        notificationManager.notify(1,builder.build());*/

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediaplayer.ManagerSongs.ManagerListSongs;
import com.example.mediaplayer.SongsRecyclerView.SongAdapter;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;


public class MainActivity extends AppCompatActivity implements ServiceConnection {
    boolean isPlaying = false;
    private ArrayList<String> listOfSongs = new ArrayList<>();
    private Intent intent;
    private MusicPlayerService musicPlayerService;


    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText linkEt = findViewById(R.id.link);
        final Button playBtn = findViewById(R.id.btn_play);
        final Button nextBtn = findViewById(R.id.btn_next);
        final Button nextPrevious = findViewById(R.id.btn_previous);
        final ImageButton addBtn = findViewById(R.id.addLinkBtn);
        RecyclerView recyclerView = findViewById(R.id.recycler_view_songs);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        List<SongItem> songsList = new ArrayList<>();

        ManagerListSongs managerListSongs = new ManagerListSongs();

        AtomicReference<SongAdapter> songAdapter = new AtomicReference<>(new SongAdapter(managerListSongs.getListOfSongsItems()));
        recyclerView.setAdapter(songAdapter.get());


        //play button
        playBtn.setOnClickListener(view -> {

//                playBtn.setImageResource(R.drawable.ic_launcher_background);
//            intent = new Intent(MainActivity.this, MusicPlayerService.class);
//            intent.putExtra("command", "new_instance");

            intent = new Intent(MainActivity.this, MusicPlayerService.class);
            bindService(intent,this,BIND_AUTO_CREATE);
//            intent.putExtra("managerListSongs", (Parcelable) managerListSongs);
            // bindService(intent, (ServiceConnection) this, BIND_AUTO_CREATE);

//            startService(intent);

        });

        //add button,add a song by link
//        addBtn.setOnClickListener( int aa, new View.OnClickListener() {
//
//        });


        addBtn.setOnClickListener(view -> {

            String link = linkEt.getText().toString();
            if (!link.isEmpty()) {
                try {
                    managerListSongs.addClicked(link);
//                    managerListSongs.addSong(link);

                    Log.d(">>>addBtn", "managerListSongs: " + managerListSongs);
                    toggleUpdates();
//                    intent.putExtra()
                    // update the view list
                    songAdapter.set(new SongAdapter(managerListSongs.getListOfSongsItems()));
                    recyclerView.setAdapter(songAdapter.get());

                } catch (Exception e) {
//                    e.printStackTrace();
                    Snackbar snackbar = Snackbar
                            .make(view, "" + e.getMessage(), Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
            linkEt.setText("");
        });


        nextPrevious.setOnClickListener(view -> {
            managerListSongs.nextClicked();
        });

        nextBtn.setOnClickListener(view -> {
            managerListSongs.nextClicked();
        });


    }

    @Override
    protected void onPause() {
        super.onPause();
        unbindService(this);
    }

    private void toggleUpdates() {

    }

    //implements ServiceConnection
    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        MusicPlayerService.MyBinder binder = (MusicPlayerService.MyBinder) service;
        musicPlayerService = binder.getService();
        Log.e("onServiceConnected", "" + musicPlayerService);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        musicPlayerService = null;
        Log.e("onServiceDisconnected", "" + musicPlayerService);

    }


//    interface OnAddASongListener {
//        void SucceededAddASong(ArrayList<String> URLs);
//
////        void FailedAddASong();
//    }
}

