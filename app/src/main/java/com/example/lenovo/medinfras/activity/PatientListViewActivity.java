package com.example.lenovo.medinfras.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lenovo.medinfras.R;

public class PatientListViewActivity extends AppCompatActivity {

    int[] fotoPasien = {
            R.drawable.patient_male,
            R.drawable.patient_male,
            R.drawable.patient_male,
            R.drawable.patient_female,
            R.drawable.patient_male,
            R.drawable.patient_female
    };

    String[] namaPasien = {
            "Michael",
            "Yoseph",
            "Galih",
            "Elissa",
            "Sony",
            "Hevi"
    };

    String[] tglLahirPasien = {
            "00-00-00-01, 2 Nov 1996, Male",
            "00-00-00-02, 11 Mei 1996, Male",
            "00-00-00-03, 8 Feb 1996, Male",
            "00-00-00-04, 2 Sep 1998, Male",
            "00-00-00-05, 10 Jun 1996, Male",
            "00-00-00-06, 3 Apr 1997, Male"

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_list_view);

        ListView listView = (ListView) findViewById(R.id.listViewPatient);

        CustomAdapter customAdapter = new CustomAdapter();

        listView.setAdapter(customAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(PatientListViewActivity.this, PatientDetailActivity.class);
                intent.putExtra("fotopasien", fotoPasien[i]);
                intent.putExtra("namapasien", namaPasien[i]);
                intent.putExtra("lahirpasien", tglLahirPasien[i]);
                startActivity(intent);
            }
        });
    }

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return fotoPasien.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.custom_layout_listview, null);

            ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
            TextView textView_name = (TextView) view.findViewById(R.id.textViewName);
            TextView textView_description = (TextView) view.findViewById(R.id.textViewMRN);

            imageView.setImageResource(fotoPasien[i]);
            textView_name.setText(namaPasien[i]);
            textView_description.setText(tglLahirPasien[i]);

            return view;
        }

    }

}

