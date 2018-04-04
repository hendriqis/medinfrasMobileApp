package com.example.lenovo.medinfras.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lenovo.medinfras.R;
import com.example.lenovo.medinfras.model.TimelineModel;
import com.example.lenovo.medinfras.adapter.TimelineAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TimelineActivity extends AppCompatActivity {

    @BindView(R.id.timelineRV)
    RecyclerView timelineRV;
    @BindView(R.id.fab_addEvent)
    FloatingActionButton fabAddEvent;

    String[] name = {"Event 1", "Event 2", "Event 3"};
    String[] status = {"active", "inactive", "inactive"};
    String[] description = {"Description 1", "Description 2", "Description 3"};
    String[] time = {"11:00 PM", "10:03 AM", "10:03 PM"};

    List<TimelineModel> timeLineModelList;
    TimelineModel[] timeLineModel;
    Context context;
    LinearLayoutManager linearLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        ButterKnife.bind(this);

        timeLineModelList = new ArrayList<>();
        int size = name.length;
        timeLineModel = new TimelineModel[size];
        context = TimelineActivity.this;
        linearLayoutManager = new LinearLayoutManager(this);

        for (int i = 0; i < size; i++) {
            timeLineModel[i] = new TimelineModel();
            timeLineModel[i].setName(name[i]);
            timeLineModel[i].setStatus(status[i]);
            timeLineModel[i].setDescription(description[i]);
            timeLineModel[i].setTime(time[i]);
            timeLineModelList.add(timeLineModel[i]);
        }

        timelineRV.setLayoutManager(linearLayoutManager);
        timelineRV.setAdapter(new TimelineAdapter(timeLineModelList, context));

        fabAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = LayoutInflater.from(context);
                View promptsView = inflater.inflate(R.layout.custom_timeline_alert_dialog, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                alertDialogBuilder.setView(promptsView);

                final EditText edtTextEvent = (EditText) view.findViewById(R.id.edtTextEvent);
                final EditText edtTextDescription = (EditText) view.findViewById(R.id.edtTextDescription);

                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Submit",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Toast.makeText(context, "Submitted. (when the button pressed it should be add the timeline card view)", Toast.LENGTH_SHORT).show();
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                });

                AlertDialog alertDialog = alertDialogBuilder.create();

                alertDialog.show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
