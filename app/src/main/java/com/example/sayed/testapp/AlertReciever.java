package com.example.sayed.testapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;


public class AlertReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        createNotification(context , "Times Up" , "Time has passed!" , "Alert");

    }
    public void createNotification(Context context , String msg , String msgText , String msgAlert){
        PendingIntent pendingIntent = PendingIntent.getActivity(context , 0 ,
                new Intent(context , MainActivity.class) , 0);

        NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new
                NotificationCompat.Builder(context).setContentTitle(msg)
                .setContentText(msgText)
                .setTicker(msgAlert)
                .setSmallIcon(R.mipmap.ic_launcher);

        notificationBuilder.setContentIntent(pendingIntent);

        notificationBuilder.setDefaults(NotificationCompat.DEFAULT_SOUND);

        notificationBuilder.setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);


        notificationManager.notify(1 , notificationBuilder.build());



    }
}
