package com.example.jeremy.wakemeup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class UpdateActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        Intent updateIntent = getIntent();
        String message = updateIntent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.mainRelativeLayout);
        EditText editText = (EditText) findViewById(R.id.alarmNameEditText);
        editText.setText(message);
    }
}
