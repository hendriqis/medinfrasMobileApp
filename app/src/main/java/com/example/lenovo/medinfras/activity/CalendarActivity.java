package com.example.lenovo.medinfras.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lenovo.medinfras.R;
import com.squareup.timessquare.CalendarCellDecorator;
import com.squareup.timessquare.CalendarPickerView;
import com.squareup.timessquare.DefaultDayViewAdapter;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

public class CalendarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        Date today = new Date();
        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 10);

        CalendarPickerView datePicker = findViewById(R.id.calendarPickerViewId);
        datePicker.init(today, nextYear.getTime())
                .withSelectedDate(today);

        datePicker.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                /*String selectedDate = DateFormat.getDateInstance(DateFormat.FULL).format(date);*/

                Calendar calSelected = Calendar.getInstance();
                calSelected.setTime(date);

                /*String selectedDate = "" + calSelected.get(Calendar.DAY_OF_MONTH) + " " +
                        (calSelected.get(Calendar.MONTH) + 1) + " " + calSelected.get(Calendar
                        .YEAR);*/
            }

            @Override
            public void onDateUnselected(Date date) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_calendar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home :
                onBackPressed();
                return true;
            case R.id.menuWeekView :
                startActivity(new Intent(CalendarActivity.this, WeekViewActivity.class));
                return true;
        } return super.onOptionsItemSelected(item);
    }
}
