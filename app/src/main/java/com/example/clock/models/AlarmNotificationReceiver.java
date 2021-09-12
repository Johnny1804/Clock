package com.example.clock.models;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;

import androidx.core.app.NotificationCompat;

import com.example.clock.R;

public class AlarmNotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(2000);

        Notification notification = new NotificationCompat.Builder(context)
                .setContentTitle("Alarm is on!")
                .setContentText("You had set up the alarm!")
                .setSmallIcon(R.mipmap.ic_launcher).build();

        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (notification.flags != Notification.FLAG_AUTO_CANCEL)
            notificationManager.notify(0, notification);

        Uri notificationUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);

        Ringtone ringtone = RingtoneManager.getRingtone(context, notificationUri);
        ringtone.play();

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ringtone.stop();
    }


}
