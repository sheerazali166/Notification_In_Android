package com.example.notificationexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.RemoteInput;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public static final String CHENNEL_ID = "personal_notifications";
    public static final int NOTIFICATION_ID = 001;
    public static final String TXT_REPLY = "Text Reply";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void ShowNotification(View view) {

        Intent landingIntent = new Intent(MainActivity.this, LandingActivity.class);
        landingIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent landingPendingIntent = PendingIntent.getActivity(this, 0, landingIntent, PendingIntent.FLAG_ONE_SHOT);


        Intent yesIntent = new Intent(MainActivity.this, YesActivity.class);
        yesIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent yesPendingIntent = PendingIntent.getActivity(this, 0, yesIntent, PendingIntent.FLAG_ONE_SHOT);



        Intent noIntent = new Intent(MainActivity.this, NoActivity.class);
        noIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent noPendingIntent = PendingIntent.getActivity(this, 0, noIntent, PendingIntent.FLAG_ONE_SHOT);


        createNotificationChennel();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHENNEL_ID);
        builder.setSmallIcon(R.drawable.ic_notification_sms);
        builder.setContentTitle("Simple Notification");
        builder.setContentText("This is simple notification");
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setAutoCancel(true);
        builder.setContentIntent(landingPendingIntent);
        builder.addAction(R.drawable.ic_notification_sms, "Yes", yesPendingIntent);
        builder.addAction(R.drawable.ic_notification_sms, "No", noPendingIntent);


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){

            RemoteInput remoteInput = new RemoteInput.Builder(TXT_REPLY).setLabel("Reply").build();

            Intent remoteIntent = new Intent(MainActivity.this, RemoteActivity.class);
            remoteIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            PendingIntent remotePendingIntent = PendingIntent.getActivity(this, 0, remoteIntent, PendingIntent.FLAG_ONE_SHOT);
            NotificationCompat.Action action = new NotificationCompat.Action.Builder(R.drawable.ic_notification_sms, "Reply", remotePendingIntent).addRemoteInput(remoteInput).build();
            builder.addAction(action);




        }



        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());
    }

    private void createNotificationChennel() {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ){

        CharSequence name = "Personal Notification";
        String description = "All notifications are included";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;

        NotificationChannel notificationChannel = new NotificationChannel(CHENNEL_ID, name, importance);
        notificationChannel.setDescription(description);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(notificationChannel);

        }
    }
}