package com.example.lenovo.medinfras.activity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import com.example.lenovo.medinfras.R;
import com.example.lenovo.medinfras.adapter.ExpandableListViewAdapter;
import com.example.lenovo.medinfras.javaclass.DrugJsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DrugListActivity extends AppCompatActivity {

    public static String jsonURL = "https://api.myjson.com/bins/17iogq";
    ExpandableListViewAdapter expandableListViewAdapter;
    ExpandableListView expandableListView;
    List<String> listDataParent;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drug_list);

        //get list view
        expandableListView = (ExpandableListView) findViewById(R.id.expandableListViewId);

        //preparing list of data
        new Downloadjson().execute();
    }

    @SuppressLint("StaticFieldLeak")
    private class Downloadjson extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {

            DrugJsonParser jsonParser = new DrugJsonParser();
            String jsonstr = jsonParser.makeServiceCall(jsonURL);

            if(jsonstr != null) {
                listDataParent = new ArrayList<String>();
                listDataChild = new HashMap<String, List<String>>();
            }

            try {
                JSONObject object = new JSONObject(jsonstr);
                JSONArray array = object.getJSONArray("drug");

                for (int i = 0; i < array.length(); i++) {
                    JSONObject jsonObject = array.getJSONObject(i);

                    //add parent data
                    listDataParent.add(jsonObject.getString("name"));

                    //add child data
                    List<String> list = new ArrayList<String>();
                    list.add(jsonObject.getString("generic"));
                    list.add(jsonObject.getString("route"));
                    list.add(jsonObject.getString("physician"));

                    //parent into child data
                    listDataChild.put(listDataParent.get(i), list);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            expandableListViewAdapter = new ExpandableListViewAdapter(getApplicationContext(), listDataParent,
                    listDataChild);

            //setting list adapter
            expandableListView.setAdapter(expandableListViewAdapter);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home :
                onBackPressed();
                return true;
        } return super.onOptionsItemSelected(item);
    }
}
