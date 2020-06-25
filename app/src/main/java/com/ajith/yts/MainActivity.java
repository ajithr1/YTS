package com.ajith.yts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "ajju";
    private TextInputEditText editText;
    private MaterialButton button;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    private ArrayList<Movie> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.nameText);
        button = findViewById(R.id.searchButton);
        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        arrayList = new ArrayList<>();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String query = Objects.requireNonNull(editText.getText()).toString();

                MovieAdapter adapter = new MovieAdapter(getArrayList(query), getApplicationContext());

                recyclerView.setAdapter(adapter);
            }
        });
    }

    private ArrayList<Movie> getArrayList(String query){
        ApiInterface apiInterface = ApiClass.getRetrofit().create(ApiInterface.class);
        Call<JsonElement> call = apiInterface.getMovie(query);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                assert response.body() != null;
                JsonObject jsonObject = response.body().getAsJsonObject();
                JsonObject data = jsonObject.getAsJsonObject("data");
                JsonArray jsonArray = data.getAsJsonArray("movies");
                int a = Integer.parseInt(data.get("movie_count").toString());

                for (int i=0; i<a; i++){
                    JsonObject object = jsonArray.get(i).getAsJsonObject();
                    arrayList.add(new Movie(object.get("title_long").toString(),
                            Integer.parseInt(object.get("id").toString())));
                }
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
            }
        });

        return arrayList;
    }
}