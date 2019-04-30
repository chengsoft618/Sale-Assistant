package com.shoniz.saledistributemobility.message.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.view.message.MessageActivity;


public class PersonalNotificationHandler implements IMessageHandler {
    private final static int NOTIFICATION_PUBLIC_ID = 1001;
    private static NotificationManager notificationManager;
    private Context context;
    private int count;

    public PersonalNotificationHandler(Context context, int count) {
        this.context = context;
        this.count = count;
        notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public void cancelNotification() {
        notificationManager.cancel(NOTIFICATION_PUBLIC_ID);
    }

    private void riseActivity() {
        Intent intent = MessageActivity.newIntent(context);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_launcher_round)
                .setContentTitle("پیام جدید از شرکت شونیز")
                .setContentText("شما " + count + "پیام جدید خوانده نشده دارید ")
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        notificationManager.notify(NOTIFICATION_PUBLIC_ID, notificationBuilder.build());
    }

    @Override
    public void MessageHandler() {
        if (count > 0)
            riseActivity();

    }
}
