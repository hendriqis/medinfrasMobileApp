package com.example.lenovo.medinfras.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.medinfras.model.PatientDetailModel;
import com.example.lenovo.medinfras.R;
import com.example.lenovo.medinfras.activity.DrugListActivity;
import com.example.lenovo.medinfras.activity.TimelineActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Lenovo on 2/23/2018.
 */

public class RecyclerViewCardAdapter extends RecyclerView.Adapter<RecyclerViewCardAdapter
        .ViewHolder> {

    @BindView(R.id.btnTimelineCard)
    Button btnTimelineCard;
    @BindView(R.id.cardViewId)
    CardView cardViewId;
    @BindView(R.id.LinearLayoutCardViewId)
    LinearLayout LinearLayoutCardViewId;
    @BindView(R.id.btnObatCard)
    Button btnObatCard;

    private List<PatientDetailModel> list_item;
    private Context context;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public RecyclerViewCardAdapter(List<PatientDetailModel> list_item, final Context context) {
        this.list_item = list_item;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .custom_card_recycler_view, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final PatientDetailModel listitem = list_item.get(position);

        holder.textViewName.setText(listitem.getNameCard());
        holder.textViewMRN.setText(listitem.getMRNCard());
        holder.textViewGender.setText(listitem.getGenderCard());
        holder.textViewBirthday.setText(listitem.getBirthdayCard());

        holder.btnTimelineCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TimelineActivity.class);
                context.startActivity(intent);

            }
        });

        holder.btnObatCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DrugListActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list_item.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewName;
        public TextView textViewMRN;
        public TextView textViewGender;
        public TextView textViewBirthday;
        public ImageView imageViewPatient;
        public Button btnTimelineCard;
        public Button btnObatCard;
        public CardView cardViewId;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewName = (TextView) itemView.findViewById(R.id.textViewNameCard);
            textViewMRN = (TextView) itemView.findViewById(R.id.textViewMRNCard);
            textViewGender = (TextView) itemView.findViewById(R.id.textViewGenderCard);
            textViewBirthday = (TextView) itemView.findViewById(R.id.textViewBirthdayCard);
            imageViewPatient = (ImageView) itemView.findViewById(R.id.imageViewCard);
            btnTimelineCard = (Button) itemView.findViewById(R.id.btnTimelineCard);
            btnObatCard = (Button) itemView.findViewById(R.id.btnObatCard);
            cardViewId = (CardView) itemView.findViewById(R.id.cardViewId);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public void setFilter(List<PatientDetailModel> newList) {
        list_item = new ArrayList<>();
        list_item.addAll(newList);
        notifyDataSetChanged();
    }
}
