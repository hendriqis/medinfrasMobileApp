package com.example.lenovo.medinfras.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.medinfras.R;
import com.example.lenovo.medinfras.activity.PatientDetailActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabViewFragment extends Fragment {

    int[] fotoDokter = {
            R.drawable.physician,
            R.drawable.physician
    };

    String[] namaDokter = {
            "Dr. Andre",
            "Dr. Sofia"
    };

    String[] catatanDokter = {
            "Catatan Dokter Andre",
            "Catatan Dokter Sofia"
    };

    public TabViewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab_view, container, false);

        ListView listView = (ListView) view.findViewById(R.id.catatanPerawatLog);

        listView.setAdapter(new CustomAdapter());

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(namaDokter[i]);
                builder.setMessage(catatanDokter[i]);
                builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Toast.makeText(getActivity(), "You just click " + namaDokter[i], Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });
        return view;
    }

    class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return fotoDokter.length;
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
            view = getLayoutInflater().inflate(R.layout.catatan_dokter_item, null);

            ImageView imageView = (ImageView) view.findViewById(R.id.imgFotoDokter);
            TextView textView_nama = (TextView) view.findViewById(R.id.txtNamaDokter);
            TextView textView_catatan = (TextView) view.findViewById(R.id.txtCatatanDokter);

            imageView.setImageResource(fotoDokter[i]);
            textView_nama.setText(namaDokter[i]);
            textView_catatan.setText(catatanDokter[i]);

            return view;
        }
    }

}
