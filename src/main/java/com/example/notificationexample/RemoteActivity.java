package com.example.notificationexample;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationManager;
import android.app.RemoteInput;
import android.os.Bundle;
import android.widget.TextView;

public class RemoteActivity extends AppCompatActivity {

    TextView remoteTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote);

        remoteTextView = findViewById(R.id.textViewRemote);

        Bundle remoteReply = RemoteInput.getResultsFromIntent(getIntent());

        if(remoteReply != null){

            String message = remoteReply.getCharSequence(MainActivity.TXT_REPLY).toString();
            remoteTextView.setText(message);
        }

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(MainActivity.NOTIFICATION_ID);


    }
}