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
        super.onDestroy();
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
        startForeground(1, builder.build());*/

        /////////////////////////////////////////////////////////////
        final int NOTIFY_ID = 1000;

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        String channelId = "some_channel_id";
        CharSequence channelName = "Some Channel";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel notificationChannel = new NotificationChannel(channelId, channelName, importance);
        manager.createNotificationChannel(notificationChannel);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId);
        builder.setSmallIcon(android.R.drawable.ic_media_play).setContentTitle("Playing music")
                .setContentText("Play!");

        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.media_player_notification_layout);


        Intent playIntent = new Intent(this, MainActivity.class);
        playIntent.putExtra("playing", true);

        PendingIntent playPendingIntent = PendingIntent.getActivity(this, 0, playIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.play_btn, playPendingIntent);

      /*  Intent nextIntent = new Intent(this, SecondActivity.class);
        nextIntent.putExtra("notif_txt", "next");
        PendingIntent nextPendingIntent = PendingIntent.getActivity(this, 1, nextIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.next_btn, nextPendingIntent);

        Intent prevIntent = new Intent(this, SecondActivity.class);
        prevIntent.putExtra("notif_txt", "prev");
        PendingIntent prevPendingIntent = PendingIntent.getActivity(this, 2, prevIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.prev_btn, prevPendingIntent);

        Intent pauseIntent = new Intent(this, SecondActivity.class);
        pauseIntent.putExtra("notif_txt", "pause");
        PendingIntent pausePendingIntent = PendingIntent.getActivity(this, 3, pauseIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.pause_btn, pausePendingIntent);
*/

        builder.setContent(remoteViews);
        manager.notify(NOTIFY_ID, builder.build());
        startForeground(NOTIFY_ID, builder.build());
    }
}
