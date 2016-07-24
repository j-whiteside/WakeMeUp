package com.example.jeremy.wakemeup;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * Created by Jeremy on 7/24/2016.
 */
public class AlarmObjectView extends TableRow{
    Button button;
    TextView alarmTextView;
    Switch activeStatusSwitch;
    ArrayAdapter adapter;
    AlarmObjectView v;

    public AlarmObjectView(Context context, AttributeSet attributes)
    {
        super(context, attributes);

        adapter = ArrayAdapter.createFromResource(context, R.array.FrequencySpinnerOptions, android.R.layout.simple_spinner_item);

        int index = 1;

        button = new Button(context);
        alarmTextView = new TextView(context);
        alarmTextView.setId(index+1);
        activeStatusSwitch = new Switch(context);
        activeStatusSwitch.setId(index+2);

        TableRow.LayoutParams alarmTextViewParams = new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT
        );

        TableRow.LayoutParams activeStatusSwitchParams = new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.MATCH_PARENT
        );

        alarmTextView.setMaxLines(2);
        alarmTextView.setMinLines(2);
        alarmTextView.setTextAppearance(context, android.R.style.TextAppearance_Medium);
        alarmTextView.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        //alarmTextView.setClickable(true);
        alarmTextView.setLayoutParams(alarmTextViewParams);
        this.addView(alarmTextView);

        activeStatusSwitchParams.setMargins(0,0,8,0);
        activeStatusSwitch.setLayoutParams(activeStatusSwitchParams);
        this.addView(activeStatusSwitch);

        button.setLayoutParams(new ViewGroup.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
        button.setBackground(null);
        button.setTextColor(Color.BLACK);
        this.addView(button);

        this.setBackgroundColor(getResources().getColor(R.color.colorSecondary));

    }

    public void updateViewData(AlarmObjectData data)
    {
        String startTime = (data.getStartHour() + ":" + data.getStartMinute());
        String endTime = (data.getEndHour() + ":" + data.getEndMinute());
        String alarmRange = (startTime + " - " + endTime);
        String alarmFrequency = (adapter.getItem(data.getFrequency()).toString());


        alarmTextView.setText(alarmRange + " \n" + alarmFrequency);
        activeStatusSwitch.setChecked(data.isActiveStatus());
    }

    public Button getButton()
    {
        return button;
    }

}