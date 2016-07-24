package com.example.jeremy.wakemeup;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {

    TimePicker startTimePicker;
    TimePicker endTimePicker;
    Spinner frequencySpinner;

    Button saveButton;
    Button cancelButton;
    Button deleteButton;

    static final int START_DIALOG_ID = 0;
    static final int END_DIALOG_ID = 1;
    int startHour;
    int startMinute;
    int endHour;
    int endMinute;
    String buttonName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        Intent updateIntent = getIntent();
        String message = updateIntent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.mainRelativeLayout);
        EditText editText = (EditText) findViewById(R.id.alarmNameEditText);
        editText.setText(message);
        showTimePickerDialog();

        saveButton = (Button) findViewById(R.id.saveButton);
        cancelButton = (Button) findViewById(R.id.cancelButton);
        deleteButton = (Button) findViewById(R.id.deleteButton);

        frequencySpinner = (Spinner) findViewById(R.id.frequencySpinner);
        ArrayAdapter frequencyAdapter = ArrayAdapter.createFromResource(this, R.array.FrequencySpinnerOptions, android.R.layout.simple_spinner_item);
        frequencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        frequencySpinner.setAdapter(frequencyAdapter);

        cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent mainIntent = new Intent(UpdateActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                }
            }
        );
    }

    public void showTimePickerDialog () {
        startTimePicker = (TimePicker)findViewById(R.id.startTimePicker);
        endTimePicker = (TimePicker) findViewById(R.id.endTimePicker);

        startTimePicker.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        buttonName = "start";
                        showDialog(START_DIALOG_ID);
                    }
                }
        );

        endTimePicker.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        buttonName = "end";
                        showDialog(END_DIALOG_ID);
                    }
                }
        );
    }


    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == START_DIALOG_ID)
            return new TimePickerDialog(UpdateActivity.this, timePickerListener, startHour, startMinute, false);
        else if (id == END_DIALOG_ID)
            return new TimePickerDialog(UpdateActivity.this, timePickerListener, endHour, endMinute, false);
        else
            return null;
    }

    protected TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            if (buttonName == "start") {
                startHour = hourOfDay;
                startTimePicker.setHour(hourOfDay);
                startMinute = minute;
                startTimePicker.setMinute(minute);
                Toast.makeText(UpdateActivity.this, "Alarm will start at " + startHour + ":" + startMinute, Toast.LENGTH_LONG).show();
            }
            else {
                endHour = hourOfDay;
                endTimePicker.setHour(hourOfDay);
                endMinute = minute;
                endTimePicker.setMinute(minute);
                Toast.makeText(UpdateActivity.this, "Alarm will end at " + endHour + ":" + endMinute, Toast.LENGTH_LONG).show();
            }


        }
    };
}
