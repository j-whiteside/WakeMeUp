package com.example.jeremy.wakemeup;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;

/**
 * Created by Jeremy on 7/23/2016.
 */
public class AlertReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {

        createNotification(context, "Wake up", "5 seconds has passed", "Alert");
    }

    public void createNotification(Context context, String header, String details, String msgAlert) {
        PendingIntent notificationIntent = PendingIntent.getActivity(context, 0,
                new Intent(context, MainActivity.class), 0);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context);
        notificationBuilder.setContentTitle(header);
        notificationBuilder.setContentText(details);
        notificationBuilder.setTicker(msgAlert);
        notificationBuilder.setSmallIcon(R.drawable.alarm_notification_48_black);

        notificationBuilder.setContentIntent(notificationIntent);
        notificationBuilder.setDefaults(NotificationCompat.DEFAULT_ALL);
        notificationBuilder.setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(1, notificationBuilder.build());
    }
}
