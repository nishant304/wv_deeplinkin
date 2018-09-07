package com.autoportal.wv_poc;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class FireBaseMessaging extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Map<String, String> data = remoteMessage.getData();
        handleLeadAssignedvent(data);
    }


    private void handleLeadAssignedvent(Map<String, String> map) {
        Intent homeIntent = new Intent(getApplicationContext(), MainActivity.class);
        homeIntent.setData(Uri.parse(map.get("deepLink")));
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), Integer.valueOf(map.get("id")), homeIntent,
                PendingIntent.FLAG_IMMUTABLE);


        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this, map.get("id"))
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle(map.get("title"))
                        .setContentText(map.get("message"))
                        .setAutoCancel(true)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(map.get("message")))
                        .setContentIntent(pendingIntent);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mBuilder.setSmallIcon(R.drawable.ic_launcher_background);
            mBuilder.setColor(getResources().getColor(android.R.color.white));
        } else {
            mBuilder.setSmallIcon(R.drawable.ic_launcher_background);
        }

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        int notificationId = Integer.valueOf(map.get("id"));
        String channelId = "channel-01";
        String channelName = "Channel Name";
        int importance = NotificationManager.IMPORTANCE_HIGH;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);
            notificationManager.createNotificationChannel(mChannel);
        }

        notificationManager.notify(Integer.valueOf(map.get("id")), mBuilder.build());
    }
}
