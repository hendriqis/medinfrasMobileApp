package com.example.lenovo.medinfras.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import com.example.lenovo.medinfras.R;
import com.example.lenovo.medinfras.adapter.ExpandableListViewAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DrugListActivity extends AppCompatActivity {

    public static String jsonURL = "https://api.myjson.com/bins/19fmh7";
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
        prepareListData();
        
        expandableListViewAdapter = new ExpandableListViewAdapter(this, listDataParent,
                listDataChild);

        //setting list adapter
        expandableListView.setAdapter(expandableListViewAdapter);
    }

    private void prepareListData() {
        listDataParent = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        //Parent Title
        listDataParent.add("Paracetamol");
        listDataParent.add("Biogesic");
        listDataParent.add("Formalin");

        //Add the child data
        List<String> Paracetamol = new ArrayList<String>();
        Paracetamol.add("Generic Name : donepezil hydrochloride");
        Paracetamol.add("Signa : 1 x sehari 3 Tablet");
        Paracetamol.add("Route : Oral");
        Paracetamol.add("Physician : Dr. A");
        Paracetamol.add("Start Date : 20-Dec-2017");

        List<String> Biogesic = new ArrayList<String>();
        Biogesic.add("Generic Name : donepezil hydrochloride");
        Biogesic.add("Signa : 1 x sehari 3 Tablet");
        Biogesic.add("Route : Oral");
        Biogesic.add("Physician : Dr. B");
        Biogesic.add("Start Date : 20-Dec-2017");

        List<String> Formalin = new ArrayList<String>();
        Formalin.add("Generic Name : donepezil hydrochloride");
        Formalin.add("Signa : 1 x sehari 3 Tablet");
        Formalin.add("Route : Oral");
        Formalin.add("Physician : Dr. C");
        Formalin.add("Start Date : 20-Dec-2017");

        listDataChild.put(listDataParent.get(0), Paracetamol);
        listDataChild.put(listDataParent.get(1), Biogesic);
        listDataChild.put(listDataParent.get(2), Formalin);
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
