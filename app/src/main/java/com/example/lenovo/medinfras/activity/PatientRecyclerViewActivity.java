package com.example.lenovo.medinfras.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.lenovo.medinfras.Listitem;
import com.example.lenovo.medinfras.R;
import com.example.lenovo.medinfras.adapter.RecyclerViewCardAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PatientRecyclerViewActivity extends AppCompatActivity implements RecyclerViewCardAdapter.OnItemClickListener{

    @BindView(R.id.recyclerViewId)
    RecyclerView recyclerView;

    private static final String json_url = "https://api.jsonbin.io/b/5a98a83473fb541c61a5af78";
    private List<Listitem> list_item;
    private RecyclerViewCardAdapter adapter;

    public static final String EXTRA_NAME = "name";
    public static final String EXTRA_MRN = "mrn";
    public static final String EXTRA_GENDER = "gender";
    public static final String EXTRA_BIRTHDAY = "birthday";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_recycler_view);
        ButterKnife.bind(this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list_item = new ArrayList<>();

        loadRecyclerViewData();
    }

    private void loadRecyclerViewData() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                json_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("patient");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                Listitem item = new Listitem(
                                        object.getString("image"),
                                        object.getString("name"),
                                        object.getString("mrn"),
                                        object.getString("gender"),
                                        object.getString("birthday"));
                                list_item.add(item);
                            }

                            adapter = new RecyclerViewCardAdapter(list_item, PatientRecyclerViewActivity.this);
                            recyclerView.setAdapter(adapter);
                            adapter.setOnItemClickListener(PatientRecyclerViewActivity.this);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(PatientRecyclerViewActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PatientRecyclerViewActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
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


    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(this, PatientDetailActivity.class);
        Listitem clickedItem = list_item.get(position);

        intent.putExtra(EXTRA_NAME,clickedItem.getNameCard());
        intent.putExtra(EXTRA_MRN, clickedItem.getMRNCard());
        intent.putExtra(EXTRA_GENDER, clickedItem.getGenderCard());

        startActivity(intent);
    }
}
