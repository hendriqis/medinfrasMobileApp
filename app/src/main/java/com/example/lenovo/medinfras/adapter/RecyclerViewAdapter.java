package com.example.lenovo.medinfras.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.medinfras.R;
import com.example.lenovo.medinfras.activity.PatientRecyclerViewActivity;
import com.example.lenovo.medinfras.activity.TimelineActivity;

/**
 * Created by Lenovo on 2/21/2018.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    String[] patients;

    public RecyclerViewAdapter (Context context, String[] patients){
        this.context = context;
        this.patients = patients;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_layout_listview, parent, false);
        Item item = new Item(view);
        return item;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((Item)holder).textView.setText(patients[position]);
        ((Item)holder).button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*PatientRecyclerViewActivity.startActivity(new Intent(PatientRecyclerViewActivity.this, TimelineActivity.class));*/
                Intent intent = new Intent(context, TimelineActivity.class);
                context.startActivity(intent);
                /*Toast.makeText(context, "Button Clicked", Toast.LENGTH_SHORT).show();*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return patients.length;
    }

    public class Item extends RecyclerView.ViewHolder {
        TextView textView;
        Button button;
        public Item(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textViewName);
            button = (Button) itemView.findViewById(R.id.btnTimeline);
        }
    }
}
