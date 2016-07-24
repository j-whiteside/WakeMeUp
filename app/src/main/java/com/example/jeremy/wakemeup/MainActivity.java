package com.example.jeremy.wakemeup;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "com.example.jeremy.wakemeup.MESSAGE";

    Button stopAlarmButton, startAlarmButton;
    NotificationManager notificationManager;
    boolean isAlarmRinging = false;
    int notificationID = 33;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        stopAlarmButton = (Button) findViewById(R.id.stopAlarmButton);
        startAlarmButton = (Button) findViewById(R.id.startAlarmButton);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void UpdateAlarm (View view) {
        Intent updateIntent = new Intent(this, UpdateActivity.class);
        //EditText editText = (EditText) findViewById(R.id.alarmNameEditText);
        String message = "Test text";
        updateIntent.putExtra(EXTRA_MESSAGE, message);
        startActivity(updateIntent);
    }

    public void showAlarm(View view) {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
        notificationBuilder.setContentTitle("Wake up!");
        notificationBuilder.setContentText("New message");
        notificationBuilder.setTicker("Alert new message");
        notificationBuilder.setSmallIcon(R.drawable.alarm_notification_24_white);

        Intent moreInfoIntent = new Intent(this, AlarmPopup.class);
        TaskStackBuilder tStackBuilder = TaskStackBuilder.create(this);
        tStackBuilder.addParentStack(AlarmPopup.class);
        tStackBuilder.addNextIntent(moreInfoIntent);
        PendingIntent pendingIntent = tStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        notificationBuilder.setContentIntent(pendingIntent);

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationID, notificationBuilder.build());
        isAlarmRinging = true;
    }

    public void stopAlarm(View view) {
        if (isAlarmRinging) {
            notificationManager.cancel(notificationID);
        }
    }

    public void scheduleAlarm(View view) {
        Long alertTime = new GregorianCalendar().getTimeInMillis()+(6*1000);

        Intent alertIntent = new Intent(this, AlertReceiver.class);

        PendingIntent pi = PendingIntent.getBroadcast(this, 1, alertIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

        //alarmManager.set(AlarmManager.RTC_WAKEUP, alertTime, pi);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, alertTime, 1000 * 10, pi);

        Toast.makeText(MainActivity.this, "Bomb activated", Toast.LENGTH_LONG).show();
    }

}
