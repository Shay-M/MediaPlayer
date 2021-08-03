/*
package com.example.mediaplayer;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.io.IOException;


public class MusicPlayerService extends Service implements MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener {

    private final MediaPlayer player = new MediaPlayer();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        player.setOnCompletionListener(this);
        player.setOnPreparedListener(this);
        player.reset();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String link = intent.getStringExtra("link");


        if (!player.isPlaying()) {
            try {
                player.setDataSource(link);
                player.prepareAsync();//?

            } catch (IOException e) {
                e.printStackTrace();
//                https://stackoverflow.com/questions/11540076/android-mediaplayer-error-1-2147483648

            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy(); // if (mediaPlayer != null) {
        if (player.isPlaying())
            player.stop();
        player.release();

    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        Log.d("onCompletion", "playing?: " + player.isPlaying());
        stopSelf();
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {

        player.start();//start playing

        Log.d("onPrepared", "playing?: " + player.isPlaying());

        */
/*NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        NotificationChannel channel = new NotificationChannel("id", "name", NotificationManager.IMPORTANCE_DEFAULT);
        notificationManager.createNotificationChannel(channel);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "id");
        builder.setSmallIcon(android.R.drawable.ic_media_play).setContentTitle("Playing music")
                .setContentText("Play!");

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("playing", true);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(pendingIntent);

         notificationManager.notify(1,builder.build());
        startForeground(1, builder.build());*//*


        /////////////////////////////////////////////////////////////
        final int NOTIFY_ID = 1000;

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        String channelId = "some_channel_id";
        CharSequence channelName = "Some Channel";
        int importance = NotificationManager.IMPORTANCE_HIGH;

        NotificationChannel notificationChannel = new NotificationChannel(channelId, channelName, importance);
        manager.createNotificationChannel(notificationChannel);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId);
        builder.setSmallIcon(android.R.drawable.ic_media_play);

        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.media_player_notification_layout);


        Intent playIntent = new Intent(this, MainActivity.class);
        playIntent.putExtra("playing", true);

        PendingIntent playPendingIntent = PendingIntent.getActivity(this, 0, playIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.play_btn, playPendingIntent);




        builder.setContent(remoteViews);
        manager.notify(NOTIFY_ID, builder.build());
        startForeground(NOTIFY_ID, builder.build());
    }
}
*/

package com.example.mediaplayer;/* Created by Shay Mualem 22/07/2021 */

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.NotificationTarget;
import com.example.mediaplayer.ActionsMediaPlayer.Actions;
import com.example.mediaplayer.ManagerSongs.ManagerListSongs;

import java.io.IOException;
import java.util.ArrayList;


public class MusicPlayerService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {

    final int NOTIFY_ID = 100;
    private final MediaPlayer mediaPlayer = new MediaPlayer();
    private ArrayList<String> listOfSongs = new ArrayList<>();
    private int currentPlaying = 0;
    private RemoteViews remoteViews;
    private NotificationManager notificationManager;
    private NotificationCompat.Builder builder;
    private ManagerListSongs managerListSongs;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Context context = this;
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.reset();

        remoteViews = new RemoteViews(getPackageName(), R.layout.media_player_notification_layout);

        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationChannel channel = new NotificationChannel("channelId", "channelName", NotificationManager.IMPORTANCE_HIGH);
        //Notification sound https://stackoverflow.com/questions/48986856/android-notification-setsound-is-not-working
        channel.setSound(null, null);
        notificationManager.createNotificationChannel(channel);

        builder = new NotificationCompat.Builder(context, "channelId");
        builder.setSmallIcon(android.R.drawable.ic_media_play);


        Intent playIntent = new Intent(context, MusicPlayerService.class);
        playIntent.putExtra("command", Actions.PLAY_SONG);
        PendingIntent playPendingIntent = PendingIntent.getService(context, 0, playIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.play_btn, playPendingIntent);

        Intent pauseIntent = new Intent(context, MusicPlayerService.class);
        pauseIntent.putExtra("command", Actions.PAUSE_SONG);
        PendingIntent pausePendingIntent = PendingIntent.getService(context, 1, pauseIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.pause_btn, pausePendingIntent);

        Intent nextIntent = new Intent(context, MusicPlayerService.class);
        nextIntent.putExtra("command", Actions.NEXT_SONG);
        PendingIntent nextPendingIntent = PendingIntent.getService(context, 2, nextIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.next_btn, nextPendingIntent);

        Intent prevIntent = new Intent(context, MusicPlayerService.class);
        prevIntent.putExtra("command", Actions.PREV_SONG);
        PendingIntent prevPendingIntent = PendingIntent.getService(context, 3, prevIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.prev_btn, prevPendingIntent);

        Intent closeIntent = new Intent(context, MusicPlayerService.class);
        closeIntent.putExtra("command", Actions.CLOSE_SONG);
        PendingIntent closePendingIntent = PendingIntent.getService(context, 4, closeIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.exit_btn, closePendingIntent);

        builder.setContent(remoteViews);

        startForeground(NOTIFY_ID, builder.build());


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String command = intent.getStringExtra("command");
        if (managerListSongs == null)
            managerListSongs = ManagerListSongs.getInstance();

        listOfSongs = managerListSongs.getListOfUrlSongs();
        switch (command) {
            case Actions.NEW_INSTANCE:
                if (!mediaPlayer.isPlaying()) {
                    try {

                        mediaPlayer.setDataSource(listOfSongs.get(currentPlaying));
                        UpdateSongDetails();
                        mediaPlayer.prepareAsync();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case Actions.PLAY_SONG:
                if (!mediaPlayer.isPlaying())
                    mediaPlayer.start();
                break;
            case Actions.NEXT_SONG:
                if (mediaPlayer.isPlaying())
                    mediaPlayer.stop();
                playSong(true);
                break;
            case Actions.PREV_SONG:
                if (mediaPlayer.isPlaying())
                    mediaPlayer.stop();
                playSong(false);
                break;
            case Actions.PAUSE_SONG:
                sendBroadcast(new Intent(Actions.PAUSE_SONG)); //update ui in main
                if (mediaPlayer.isPlaying())
                    mediaPlayer.pause();
                break;
            case Actions.CLOSE_SONG:
                if (mediaPlayer.isPlaying())
                    mediaPlayer.stop();
                sendBroadcast(new Intent(Actions.CLOSE_SONG)); //update ui in main
                stopSelf();

        }
        return super.onStartCommand(intent, flags, startId);
    }


    private void playSong(boolean NextOrPrev) {

        //listOfSongs = managerListSongs.getListOfUrlSongs();

        currentPlaying = managerListSongs.getCurrentPlaying(NextOrPrev);

        mediaPlayer.reset();

        try {
            mediaPlayer.setDataSource(listOfSongs.get(currentPlaying));
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        playSong(true);
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        UpdateSongDetails();
        mediaPlayer.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying())
                mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

    /**
     * Update Notification ui
     */
    public void UpdateSongDetails() {
        String stringUrl = listOfSongs.get(currentPlaying);

        remoteViews.setTextViewText(R.id.notification_title, stringUrl.substring(stringUrl.lastIndexOf('/') + 1));

        final Notification notification = builder.build();
        notificationManager.notify(NOTIFY_ID, notification);

        //add pic using glide https://futurestud.io/tutorials/glide-loading-images-into-notifications-and-appwidgets

        NotificationTarget notificationTarget = new NotificationTarget(

                this,
                R.id.song_image,
                remoteViews,
                notification,
                NOTIFY_ID);

        Uri uri = managerListSongs.getListOfSongsItems().get(currentPlaying).getUri();


        Glide
                .with(this.getApplicationContext())
                .asBitmap()
                .load(uri)
                .centerCrop()
                .into(notificationTarget);


    }


    /*private void updateNotification(){

        int api = Build.VERSION.SDK_INT;
        // update the icon
        mRemoteViews.setImageViewResource(R.id.notif_icon, R.drawable.icon_off2);
        // update the title
        mRemoteViews.setTextViewText(R.id.notif_title, getResources().getString(R.string.new_title));
        // update the content
        mRemoteViews.setTextViewText(R.id.notif_content, getResources().getString(R.string.new_content_text));

        // update the notification
        if (api < VERSION_CODES.HONEYCOMB) {
            mNotificationManager.notify(NOTIF_ID, mNotification);
        }else if (api >= VERSION_CODES.HONEYCOMB) {
            mNotificationManager.notify(NOTIF_ID, mBuilder.build());
        }*/

}

