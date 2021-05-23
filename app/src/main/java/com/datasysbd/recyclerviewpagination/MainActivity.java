package com.datasysbd.recyclerviewpagination;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private static final String TAG ="fsd" ;
    NestedScrollView scrollView;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    ArrayList<ModelData> dataList = new ArrayList<>();
    ApiInterface apiInterface;
    Adapter adapter;
    int page = 1, limit = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = findViewById(R.id.recycler);
        progressBar = findViewById(R.id.progressbar);
        scrollView = findViewById(R.id.nestedScrollView);

        adapter=new Adapter(MainActivity.this,dataList);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getData(page, limit);
        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {

                    page++;
                    progressBar.setVisibility(View.VISIBLE);
                    getData(page, limit);

                }
            }
        });

    }

    private void getData(int page, int limit) {

        Retrofit instance = ApiClient.getClient();
        apiInterface = instance.create(ApiInterface.class);

        apiInterface.getData(page,limit).enqueue(new Callback<List>() {
            @Override
            public void onResponse(Call<List> call, Response<List> response) {

                if (response.isSuccessful() && response.body() != null) {
                    progressBar.setVisibility(View.VISIBLE);
                    JSONArray jsonArray = new JSONArray(response.body());
                    parseResult(jsonArray);


                }
            }

            @Override
            public void onFailure(Call<List> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });



    }

    private void parseResult(JSONArray jsonArray) {

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                ModelData data = new ModelData();

                data.setImage(jsonObject.getString("download_url"));
                data.setName(jsonObject.getString("author"));
                dataList.add(data);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            adapter = new Adapter(MainActivity.this, dataList);
            recyclerView.setAdapter(adapter);
        }


    }
}