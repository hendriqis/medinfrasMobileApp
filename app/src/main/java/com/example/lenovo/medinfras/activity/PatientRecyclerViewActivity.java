package com.example.lenovo.medinfras.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
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

public class PatientRecyclerViewActivity extends AppCompatActivity implements
        RecyclerViewCardAdapter.OnItemClickListener {

    @BindView(R.id.recyclerViewId)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayoutId)
    SwipeRefreshLayout swipeRefreshLayoutId;


    private static final String json_url = "https://api.jsonbin.io/b/5a98a83473fb541c61a5af78";
    private List<Listitem> list_item;
    private RecyclerViewCardAdapter adapter;

    public static final String EXTRA_NAME = "name";
    public static final String EXTRA_MRN = "mrn";
    public static final String EXTRA_GENDER = "gender";
    public static final String EXTRA_BIRTHDAY = "birthday";


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_recycler_view);
        ButterKnife.bind(this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        swipeRefreshLayoutId.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayoutId.setRefreshing(false);
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0,0);
            }
        });

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

                            adapter = new RecyclerViewCardAdapter(list_item,
                                    PatientRecyclerViewActivity.this);
                            recyclerView.setAdapter(adapter);
                            adapter.setOnItemClickListener(PatientRecyclerViewActivity.this);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(PatientRecyclerViewActivity.this, e.toString(), Toast
                                    .LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PatientRecyclerViewActivity.this, error.toString(), Toast
                                .LENGTH_SHORT).show();
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

        intent.putExtra(EXTRA_NAME, clickedItem.getNameCard());
        intent.putExtra(EXTRA_MRN, clickedItem.getMRNCard());
        intent.putExtra(EXTRA_GENDER, clickedItem.getGenderCard());

        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_patient_search, menu);

        //Menu Item SearchView
        final MenuItem menuSearch = menu.findItem(R.id.menuSearchView);
        final SearchView searchView = (SearchView) menuSearch.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!searchView.isIconified()) {
                    searchView.setIconified(true);
                }
                menuSearch.collapseActionView();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                final List<Listitem> filtermodelist = filter(list_item, newText);
                adapter.setFilter(filtermodelist);
                return true;
            }
        });

        //Menu Item Refresh Page
        final MenuItem menuRefresh = menu.findItem(R.id.menuRefresh);
        menuRefresh.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0,0);
                return true;
            }
        });

        return true;
    }


    private List<Listitem> filter(List<Listitem> li, String query) {
        query = query.toLowerCase();
        final List<Listitem> filterModeList = new ArrayList<>();
        for (Listitem listitem : li) {
            final String text = listitem.getNameCard().toLowerCase();
            if (text.startsWith(query)) {
                list_item = filterModeList;
                filterModeList.add(listitem);
            }
        }
        return filterModeList;
    }
}
