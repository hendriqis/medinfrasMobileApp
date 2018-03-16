package com.example.lenovo.medinfras.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.medinfras.HttpHandler;
import com.example.lenovo.medinfras.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PatientListActivity extends AppCompatActivity {

    @BindView(R.id.progressBarId)
    ProgressBar progressBarId;
    @BindView(R.id.listView)
    ListView listView;

    private String TAG = PatientListActivity.class.getSimpleName();

    private static String url = "https://api.myjson.com/bins/r75ll";

    ArrayList<HashMap<String, String>> patientList;

    int[] fotoPasien = {
            R.drawable.patient_male,
            R.drawable.patient_male,
            R.drawable.patient_male,
            R.drawable.patient_female,
            R.drawable.patient_male,
            R.drawable.patient_female
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_list);
        ButterKnife.bind(this);

        patientList = new ArrayList<>();

        listView = (ListView) findViewById(R.id.listView);

        listView.setAdapter(new MyListAdapter(this, R.layout.custom_layout_listview));

        new GetPatients().execute();
    }

    public class GetPatients extends AsyncTask<Void, Void, Void> {

        @BindView(R.id.imageView)
        ImageView imageView;
        @BindView(R.id.textViewMRN)
        TextView textViewMRN;
        @BindView(R.id.textViewGender)
        TextView textViewGender;
        @BindView(R.id.textViewBirthday)
        TextView textViewBirthday;
        @BindView(R.id.textViewName)
        TextView textViewName;
        @BindView(R.id.btnTimeline)
        Button btnTimeline;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBarId.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            HttpHandler sh = new HttpHandler();

            String jsonstr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from URL : " + jsonstr);

            if (jsonstr != null) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonstr);

                    //get json array node
                    JSONArray patient = jsonObject.getJSONArray("patient");

                    //loop through all contacts
                    for (int i = 0; i < patient.length(); i++) {
                        JSONObject c = patient.getJSONObject(i);

                        int id = c.getInt("id");
                        String name = c.getString("name");
                        String mrn = c.getString("mrn");
                        String gender = c.getString("gender");
                        String birthday = c.getString("birthday");
                        String image = c.getString("image");

                        HashMap<String, String> patients = new HashMap<>();

                        patients.put("name", name);
                        patients.put("mrn", mrn);
                        patients.put("gender", gender);
                        patients.put("birthday", birthday);
                        patients.put("image", image);

                        patientList.add(patients);
                    }

                } catch (final JSONException e) {
                    Log.e(TAG, "Json Parsing Error : " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(PatientListActivity.this, "Json Parsing Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            } else {
                Log.e(TAG, "Couldn't get JSON from server");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(PatientListActivity.this, "Couldn't get JSON from server. CHECK LOGCAT", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ListAdapter adapter = new SimpleAdapter
                    (PatientListActivity.this,
                            patientList,
                            R.layout.custom_layout_listview,
                            new String[]{"name", "mrn", "gender", "birthday"},
                            new int[]{R.id.textViewName, R.id.textViewMRN, R.id.textViewGender, R.id.textViewBirthday});

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String name = patientList.get(i).get("name");
                    String mrn = patientList.get(i).get("mrn");
                    String gender = patientList.get(i).get("gender");
                    String image = patientList.get(i).get("image");

                    Intent intent = new Intent(PatientListActivity.this, PatientDetailActivity.class);
                    intent.putExtra("name", name);
                    intent.putExtra("mrn", mrn);
                    intent.putExtra("gender", gender);
                    intent.putExtra("image", fotoPasien[i]);
                    startActivity(intent);
                }
            });

            listView.setAdapter(adapter);

            progressBarId.setVisibility(View.GONE);
        }
    }

    private class MyListAdapter extends ArrayAdapter<String> {
        private int layout;

        public MyListAdapter(@NonNull Context context, int resource) {
            super(context, resource);
            layout = resource;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            ViewHolder mainViewHolder = null;
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout, parent, false);
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.buttonTimeline = (Button) convertView.findViewById(R.id.btnTimeline);
                viewHolder.buttonTimeline.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(PatientListActivity.this, "Button Clicked", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(PatientListActivity.this, TimelineActivity.class));
                    }
                });
                convertView.setTag(viewHolder);
            }
            else {
                mainViewHolder = (ViewHolder) convertView.getTag();
            }

            return convertView;
        }
    }

    public class ViewHolder {
        Button buttonTimeline;
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