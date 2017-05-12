package com.example.sayed.testapp;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.icu.util.GregorianCalendar;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Handler;

public class MainActivity extends AppCompatActivity {
    Button setNotificationButt , stopNotificationButt , setAlarmButt;
    EditText editText;
    NotificationManager notificationManager;
    TextView textView;
    int notifyID = 33;
    boolean isNotifyActive = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setAlarmButt = (Button) findViewById(R.id.Alarmmein5Secs);
        setNotificationButt = (Button) findViewById(R.id.showNotifications);
        stopNotificationButt = (Button) findViewById(R.id.hideNotifications);
        textView = (TextView) findViewById(R.id.mText);
        editText = (EditText) findViewById(R.id.minEditText);
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    editText.setText("");
                else
                    editText.setHint("Write the minutes to Alert");
            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void showNotification(View view) {
        NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new
                NotificationCompat.Builder(this).setContentTitle("Message")
                .setContentText("New Message")
                .setTicker("New Alarm")
                .setSmallIcon(R.mipmap.ic_launcher);

        Intent newMoreInfo = new Intent(this , MoreInfoNotification.class);

        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(this);

        taskStackBuilder.addParentStack(MoreInfoNotification.class);

        taskStackBuilder.addNextIntent(newMoreInfo);

        PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(0 ,
                PendingIntent.FLAG_UPDATE_CURRENT);

        notificationBuilder.setContentIntent(pendingIntent);

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(notifyID , notificationBuilder.build());

        isNotifyActive = true;

    }
    void Count(int Minutes){

        new CountDownTimer(Minutes * 60 * 1000  , 1000){


            @Override
            public void onTick(long millisUntilFinished) {

                if(millisUntilFinished / 1000 == 3){
                    scheduleTheAlarm();
                }
            textView.setText(("Seconds Remaining: " + millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                textView.setText(("Done!"));
                // it takes time here so i put it in 3 seconds before the timer finishes
               // scheduleTheAlarm();
            }
        }.start();
    }
    void scheduleTheAlarm(){

        Intent alertIntent = new Intent(this , AlertReciever.class);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP , 0,
                PendingIntent.getBroadcast(this , 1 , alertIntent , PendingIntent.FLAG_UPDATE_CURRENT));
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setAlarm(View view) {

        String toAlertStr = editText.getText().toString();

        int toAlertMin = Integer.parseInt(toAlertStr);

        Count(toAlertMin);
    }

    public void hideNotification(View view) {
        if(isNotifyActive){
            notificationManager.cancel(notifyID);
            isNotifyActive = false;

        }
    }
}
