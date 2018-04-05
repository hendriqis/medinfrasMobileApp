package com.example.lenovo.medinfras.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.medinfras.R;
import com.example.lenovo.medinfras.model.TimelineModel;
import com.github.vipulasri.timelineview.TimelineView;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Lenovo on 3/2/2018.
 */

public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.ViewHolder> {
    private List<TimelineModel> timelineModelList;
    private Context context;

    public TimelineAdapter(List<TimelineModel> timelineModelList, Context context) {
        this.timelineModelList = timelineModelList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_timeline_row, parent, false);
        return new ViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        (holder).textView.setText(timelineModelList.get(position).getName());
        (holder).textViewDescription.setText(timelineModelList.get(position).getDescription());
        (holder).textViewTime.setText(timelineModelList.get(position).getTime());
        (holder).timelineCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Timeline "+position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return timelineModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TimelineView timelineView;
        CardView timelineCV;
        TextView textView, textViewDescription, textViewTime;

        public ViewHolder(View view, int viewType) {
            super(view);
            timelineView = view.findViewById(R.id.timelineViewId);
            textView = view.findViewById(R.id.timelineName);
            textViewDescription = view.findViewById(R.id.timelineDescription);
            textViewTime = view.findViewById(R.id.timelineTime);
            timelineCV = view.findViewById(R.id.timelineCV);
        }
    }
}
