package com.shoniz.saledistributemobility.utility;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.RemoteViews;

import com.shoniz.saledistributemobility.R;

public class Notification {
    String TAG_Notification="TAG_Notification";
    private NotificationCompat.Builder mBuilder;
    private NotificationManager notificationManager;
    private int Id;
    private RemoteViews remoteViews;
    public Notification(Context context,int Id){
        this.Id=Id;
        mBuilder =new NotificationCompat.Builder(context);
        mBuilder.setSmallIcon(R.drawable.ic_launcher_round);

        notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        remoteViews = new RemoteViews(context.getPackageName(), R.layout.notification_view);
        mBuilder.setCustomBigContentView(remoteViews);
        mBuilder.setCustomContentView(remoteViews);
        remoteViews.setImageViewResource(R.id.notification_icon,R.drawable.ic_launcher_round);
        mBuilder.setDefaults(NotificationCompat.DEFAULT_ALL);
        mBuilder.setDefaults(NotificationCompat.DEFAULT_LIGHTS| NotificationCompat.DEFAULT_SOUND);
    }
    public void set(int resId,String title,String text){
        mBuilder.setSmallIcon(resId);
        mBuilder.setContentTitle(title);
        mBuilder.setContentText(text);
        remoteViews.setImageViewResource(R.id.notification_icon,resId);
        remoteViews.setTextViewText(R.id.notification_title,title);
        remoteViews.setTextViewText(R.id.notification_text,text);


    }
    public void setIcon(int resId){
        mBuilder.setSmallIcon(resId);
        remoteViews.setImageViewResource(R.id.notification_icon,resId);
        push();
    }
    public void setTitle(String title){
        mBuilder.setContentTitle(title);
        remoteViews.setTextViewText(R.id.notification_title,title);
        push();
    }
    public void setContext(String text){
        mBuilder.setContentText(text);
        remoteViews.setTextViewText(R.id.notification_text,text);
        push();
    }
    public void setProgress(int min,int max,boolean indeterminate){
        mBuilder.setProgress(min,max,indeterminate);
        remoteViews.setViewVisibility(R.id.notification_progressbar, indeterminate?View.VISIBLE:View.INVISIBLE);
        push();

    }
    public void push(){
        if (notificationManager != null) {
            notificationManager.notify(TAG_Notification,Id, mBuilder.build());
        }
    }
    public  void cancel(){
        if (notificationManager != null) {

            notificationManager.cancel(TAG_Notification,Id);
        }
    }
}
