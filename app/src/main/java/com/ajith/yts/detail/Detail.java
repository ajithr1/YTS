package com.ajith.yts.detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.ajith.yts.ApiClass;
import com.ajith.yts.ApiInterface;
import com.ajith.yts.Movie;
import com.ajith.yts.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Detail extends AppCompatActivity {

    private static final String TAG = "ajju";
    private TextView movieName, movieRating, movieRunTime;
    private ImageView imageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        movieName = findViewById(R.id.movieName);
        movieRating = findViewById(R.id.movieRating);
        movieRunTime = findViewById(R.id.movieRunTime);
        imageView = findViewById(R.id.imageView3);

        Intent intent = getIntent();
        int movieID = intent.getIntExtra("key", 1);

        final ArrayList<MovieDetail> movieDetails = new ArrayList<>();

        ApiInterface apiInterface = ApiClass.getRetrofit().create(ApiInterface.class);
        Call<JsonElement> call = apiInterface.getMovieDetail(movieID);

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                assert response.body() != null;
                JsonObject object = (response.body().getAsJsonObject())
                        .get("data").getAsJsonObject().get("movie").getAsJsonObject();

                movieName.setText(removeQuotes(object.get("title_long").toString()));
                movieRating.setText(removeQuotes(object.get("rating").toString()));
                movieRunTime.setText(removeQuotes(object.get("runtime").toString()));
                //imageView.setImageBitmap(getBitmapFromURL(removeQuotes(object.get("small_cover_image").toString())));

                JsonArray array = object.get("torrents").getAsJsonArray();

                for (int i=0; i<array.size(); i++){
                    JsonObject jsonObject = array.get(i).getAsJsonObject();
                    movieDetails.add(new MovieDetail(jsonObject.get("quality").toString(),
                            jsonObject.get("type").toString(),
                            jsonObject.get("size").toString(),
                            jsonObject.get("url").toString()));
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Log.d(TAG, "onFailure: Detail");
            }
        });

        RecyclerView recyclerView = findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new DetailAdapter(movieDetails, this));
    }

    private String removeQuotes(String string) {

        return string.replace("\"", "");
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            Log.e("src",src);
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Exception", Objects.requireNonNull(e.getMessage()));
            return null;
        }
    }
}