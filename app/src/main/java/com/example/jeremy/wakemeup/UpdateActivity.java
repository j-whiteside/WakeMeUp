package com.example.jeremy.wakemeup;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.GregorianCalendar;

public class UpdateActivity extends AppCompatActivity {

    int dataIndex;
    AlarmObjectData data;
    AlarmObjectData tempAlarm;

    EditText alarmNameEditText;
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
        dataIndex = getIntent().getIntExtra("data_index", MainActivity.DATA_INDEX);
        tempAlarm = new AlarmObjectData("", new GregorianCalendar(), new GregorianCalendar(), 5);

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.mainRelativeLayout);
        EditText editText = (EditText) findViewById(R.id.alarmNameEditText);
        showTimePickerDialog();

        saveButton = (Button) findViewById(R.id.saveButton);
        cancelButton = (Button) findViewById(R.id.cancelButton);
        deleteButton = (Button) findViewById(R.id.deleteButton);

        if (dataIndex == -999) {
            Toast.makeText(UpdateActivity.this, "Error: No Object", Toast.LENGTH_SHORT).show();
            returnToMainActivity();
        }
        else if (dataIndex == -1000) {
            deleteButton.setEnabled(false);
            data = new AlarmObjectData("", new GregorianCalendar(), new GregorianCalendar(), 10);
        }
        else {
            data = MainActivity.serializableData.dataList.get(dataIndex);
        }

        alarmNameEditText = (EditText) findViewById(R.id.alarmNameEditText);

        startTimePicker = (TimePicker) findViewById(R.id.startTimePicker);
        startTimePicker.setHour(data.getStartHour());
        startTimePicker.setMinute(data.getStartMinute());

        endTimePicker = (TimePicker) findViewById(R.id.endTimePicker);
        endTimePicker.setHour(data.getEndHour());
        endTimePicker.setMinute(data.getEndMinute());

        frequencySpinner = (Spinner) findViewById(R.id.frequencySpinner);
        ArrayAdapter frequencyAdapter = ArrayAdapter.createFromResource(this, R.array.FrequencySpinnerOptions, android.R.layout.simple_spinner_item);
        frequencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        frequencySpinner.setAdapter(frequencyAdapter);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveData();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnToMainActivity();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = AskDeleteOption();
                dialog.show();
            }
        });
    }

    private void saveData()
    {
        tempAlarm.setName(alarmNameEditText.toString());
        tempAlarm.setFrequency(frequencySpinner.getSelectedItemPosition());

        if(alarmNameEditText.getText().toString().length() > 0)
        {
            if(dataIndex < 0){
                MainActivity.serializableData.dataList.add(tempAlarm);
            } else {
                MainActivity.serializableData.dataList.set(dataIndex, tempAlarm);
            }

            writeSerializableData();

            returnToMainActivity();
        }
        else
        {
            AlertDialog dialog = emptyNameOnSave();
            dialog.show();
        }


    }


    private void writeSerializableData()
    {
        try(FileOutputStream fs = this.getApplicationContext().openFileOutput("alarm_data_array.bin", Context.MODE_PRIVATE))
        {
            ObjectOutputStream os = new ObjectOutputStream(fs);

            os.writeObject(MainActivity.serializableData);

            os.close();
        }
        catch (FileNotFoundException ex)
        {
            Toast.makeText(UpdateActivity.this, "File Not Found", Toast.LENGTH_SHORT).show();
        }
        catch (IOException ex)
        {
            Toast.makeText(UpdateActivity.this, "IO Exception", Toast.LENGTH_SHORT).show();
        }
    }

    public void returnToMainActivity() {
        Intent mainIntent = new Intent(UpdateActivity.this, MainActivity.class);
        startActivity(mainIntent);
    }


    private AlertDialog AskDeleteOption() {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
                //set message, title, and icon
                .setTitle("Delete")
                .setMessage("Are you sure you want to delete?")

                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        MainActivity.serializableData.dataList.remove(dataIndex);
                        writeSerializableData();
                        returnToMainActivity();
                        dialog.dismiss();

                    }

                })


                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();
        return myQuittingDialogBox;

    }


    private AlertDialog emptyNameOnSave()
    {
        AlertDialog errorDialogueBox =new AlertDialog.Builder(this)
                //set message, title, and icon
                .setTitle("Error")
                .setMessage("You must enter a name.")

                .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();
        return errorDialogueBox;

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
                tempAlarm.setStartHour(hourOfDay);
                tempAlarm.setStartMinute(minute);
                Toast.makeText(UpdateActivity.this, "Alarm will start at " + startHour + ":" + startMinute, Toast.LENGTH_LONG).show();
            }
            else {
                endHour = hourOfDay;
                endTimePicker.setHour(hourOfDay);
                endMinute = minute;
                endTimePicker.setMinute(minute);
                tempAlarm.setEndHour(hourOfDay);
                tempAlarm.setEndMinute(minute);
                Toast.makeText(UpdateActivity.this, "Alarm will end at " + endHour + ":" + endMinute, Toast.LENGTH_LONG).show();
            }


        }
    };
}
